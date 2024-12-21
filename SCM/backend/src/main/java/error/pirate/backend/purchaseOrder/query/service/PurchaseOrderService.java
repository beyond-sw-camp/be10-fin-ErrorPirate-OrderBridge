package error.pirate.backend.purchaseOrder.query.service;

import error.pirate.backend.common.ExcelDownLoad;
import error.pirate.backend.common.Pagination;
import error.pirate.backend.purchaseOrder.query.dto.*;
import error.pirate.backend.purchaseOrder.query.mapper.PurchaseOrderMapper;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PurchaseOrderService {

    private final PurchaseOrderMapper purchaseOrderMapper;

    private final ExcelDownLoad excelDownBody;

    public PurchaseOrderResponsePagination readPurchaseOrderList(PurchaseOrderRequest purchaseOrderRequest) {
        List<PurchaseOrderResponse> purchaseOrderResponseList = purchaseOrderMapper.readPurchaseOrderList(purchaseOrderRequest);
        for (PurchaseOrderResponse purchaseOrderResponse : purchaseOrderResponseList) {
            List<PurchaseOrderItemResponse> purchaseOrderItemResponseList = purchaseOrderMapper.readPurchaseOrderItemList(purchaseOrderResponse.getPurchaseOrderSeq());
            purchaseOrderResponse.setPurchaseOrderItemResponseList(purchaseOrderItemResponseList);
        }

        int totalCount = purchaseOrderMapper.readPurchaseOrderListCount(purchaseOrderRequest);

        purchaseOrderResponseList.forEach(order -> order.setPurchaseOrderStatusValue(order.getPurchaseOrderStatus().getValue()));

        Pagination pagination = new Pagination();
        pagination.responsePaging(purchaseOrderRequest.getPageNo(), totalCount);

        return PurchaseOrderResponsePagination.builder()
                .purchaseOrderResponseList(purchaseOrderResponseList)
                .pagination(pagination)
                .build();

    }

    public byte[] purchaseOrderExcelDown(PurchaseOrderRequest request) {
        request.setLimit(null);
        request.setOffset(null);
        List<PurchaseOrderResponse> purchaseOrderResponseList = purchaseOrderMapper.readPurchaseOrderList(request);

        String[] headers = {"발주서명", "발주서 품목", "거래처명", "계약 납기일", "목표 납기일", "상태"};
        String[][] excel = new String[purchaseOrderResponseList.size()][headers.length];

        for (int i = 0; i < purchaseOrderResponseList.size(); i++) {
            PurchaseOrderResponse dto = purchaseOrderResponseList.get(i);
            dto.setPurchaseOrderItemResponseList(purchaseOrderMapper.readPurchaseOrderItemList(dto.getPurchaseOrderSeq()));

            excel[i][0] = dto.getPurchaseOrderName();
            excel[i][1] = dto.getPurchaseOrderItemResponseList()
                    .stream()
                    .map(PurchaseOrderItemResponse::getItemName)
                    .collect(Collectors.joining(", "));//  품목
            excel[i][2] = dto.getClientName();
            excel[i][3] = dto.getPurchaseOrderDueDate() != null
                    ? dto.getPurchaseOrderDueDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                    : null;
            excel[i][4] = dto.getPurchaseOrderTargetDueDate() != null
                    ? dto.getPurchaseOrderTargetDueDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                    : null;
            excel[i][5] = String.valueOf(dto.getPurchaseOrderStatus());
        }

        return excelDownBody.excelDownBody(excel, headers, "발주서");
    }

    public List<PurchaseOrderSituationResponse> readPurchaseOrderSituationList(PurchaseOrderRequest request) {
        return purchaseOrderMapper.readPurchaseOrderSituationList(request);
    }

    public byte[] purchaseOrderSituationExcelDown(PurchaseOrderRequest request) {
        request.setLimit(null);
        request.setOffset(null);
        List<PurchaseOrderSituationResponse> purchaseOrderResponseList = purchaseOrderMapper.readPurchaseOrderSituationList(request);

        String[] headers = {"발주일자", "발주서명", "총 수량", "금액", "거래처명", "목표 납기일", "비고"};
        String[][] excel = new String[purchaseOrderResponseList.size()][headers.length];

        for (int i = 0; i < purchaseOrderResponseList.size(); i++) {
            String regMonth = "";
            PurchaseOrderSituationResponse dto = purchaseOrderResponseList.get(i);

            if (dto.getPurchaseOrderRegDate() != null) {
                excel[i][0] = dto.getPurchaseOrderRegDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                excel[i][1] = dto.getPurchaseOrderName();
                excel[i][2] = (dto.getPurchaseOrderTotalQuantity() != null ? dto.getPurchaseOrderTotalQuantity() : "0") + " 개";
                excel[i][3] = dto.getPurchaseOrderExtendedPrice() + " 원";
                excel[i][4] = dto.getClientName();
                excel[i][5] = dto.getPurchaseOrderTargetDueDate() != null
                        ? dto.getPurchaseOrderTargetDueDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                        : null;
                excel[i][6] = dto.getPurchaseOrderNote();
            } else {
                if (StringUtils.isNotEmpty(dto.getPurchaseOrderRegMonth())) {
                    excel[i][0] = dto.getPurchaseOrderRegMonth();
                    excel[i][1] = "-";
                    excel[i][2] = dto.getPurchaseOrderMonthQuantity() + " 개";
                    excel[i][3] = dto.getPurchaseOrderMonthPrice() + " 원";
                    excel[i][4] = "-";
                    excel[i][5] = "-";
                    excel[i][6] = "-";
                }
            }
        }
        return excelDownBody.excelDownBody(excel, headers, "발주 현황");
    }

    public List<PurchaseOrderStockSituationResponse> readPurchaseOrderStockSituationList(PurchaseOrderRequest request) {
        return purchaseOrderMapper.readPurchaseOrderStockSituationList(request);
    }

    // 미입고
    public byte[] purchaseOrderStockSituationExcelDown(PurchaseOrderRequest request) {
        request.setLimit(null);
        request.setOffset(null);
        List<PurchaseOrderStockSituationResponse> purchaseOrderResponseList = purchaseOrderMapper.readPurchaseOrderStockSituationList(request);

        String[] headers = {"발주일자", "발주서명", "품목명", "총 수량", "금액"};
        String[][] excel = new String[purchaseOrderResponseList.size()][headers.length];

        for (int i = 0; i < purchaseOrderResponseList.size(); i++) {
            PurchaseOrderStockSituationResponse dto = purchaseOrderResponseList.get(i);

            if (dto.getPurchaseOrderRegDate() != null) {
                excel[i][0] = dto.getPurchaseOrderRegDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                excel[i][1] = dto.getPurchaseOrderName();
                excel[i][2] = dto.getItemName();
                excel[i][3] = (dto.getPurchaseOrderItemQuantity() != null ? dto.getPurchaseOrderItemQuantity() : "0") + " 개";
                excel[i][4] = (dto.getPurchaseOrderItemExtendedPrice() != null ? dto.getPurchaseOrderItemExtendedPrice() : "0") + " 원";
            } else {
                if (StringUtils.isNotEmpty(dto.getPurchaseOrderRegMonth())) {
                    excel[i][0] = dto.getPurchaseOrderRegMonth();
                    excel[i][1] = "-";
                    excel[i][2] = "-";
                    excel[i][3] = dto.getPurchaseOrderMonthQuantity() + " 개";
                    excel[i][4] = dto.getPurchaseOrderMonthPrice() + " 원";
                }
            }
        }
        return excelDownBody.excelDownBody(excel, headers, "미입고 현황");
    }

}
