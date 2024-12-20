package error.pirate.backend.workOrder.query.dto;

import error.pirate.backend.workOrder.command.domain.aggregate.entity.WorkOrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Schema(description = "작업지시서 목록 필터링 DTO")
public class WorkOrderFilterDTO {

    private String warehouseName;          // 생산창고 이름
    private List<WorkOrderStatus> workOrderStatus;        // 작업지시 상태

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate;       // 검색 시작일
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;         // 검색 종료일

    private Integer page = 1;              // 기본값: 1
    private Integer size = 10;             // 기본값: 10

    public LocalDateTime getStartDateTime() {
        return startDate != null ? startDate.atStartOfDay() : null;
    }

    public LocalDateTime getEndDateTime() {
        return endDate != null ? endDate.atTime(23, 59, 59) : null;
    }

}
