package error.pirate.backend.shippingInstruction.query.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@Schema(description = "출하지시서 상세 응답")
public class ShippingInstructionResponse {
    private ShippingInstructionDTO shippingInstructionDTO;
    private List<ShippingInstructionItemDTO> itemList;
}
