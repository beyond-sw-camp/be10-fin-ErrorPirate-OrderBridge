package error.pirate.backend.purchaseOrder.query.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PurchaseOrderStockSituationResponse {

    private Long purchaseOrderSeq;

    private String purchaseOrderName; // 발주서명

    private LocalDateTime purchaseOrderRegDate; // 발주 일자

    private String itemName;

    private Integer purchaseOrderItemExtendedPrice;

    private Integer purchaseOrderItemQuantity;

    private Integer purchaseOrderMonthPrice;  // 발주 월별 총금액

    private Integer purchaseOrderMonthQuantity;   // 발주 월별 총수량

    private String purchaseOrderRegMonth;

}
