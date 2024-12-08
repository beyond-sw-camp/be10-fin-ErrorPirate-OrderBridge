package error.pirate.backend.item.command.domain.service;

import error.pirate.backend.item.command.domain.aggregate.entity.Item;
import error.pirate.backend.item.command.domain.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemDomainService {

    private final ItemRepository itemRepository;

    public List<Item> findByItemNameIn(List<String> itemNameList) {
        return itemRepository.findByItemNameIn(itemNameList);
    }
}
