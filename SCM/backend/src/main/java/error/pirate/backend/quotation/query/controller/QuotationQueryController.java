package error.pirate.backend.quotation.query.controller;

import error.pirate.backend.quotation.query.dto.QuotationListResponse;
import error.pirate.backend.quotation.query.dto.QuotationResponse;
import error.pirate.backend.quotation.query.dto.QuotationSituationResponse;
import error.pirate.backend.quotation.query.service.QuotationQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/quotation")
@Tag(name = "Quotation", description = "견적서")
public class QuotationQueryController {

    private final QuotationQueryService quotationQueryService;

    @GetMapping("")
    @Operation(summary = "견적서 목록 조회")
    public ResponseEntity<QuotationListResponse> getQuotationList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) String clientName,
            @RequestParam(required = false) String quotationStatus) {

        return ResponseEntity.ok(quotationQueryService.getQuotationList(
                page, size, startDate, endDate, clientName, quotationStatus));
    }

    @GetMapping("/{quotationSeq}")
    @Operation(summary = "견적서 상세 조회")
    public ResponseEntity<QuotationResponse> getQuotation(
            @PathVariable Long quotationSeq) {

        return ResponseEntity.ok(quotationQueryService.getQuotation(quotationSeq));
    }

    @GetMapping("/situation")
    @Operation(summary = "견적서 현황 조회")
    public ResponseEntity<QuotationSituationResponse> getQuotationSituation(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) String clientName) {

        return ResponseEntity.ok(quotationQueryService.getQuotationSituation(
                startDate, endDate, clientName));
    }
}
