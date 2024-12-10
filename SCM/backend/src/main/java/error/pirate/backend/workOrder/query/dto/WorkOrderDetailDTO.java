package error.pirate.backend.workOrder.query.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Schema(description = "작업지시서 상세조회 DTO")
public class WorkOrderDetailDTO {

    private Long workOrderSeq;
    private String workOrderName;
    private String warehouseName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime workOrderIndicatedDate;
    private String workOrderStatus;
    private int workOrderIndicatedQuantity;
    private int workOrderPrice;
    private String userName;
    private String clientName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime workOrderRegDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime workOrderModDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime workOrderEndDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime workOrderDueDate;
    private int WorkOrderWorkQuantity;
    private String workOrderNote;

}
