package error.pirate.backend.purchaseOrder.query.dto;

import error.pirate.backend.common.Pagination;

import java.time.LocalDateTime;

public class PurchaseOrderRequest extends Pagination {

    private LocalDateTime purchaseOrderTargetDueDate;   // 발주서 목표 납기일

}
