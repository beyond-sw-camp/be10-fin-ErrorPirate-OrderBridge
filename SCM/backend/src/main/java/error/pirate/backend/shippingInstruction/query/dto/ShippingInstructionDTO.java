package error.pirate.backend.shippingInstruction.query.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@Schema(description = "출하지시서 DTO")
public class ShippingInstructionDTO {
    private long shippingInstructionSeq;
    private String shippingInstructionName;
    private String shippingInstructionStatus;
    private LocalDateTime shippingInstructionScheduledShipmentDate;
    private String clientName;
    private int shippingInstructionTotalQuantity;
    private String shippingInstructionAddress;
    private String userName;
    private String shippingInstructionNote;
}
