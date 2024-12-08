package error.pirate.backend.shippingInstruction.command.mapper;

import error.pirate.backend.item.command.domain.aggregate.entity.Item;
import error.pirate.backend.shippingInstruction.command.application.dto.ShippingInstructionItemDTO;
import error.pirate.backend.shippingInstruction.command.application.dto.ShippingInstructionRequest;
import error.pirate.backend.shippingInstruction.command.domain.aggregate.entity.ShippingInstruction;
import error.pirate.backend.shippingInstruction.command.domain.aggregate.entity.ShippingInstructionItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ShippingInstructionItemMapper {
    public static List<ShippingInstructionItem> toEntity(
            ShippingInstructionRequest shippingInstructionRequest,
            ShippingInstruction shippingInstruction,
            List<Item> itemList) {

        List<ShippingInstructionItem> shippingInstructionItemList = new ArrayList<>();

        // shippingInstructionRequest에서 shippingInstructionItems 가져오기
        List<ShippingInstructionItemDTO> itemDTOList  = shippingInstructionRequest.getShippingInstructionItems();

        // itemList를 Map으로 변환하여 매핑 속도 향상
        Map<String, Item> itemMap = itemList.stream()
                .collect(Collectors.toMap(Item::getItemName, item -> item));

        // DTO 리스트를 순회하며 ShippingInstructionItem 생성
        for (ShippingInstructionItemDTO itemDTO : itemDTOList) {
            Item item = itemMap.get(itemDTO.getItemName()); // itemName으로 Item 매핑

            if (item == null) {
                // 매핑되지 않은 Item이 있으면 예외 처리 (필요 시 Custom Exception 사용)
                throw new IllegalArgumentException("Item not found: " + itemDTO.getItemName());
            }

            // ShippingInstructionItem 생성 및 리스트에 추가
            ShippingInstructionItem shippingInstructionItem = ShippingInstructionItem.create(
                    shippingInstruction, // ShippingInstruction 객체
                    item, // Item 객체
                    itemDTO.getShippingInstructionItemQuantity(), // 수량
                    itemDTO.getShippingInstructionItemNote() // 비고
            );
            shippingInstructionItemList.add(shippingInstructionItem);
        }

        return shippingInstructionItemList;
    }
}
