package error.pirate.backend.shippingInstruction.query.service;

import error.pirate.backend.shippingInstruction.query.dto.*;
import error.pirate.backend.shippingInstruction.query.mapper.ShippingInstructionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShippingInstructionQueryService {

    private final ShippingInstructionMapper shippingInstructionMapper;

    /* 출하지시서 리스트 조회 */
    @Transactional(readOnly = true)
    public ShippingInstructionListResponse readShippingInstructionList(int page, int size, LocalDate startDate, LocalDate endDate, String clientName, String shippingInstructionStatus) {
        int offset = (page - 1) * size;
        List<ShippingInstructionListDTO> shippingInstructionList
                = shippingInstructionMapper.selectShippingInstructionList(offset, size, startDate, endDate, clientName, shippingInstructionStatus);

        long totalItems = shippingInstructionMapper.countShippingInstruction(startDate, endDate, clientName, shippingInstructionStatus);

        return ShippingInstructionListResponse.builder()
                .shippingInstructionList(shippingInstructionList)
                .currentPage(page)
                .totalPages((int) Math.ceil((double) totalItems / size))
                .totalItems(totalItems)
                .build();
    }

    /* 출하지시서 상세 조회 */
    @Transactional(readOnly = true)
    public ShippingInstructionResponse readShippingInstruction(long shippingInstructionSeq) {

        // 품목을 제외한 출하지시서 조회
        ShippingInstructionDTO shippingInstruction = shippingInstructionMapper.selectShippingInstructionByShippingInstructionSeq(shippingInstructionSeq);

        // 출하지시서에 해당하는 품목 리스트 조회
        List<ShippingInstructionItemDTO> itemList
                = shippingInstructionMapper.selectItemListByShippingInstructionSeq(shippingInstructionSeq);

        return ShippingInstructionResponse.builder()
                .shippingInstructionDTO(shippingInstruction)
                .itemList(itemList)
                .build();
    }
}
