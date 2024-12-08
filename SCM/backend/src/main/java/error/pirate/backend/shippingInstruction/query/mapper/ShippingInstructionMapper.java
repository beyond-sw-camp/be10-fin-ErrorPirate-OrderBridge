package error.pirate.backend.shippingInstruction.query.mapper;

import error.pirate.backend.shippingInstruction.query.dto.ShippingInstructionDTO;
import error.pirate.backend.shippingInstruction.query.dto.ShippingInstructionItemDTO;
import error.pirate.backend.shippingInstruction.query.dto.ShippingInstructionListDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface ShippingInstructionMapper {
    List<ShippingInstructionListDTO> selectShippingInstructionList(
            @Param("offset") int offset,
            @Param("limit") int limit,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("clientName") String clientName,
            @Param("shippingInstructionStatus") String shippingInstructionStatus);

    long countShippingInstruction(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("clientName") String clientName,
            @Param("shippingInstructionStatus") String shippingInstructionStatus);

    ShippingInstructionDTO selectShippingInstructionByShippingInstructionSeq(long shippingInstructionSeq);

    List<ShippingInstructionItemDTO> selectItemListByShippingInstructionSeq(long shippingInstructionSeq);
}
