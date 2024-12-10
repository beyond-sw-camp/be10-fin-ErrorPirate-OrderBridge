package error.pirate.backend.workOrder.query.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Schema(description = "작업지시서 전표 DTO")
public class WorkOrderSlipDTO {

    private Long workOrderSeq;
    private String workOrderName;
    private String clientName;
    private String userName;
    private String warehouseName;
    private String parentItemName;
    private int workOrderIndicatedQuantity;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime workOrderRegDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime workOrderModDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate workOrderIndicatedDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate workOrderDueDate;

}
