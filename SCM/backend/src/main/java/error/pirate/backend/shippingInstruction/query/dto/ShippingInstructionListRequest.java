package error.pirate.backend.shippingInstruction.query.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Schema(description = "출하지시서 리스트 요청")
public class ShippingInstructionListRequest {
    private int page = 1; // 기본값 설정
    private int size = 10; // 기본값 설정
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) // LocalDate 매핑을 위해 필요
    private LocalDate startDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;
    private String clientName;
    private String shippingInstructionStatus;
}