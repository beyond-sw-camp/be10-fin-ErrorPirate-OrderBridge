package error.pirate.backend.shippingInstruction.command.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@RequiredArgsConstructor    // 모든 값을 전달받은 생성자 생성
@Schema(description = "출하지시서 요청 데이터")
public class ShippingInstructionRequest {
    private LocalDateTime shippingInstructionScheduledShipmentDate; // 출하예정일
    private String salesOrderName;  // 주문서명
    private String clientName;  // 거래처명
    private String shippingInstructionAddress;  // 출하 주소
    private String shippingInstructionNote; // 출하 지시서 비고
    private List<ShippingInstructionItemDTO> shippingInstructionItems; // 물품 리스트
}
