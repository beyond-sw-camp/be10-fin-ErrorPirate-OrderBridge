package error.pirate.backend.workOrder.query.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@Schema(description = "작업지시서 전표 응답 DTO")
public class WorkOrderSlipResponse {

    private WorkOrderSlipDTO slipDTO;   // 작업지시서 정보 (상위 품목 포함)
    private List<WorkOrderSlipItemDTO> items; // BOM 품목 리스트

}
