package error.pirate.backend.quotation.query.service;

import error.pirate.backend.common.ExcelDownLoad;
import error.pirate.backend.quotation.command.domain.aggregate.entity.QuotationStatus;
import error.pirate.backend.quotation.query.dto.*;
import error.pirate.backend.quotation.query.mapper.QuotationMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class QuotationQueryService {

    private final ModelMapper modelMapper;
    private final QuotationMapper quotationMapper;
    private final ExcelDownLoad excelDownBody;

    // 견적서 목록 조회
    public QuotationListResponse readQuotationList(
            Integer page, Integer size,
            LocalDate startDate, LocalDate endDate,
            String clientName, List<QuotationStatus> quotationStatus) {

        // 견적서 목록 조회
        List<QuotationListItemDTO> quotationList = quotationMapper.selectQuotationList(
                (page - 1) * size, size, startDate, endDate, clientName, quotationStatus);

        // 견적서 목록 개수 조회
        int totalQuotation = quotationMapper.countQuotationList(startDate, endDate, clientName, quotationStatus);

        return new QuotationListResponse(
                quotationList,
                page,
                (int) Math.ceil((double) totalQuotation / size),
                totalQuotation
        );
    }

    // 견적서 상세 조회
    public QuotationResponse readQuotation(Long quotationSeq) {

        // 견적서 상세 조회
        QuotationResponse response = modelMapper.map(
                quotationMapper.selectQuotation(quotationSeq), QuotationResponse.class);

        // 견적서 품목 목록 조회
        response.setQuotationItem(quotationMapper.selectQuotationItem(quotationSeq));

        return response;
    }

    // 견적서 현황 조회
    public List<QuotationSituationResponse> readQuotationSituation(
            LocalDate startDate, LocalDate endDate, String clientName) {

        // 견적서 현황 조회
        return quotationMapper.selectQuotationSituation(startDate, endDate, clientName);
    }

    // 견적서 합계 계산
    public QuotationCalculateSumDTO calculateSum(Long quotationSeq) {
        return quotationMapper.calculateSum(quotationSeq);
    }

    // 견적서 목록 엑셀 다운로드
    public byte[] readQuotationExcel(
            LocalDate startDate, LocalDate endDate, String clientName, List<QuotationStatus> quotationStatus) {

        return excelDownBody.writeCells(
                new String[] {"등록", "수정", "이름", "상태", "견적일", "만료일", "총 수량", "총 가격", "비고"},
                quotationMapper.selectQuotationExcel(startDate, endDate, clientName, quotationStatus)
        );
    }

    // 견적서 현황 엑셀 다운로드
    public byte[] readQuotationSituationExcel(LocalDate startDate, LocalDate endDate, String clientName) {

        return excelDownBody.writeCells(
                new String[] {"견적일", "이름", "총 수량", "총 가격", "거래처", "비고"},
                quotationMapper.selectQuotationSituationExcel(startDate, endDate, clientName)
        );
    }
}
