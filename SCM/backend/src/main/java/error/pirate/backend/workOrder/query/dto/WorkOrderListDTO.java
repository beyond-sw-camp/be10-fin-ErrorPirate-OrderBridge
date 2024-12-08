package error.pirate.backend.workOrder.query.dto;

import error.pirate.backend.item.command.domain.aggregate.entity.Item;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@Schema(description = "작업지시서 목록")
public class WorkOrderListDTO {

    private Long workOrderSeq;
    private String workOrderName;
    private String workOrderStatus;
    private LocalDateTime workOrderIndicatedDate;
    private Long warehouseSeq;
    private List<WorkOrderItemDTO> itemLists;
}
