package error.pirate.backend.workOrder.query.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
public class WorkOrderFilterDTO {

    private String warehouseName;          // 생산창고 이름
    private String workOrderStatus;        // 작업지시 상태

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDateTime startDate;       // 검색 시작일
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDateTime endDate;         // 검색 종료일

    private Integer page = 1;              // 기본값: 1
    private Integer size = 10;             // 기본값: 10
}
