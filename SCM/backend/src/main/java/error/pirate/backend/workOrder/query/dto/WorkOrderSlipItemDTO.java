package error.pirate.backend.workOrder.query.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "작업지시서 전표 품목 DTO")
public class WorkOrderSlipItemDTO {

    private Long itemSeq;
    private String itemUnitTitle;
    private Long chileItemSeq;
    private String childItemName;
    private int bomQuantity;


}
