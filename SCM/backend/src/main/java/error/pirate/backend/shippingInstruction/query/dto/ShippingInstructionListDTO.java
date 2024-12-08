package error.pirate.backend.shippingInstruction.query.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@Schema(description = "출하지시서 리스트 DTO")
public class ShippingInstructionListDTO {
    private long shippingInstructionSeq;
    private String shippingInstructionName;
    private String shippingInstructionStatus;
    private LocalDate shippingInstructionScheduledShipmentDate;
    private String clientName;
    private String itemName;


}
