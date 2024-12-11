package error.pirate.backend.productionReceiving.query.service;

import error.pirate.backend.productionReceiving.command.domain.aggregate.entity.ProductionReceivingStatus;
import error.pirate.backend.productionReceiving.query.dto.ProductionReceivingListRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@SpringBootTest
@Transactional
class ProductionReceivingQueryServiceTest {

    @Autowired
    ProductionReceivingQueryService productionReceivingQueryService;

    private static Stream<Arguments> readProductionReceivingListParam() {
        ProductionReceivingStatus[] statusArr =  new ProductionReceivingStatus[2];
        statusArr[0] = ProductionReceivingStatus.BEFORE;
        statusArr[1] = ProductionReceivingStatus.AFTER;

        return Stream.of(
                arguments("2024-12-09", "2024-12-12", "2024/12/09-1", statusArr, 0, 10),
                arguments(null, null, "2024/12/09-1", statusArr, 0, 4),
                arguments(null, "2024-12-12", "2024/12/09-1", statusArr, 0, 8),
                arguments("2024-12-09", "2024-12-12", null, statusArr, 0, 5),
                arguments("2024-12-09", "2024-12-12", "2024/12/09-1", null, 0, 1),
                arguments(null, null, null, null, 0, 20)
        );
    }

    @DisplayName("생산입고 리스트 조회")
    @ParameterizedTest()
    @MethodSource("readProductionReceivingListParam")
    void readProductionReceivingList(LocalDate searchStartDate,
                                 LocalDate searchEndDate, String searchName,
                                 ProductionReceivingStatus[] searchStatus,
                                 int offset, int limit) {
        ProductionReceivingListRequest request = new ProductionReceivingListRequest(
            searchStartDate, searchEndDate, searchName, searchStatus
        );

        Pageable pageable = PageRequest.of(offset, limit);
        assertDoesNotThrow(() -> productionReceivingQueryService.readProductionReceivingList(request, pageable));
    }

    private static Stream<Arguments> readProductionReceivingParam() {
        return Stream.of(
                arguments(1L),
                arguments(2L),
                arguments(3L)
        );
    }

    @DisplayName("생산입고 상세 조회")
    @ParameterizedTest()
    @MethodSource("readProductionReceivingParam")
    void readProductionReceiving(Long productionReceivingSeq) {

        assertDoesNotThrow(() -> productionReceivingQueryService.readProductionReceiving(productionReceivingSeq));
    }
}