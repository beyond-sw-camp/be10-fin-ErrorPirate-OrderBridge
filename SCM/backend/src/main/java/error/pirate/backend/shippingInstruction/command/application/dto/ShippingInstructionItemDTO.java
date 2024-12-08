package error.pirate.backend.shippingInstruction.command.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "출하지시서 품목 DTO")
public class ShippingInstructionItemDTO {
    private String itemName;    // 품목명
    private int shippingInstructionItemQuantity;    // 물품 수량
    private String shippingInstructionItemNote;     // 물품 비고
}
