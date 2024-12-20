package error.pirate.backend.item.command.application.service;

import error.pirate.backend.common.NullCheck;
import error.pirate.backend.exception.CustomException;
import error.pirate.backend.exception.ErrorCodeType;
import error.pirate.backend.item.command.application.dto.BomItemDTO;
import error.pirate.backend.item.command.application.dto.ItemDTO;
import error.pirate.backend.item.command.application.dto.ItemCreateRequest;
import error.pirate.backend.item.command.application.dto.ItemUpdateRequest;
import error.pirate.backend.item.command.domain.aggregate.entity.BomItem;
import error.pirate.backend.item.command.domain.aggregate.entity.Item;
import error.pirate.backend.item.command.domain.aggregate.entity.ItemStatus;
import error.pirate.backend.item.command.domain.aggregate.entity.ItemUnit;
import error.pirate.backend.item.command.domain.repository.BomItemRepository;
import error.pirate.backend.item.command.domain.repository.ItemRepository;
import error.pirate.backend.item.command.domain.repository.ItemUnitRepository;
import error.pirate.backend.user.command.domain.aggregate.entity.User;
import error.pirate.backend.user.command.domain.repository.UserRepository;
import error.pirate.backend.warehouse.command.domain.aggregate.entity.Warehouse;
import error.pirate.backend.warehouse.command.domain.repository.WarehouseRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemUnitRepository itemUnitRepository;
    private final UserRepository userRepository;
    private final BomItemRepository bomItemRepository;
    private final ModelMapper modelMapper;
    private final WarehouseRepository warehouseRepository;

    @Transactional
    public void createItem(ItemCreateRequest request) {
        User user = userRepository.findById(request.getUserSeq())
                .orElseThrow(() -> new CustomException(ErrorCodeType.USER_NOT_FOUND));

        ItemUnit itemUnit = itemUnitRepository.findById(request.getItemUnitSeq())
                .orElseThrow(() -> new CustomException(ErrorCodeType.ITEM_UNIT_NOT_FOUND));

        Warehouse warehouse = warehouseRepository.findById(request.getWarehouseSeq())
                .orElseThrow(() -> new CustomException(ErrorCodeType.WAREHOUSE_NOT_FOUND));

        ItemDTO itemDTO = modelMapper.map(request, ItemDTO.class);
        itemDTO.setUser(user);
        itemDTO.setItemUnit(itemUnit);
        itemDTO.setWarehouse(warehouse);

        Item item = modelMapper.map(itemDTO, Item.class);

        // bom 등록
        if(NullCheck.nullCheck(request.getBomItemList())) {
            for(BomItemDTO bomItemDTO : request.getBomItemList()) {
                Item childItem = itemRepository.findById(bomItemDTO.getItemSeq()).orElseThrow(() -> new CustomException(ErrorCodeType.ITEM_NOT_FOUND));
                BomItem bomItem = BomItem.createBomItem(item, childItem, bomItemDTO.getBomChildItemQuantity());

                bomItemRepository.save(bomItem);
            }
        }

        itemRepository.save(item);
    }

    @Transactional
    public void updateItem(Long itemSeq, ItemUpdateRequest request) {

        // userSeq는 나중에 토큰에서 빼올 것
        // Long loginUserSeq = CustomUtil.getUserSeq();

        Long loginUserSeq = 1L;

        // 기존 품목 조회
        Item item = itemRepository.findById(itemSeq)
                .orElseThrow(() -> new CustomException(ErrorCodeType.ITEM_NOT_FOUND));
        if(!ItemStatus.ACTIVE.equals(item.getItemStatus())) {
            throw new CustomException(ErrorCodeType.ITEM_STATUS_ERROR);
        }

        ItemUnit itemUnit = itemUnitRepository.findById(request.getItemUnitSeq())
                .orElseThrow(() -> new IllegalArgumentException("Invalid itemUnitSeq: " + request.getItemUnitSeq()));

        Warehouse warehouse = warehouseRepository.findById(request.getWarehouseSeq())
                .orElseThrow(() -> new CustomException(ErrorCodeType.WAREHOUSE_NOT_FOUND));

        item.updateItem(itemUnit, warehouse, request);


        /* bom 수정 로직
        * 1. 해당 부모 item의 bom 전체 삭제
        * 2. bom 재등록
        * */
        bomItemRepository.deleteAllByParentItem(item);

        if(NullCheck.nullCheck(request.getBomItemList())) {
            for(BomItemDTO bomItemDTO : request.getBomItemList()) {
                Item childItem = itemRepository.findById(bomItemDTO.getItemSeq()).orElseThrow(() -> new CustomException(ErrorCodeType.ITEM_NOT_FOUND));
                BomItem bomItem = BomItem.createBomItem(item, childItem, bomItemDTO.getBomChildItemQuantity());

                bomItemRepository.save(bomItem);
            }
        }
    }

    @Transactional
    public void deleteItem(Long itemSeq) {
        // 품목 조회
        Item item = itemRepository.findById(itemSeq)
                .orElseThrow(() -> new CustomException(ErrorCodeType.ITEM_NOT_FOUND));
        if(!ItemStatus.ACTIVE.equals(item.getItemStatus())) {
            throw new CustomException(ErrorCodeType.ITEM_STATUS_ERROR);
        }

        // 품목 삭제 (상태값 변경)
        item.delete();
    }

    /* 물품 시퀀스들로 물품 리스트 불러오기 */
    @Transactional
    public List<Item> findAllById(List<Long> itemNameList) {
        return itemRepository.findAllById(itemNameList);
    }
}


