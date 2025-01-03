package error.pirate.backend.salesOrder.command.application.controller;

import error.pirate.backend.common.SecurityUtil;
import error.pirate.backend.salesOrder.command.application.dto.CreateSalesOrderRequest;
import error.pirate.backend.salesOrder.command.application.dto.UpdateSalesOrderRequest;
import error.pirate.backend.salesOrder.command.application.service.SalesOrderCommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/sales-order")
@Tag(name = "Sales Order", description = "주문서")
public class SalesOrderCommandController {

    private final SecurityUtil securityUtil;
    private final SalesOrderCommandService salesOrderCommandService;

    @PostMapping("")
    @Operation(summary = "주문서 등록")
    public ResponseEntity<Void> createSalesOrder(@RequestBody CreateSalesOrderRequest createSalesOrderRequest) {

        salesOrderCommandService.createSalesOrder(createSalesOrderRequest, securityUtil.getCurrentUserEmployeeNo());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{salesOrderSeq}")
    @Operation(summary = "주문서 수정")
    public ResponseEntity<Void> updateSalesOrder(
            @PathVariable Long salesOrderSeq,
            @RequestBody UpdateSalesOrderRequest request) {

        salesOrderCommandService.updateSalesOrder(salesOrderSeq, request);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{salesOrderSeq}")
    @Operation(summary = "주문서 삭제")
    public ResponseEntity<Void> deleteSalesOrder(
            @PathVariable Long salesOrderSeq) {

        salesOrderCommandService.deleteSalesOrder(salesOrderSeq);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/complete/{salesOrderSeq}")
    @Operation(summary = "주문서 결재완료")
    public ResponseEntity<Void> updateSalesOrderComplete(@PathVariable Long salesOrderSeq) {
        salesOrderCommandService.updateSalesOrderComplete(salesOrderSeq);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
