package error.pirate.backend.quotation.query.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@SpringBootTest
class QuotationQueryServiceTest {

    @Autowired
    private QuotationQueryService quotationQueryService;

    private static Stream<Arguments> getQuotationListParam() {
        return Stream.of(
                arguments(1, 10, null, null, null, null),
                arguments(2, 10, null, null, null, null),
                arguments(1, 10, "2024-10-20", null, null, null),
                arguments(1, 10, null, "2024-11-25", null, null),
                arguments(1, 10, "2024-10-20", "2024-11-25", null, null),
                arguments(1, 10, null, null, "대한항공", null),
                arguments(1, 10, null, null, null, "반려")
        );
    }

    @DisplayName("견적서 목록 조회")
    @ParameterizedTest(autoCloseArguments = true)
    @MethodSource("getQuotationListParam")
    void getQuotationList(Integer page, Integer size,
                          LocalDate startDate, LocalDate endDate,
                          String clientName, String quotationStatus) {

        assertDoesNotThrow(() -> quotationQueryService.getQuotationList(
                page, size, startDate, endDate, clientName, quotationStatus));
    }

    private static Stream<Arguments> getQuotationParam() {
        return Stream.of(
                arguments(1L),
                arguments(2L),
                arguments(3L),
                arguments(4L),
                arguments(5L)
        );
    }

    @DisplayName("견적서 상세 조회")
    @ParameterizedTest(autoCloseArguments = true)
    @MethodSource("getQuotationParam")
    void getQuotation(Long quotationSeq) {

        assertDoesNotThrow(() -> quotationQueryService.getQuotation(quotationSeq));
    }

    private static Stream<Arguments> getQuotationSituationParam() {
        return Stream.of(
                arguments("2024-10-20", null, null),
                arguments(null, "2024-11-25", null),
                arguments("2024-10-20", "2024-11-25", null),
                arguments(null, null, "대한항공")
        );
    }

    @DisplayName("견적서 현황 조회")
    @ParameterizedTest(autoCloseArguments = true)
    @MethodSource("getQuotationSituationParam")
    void getQuotationSituation(LocalDate startDate, LocalDate endDate, String clientName) {

        assertDoesNotThrow(() -> quotationQueryService.getQuotationSituation(startDate, endDate, clientName));
    }
}