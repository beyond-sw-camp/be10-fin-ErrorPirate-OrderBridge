package error.pirate.backend.purchaseOrder.query.controller;

import error.pirate.backend.purchaseOrder.query.dto.PurchaseOrderRequest;
import error.pirate.backend.purchaseOrder.query.dto.PurchaseOrderResponsePagination;
import error.pirate.backend.purchaseOrder.query.dto.PurchaseOrderSituationResponse;
import error.pirate.backend.purchaseOrder.query.dto.PurchaseOrderStockSituationResponse;
import error.pirate.backend.purchaseOrder.query.service.PurchaseOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/purchaseOrder")
@Tag(name = "발주 API", description = "발주 API")
public class PurchaseOrderQueryController {

    private final PurchaseOrderService purchaseOrderService;

    @GetMapping
    @Operation(summary = "발주서 조회")
    public ResponseEntity<PurchaseOrderResponsePagination> readPurchaseOrderList(PurchaseOrderRequest purchaseOrderRequest) {
        return ResponseEntity.ok(purchaseOrderService.readPurchaseOrderList(purchaseOrderRequest));
    }

    @GetMapping("/excelDown")
    @Operation(summary = "발주서 엑셀다운")
    public ResponseEntity<byte[]> purchaseOrderExcelDown(PurchaseOrderRequest purchaseOrderRequest) {
        HttpHeaders headersResponse = new HttpHeaders();
        String fileName = URLEncoder.encode("발주서[" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "].xlsx", StandardCharsets.UTF_8);
        headersResponse.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);

        return ResponseEntity.ok()
                .headers(headersResponse)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(purchaseOrderService.purchaseOrderExcelDown(purchaseOrderRequest));
    }

    @GetMapping("/situation")
    @Operation(summary = "발주서 현황")
    public ResponseEntity<List<PurchaseOrderSituationResponse>> purchaseOrderSituation(PurchaseOrderRequest request) {
        return ResponseEntity.ok(purchaseOrderService.readPurchaseOrderSituationList(request));
    }

    @GetMapping("/situation/excelDown")
    @Operation(summary = "발주서 현황 엑셀다운")
    public ResponseEntity<byte[]> purchaseOrderSituationExcel(PurchaseOrderRequest request) {
        HttpHeaders headersResponse = new HttpHeaders();
        String fileName = URLEncoder.encode("발주서_현황[" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "].xlsx", StandardCharsets.UTF_8);
        headersResponse.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);

        return ResponseEntity.ok()
                .headers(headersResponse)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(purchaseOrderService.purchaseOrderSituationExcelDown(request));

    }

    @GetMapping("/stock/situation")
    @Operation(summary = "미입고 현황")
    public ResponseEntity<List<PurchaseOrderStockSituationResponse>> notInStockSituation(PurchaseOrderRequest request) {
        return ResponseEntity.ok(purchaseOrderService.readPurchaseOrderStockSituationList(request));
    }

    @GetMapping("/stock/situation/excelDown")
    @Operation(summary = "미입고 현황 엑셀다운")
    public ResponseEntity<byte[]> notInStockSituationExcel(PurchaseOrderRequest request) {
        HttpHeaders headersResponse = new HttpHeaders();
        String fileName = URLEncoder.encode("미입고_현황[" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "].xlsx", StandardCharsets.UTF_8);
        headersResponse.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);

        return ResponseEntity.ok()
                .headers(headersResponse)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(purchaseOrderService.purchaseOrderStockSituationExcelDown(request));

    }

}
