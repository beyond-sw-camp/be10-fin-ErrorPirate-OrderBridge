package error.pirate.backend.invoice.query.controller;

import error.pirate.backend.invoice.query.dto.InvoiceListResponse;
import error.pirate.backend.invoice.query.dto.InvoiceResponse;
import error.pirate.backend.invoice.query.dto.InvoiceSituationResponse;
import error.pirate.backend.invoice.query.service.InvoiceQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/invoice")
@Tag(name = "Invoice", description = "거래 명세서")
public class InvoiceQueryController {

    private final InvoiceQueryService invoiceQueryService;

    @GetMapping("")
    @Operation(summary = "거래 명세서 목록 조회")
    public ResponseEntity<InvoiceListResponse> getInvoiceList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) String clientName) {

        return ResponseEntity.ok(invoiceQueryService.getInvoiceList(
                page, size, startDate, endDate, clientName));
    }

    @GetMapping("/{invoiceSeq}")
    @Operation(summary = "거래 명세서 상세 조회")
    public ResponseEntity<InvoiceResponse> getInvoice(
            @PathVariable Long invoiceSeq) {

        return ResponseEntity.ok(invoiceQueryService.getInvoice(invoiceSeq));
    }

    @GetMapping("/situation")
    @Operation(summary = "거래 명세서 현황 조회")
    public ResponseEntity<InvoiceSituationResponse> getInvoiceSituation(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) String clientName) {

        return ResponseEntity.ok(invoiceQueryService.getInvoiceSituation(
                startDate, endDate, clientName));
    }
}
