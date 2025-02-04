package error.pirate.backend.quotation.query.controller;

import error.pirate.backend.quotation.command.domain.aggregate.entity.QuotationStatus;
import error.pirate.backend.quotation.query.dto.QuotationListResponse;
import error.pirate.backend.quotation.query.dto.QuotationResponse;
import error.pirate.backend.quotation.query.dto.QuotationSituationResponse;
import error.pirate.backend.quotation.query.service.QuotationQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/quotation")
@Tag(name = "Quotation", description = "견적서")
public class QuotationQueryController {

    private final QuotationQueryService quotationQueryService;

    @GetMapping("")
    @Operation(summary = "견적서 목록 조회")
    public ResponseEntity<QuotationListResponse> readQuotationList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) String clientName,
            @RequestParam(required = false) List<QuotationStatus> quotationStatus) {

        return ResponseEntity.ok(quotationQueryService.readQuotationList(
                page, size, startDate, endDate, clientName, quotationStatus));
    }

    @GetMapping("/{quotationSeq}")
    @Operation(summary = "견적서 상세 조회")
    public ResponseEntity<QuotationResponse> readQuotation(
            @PathVariable Long quotationSeq) {

        return ResponseEntity.ok(quotationQueryService.readQuotation(quotationSeq));
    }

    @GetMapping("/situation")
    @Operation(summary = "견적서 현황 조회")
    public ResponseEntity<List<QuotationSituationResponse>> readQuotationSituation(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) String clientName) {

        return ResponseEntity.ok(quotationQueryService.readQuotationSituation(
                startDate, endDate, clientName));
    }

    @GetMapping("/status")
    @Operation(summary = "견적서 상태 분류 조회")
    public ResponseEntity<List<QuotationStatus.QuotationStatusResponse>> readQuotationStatus() {
        return ResponseEntity.ok(QuotationStatus.readQuotationStatusList());
    }

    @GetMapping("/excel")
    @Operation(summary = "견적서 목록 엑셀 다운로드")
    public ResponseEntity<byte[]> readQuotationExcel(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) String clientName,
            @RequestParam(required = false) List<QuotationStatus> quotationStatus) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + URLEncoder.encode(
                new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + "_견적서.xlsx"
                , StandardCharsets.UTF_8));

        return ResponseEntity.ok()
                .headers(httpHeaders)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(quotationQueryService.readQuotationExcel(startDate, endDate, clientName, quotationStatus));
    }

    @GetMapping("/situation/excel")
    @Operation(summary = "견적서 현황 엑셀 다운로드")
    public ResponseEntity<byte[]> readQuotationSituationExcel(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) String clientName) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + URLEncoder.encode(
                new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + "_견적서_현황.xlsx"
                , StandardCharsets.UTF_8));

        return ResponseEntity.ok()
                .headers(httpHeaders)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(quotationQueryService.readQuotationSituationExcel(startDate, endDate, clientName));
    }
}
