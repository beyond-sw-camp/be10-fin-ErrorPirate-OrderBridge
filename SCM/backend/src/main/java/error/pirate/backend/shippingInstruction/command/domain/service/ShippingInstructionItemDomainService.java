package error.pirate.backend.shippingInstruction.command.domain.service;

import error.pirate.backend.item.command.domain.aggregate.entity.Item;
import error.pirate.backend.shippingInstruction.command.application.dto.ShippingInstructionRequest;
import error.pirate.backend.shippingInstruction.command.domain.aggregate.entity.ShippingInstruction;
import error.pirate.backend.shippingInstruction.command.domain.aggregate.entity.ShippingInstructionItem;
import error.pirate.backend.shippingInstruction.command.domain.repository.ShippingInstructionItemRepository;
import error.pirate.backend.shippingInstruction.command.mapper.ShippingInstructionItemMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShippingInstructionItemDomainService {

    private final ShippingInstructionItemRepository shippingInstructionItemRepository;

    /* 도메인 객체를 생성하는 로직 */
    public List<ShippingInstructionItem> createShippingInstructionItemList(
            ShippingInstructionRequest shippingInstructionRequest,
            ShippingInstruction shippingInstruction,
            List<Item> itemList) {

        /* dto to entity */
        List<ShippingInstructionItem> newShippingInstructionItems = ShippingInstructionItemMapper.toEntity(
                shippingInstructionRequest, shippingInstruction, itemList
        );

        return newShippingInstructionItems;
    }

    /* 도메인 객체를 저장하는 로직 */
    public List<ShippingInstructionItem> saveShippingInstructionItem(List<ShippingInstructionItem> newShippingInstructionItemList) {
        return shippingInstructionItemRepository.saveAll(newShippingInstructionItemList);
    }
}
