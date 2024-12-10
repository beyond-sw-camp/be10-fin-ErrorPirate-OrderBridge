package error.pirate.backend.workOrder.query.service;

import error.pirate.backend.workOrder.query.dto.WorkOrderFilterDTO;
import error.pirate.backend.workOrder.query.dto.WorkOrderListResponse;
import error.pirate.backend.workOrder.query.dto.WorkOrderResponse;
import error.pirate.backend.workOrder.query.dto.WorkOrderSlipResponse;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@SpringBootTest
@Transactional
class WorkOrderQueryServiceTest {

    @Autowired
    private WorkOrderQueryService workOrderService;

    // 목록조회 테스트 데이터 생성
    private static Stream<WorkOrderFilterDTO> getWorkOrderFilterArguments() {
        return Stream.of(
                // 페이지와 기본 조건만 설정
                new WorkOrderFilterDTO(null, null, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 12, 31), 1, 5),
                // 특정 상태 필터링
                new WorkOrderFilterDTO(null, "진행중", LocalDate.of(2024, 1, 1), LocalDate.of(2024, 12, 31), 1, 5),
                // 특정 창고 필터링
                new WorkOrderFilterDTO("생산창고", null, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 12, 31), 1, 5),
                // 특정 기간 필터링
                new WorkOrderFilterDTO(null, null, LocalDate.of(2024, 3, 1), LocalDate.of(2024, 6, 30), 1, 5),
                // 모든 조건 조합
                new WorkOrderFilterDTO("생산창고", "완료", LocalDate.of(2024, 1, 1), LocalDate.of(2024, 12, 31), 1, 5)
        );
    }

    @DisplayName("작업지시서 목록 조회 단위테스트")
    @ParameterizedTest
    @MethodSource("getWorkOrderFilterArguments")
    void readWorkOrderListTest_1(WorkOrderFilterDTO filter) {
        // 실제 서비스 호출, 예외가 발생하지 않는지 확인
        assertDoesNotThrow(() -> {
            WorkOrderListResponse response = workOrderService.readWorkOrderList(filter);
            System.out.println("조회된 작업지시서 수: " + response.getWorkOrderList().size());
        });
    }

    @DisplayName("회원 전체 목록 조회 통합테스트")
    @Test
    void readWorkOrderList_2() {
        // Given : 테스트 초기 설정
        WorkOrderFilterDTO filter = new WorkOrderFilterDTO();
        filter.setWarehouseName(null);
        filter.setWorkOrderStatus(null);
        filter.setStartDate(LocalDate.of(2024, 3, 1)); // 테스트용 날짜 범위 시작
        filter.setEndDate(LocalDate.of(2024, 12, 31)); // 테스트용 날짜 범위 시작
        filter.setPage(1);
        filter.setSize(5);

        // When : 메소드 실행
        WorkOrderListResponse response = workOrderService.readWorkOrderList(filter);

        // Then : (예상과) 비교
        assertNotNull(response, "조회결과가 있음을 확인");
        assertEquals(5, response.getWorkOrderList().size(), "조회된 작업지시서 개수가 맞는지 확인");
        assertEquals(17, response.getTotalItems(), "총 작업지시서 개수 확인");
        assertEquals(4, response.getTotalPages(), "총 페이지 수 확인");
        assertEquals(filter.getPage(), response.getCurrentPage(), "현재 페이지 확인");
    }

    @DisplayName("작업지시서 상세 조회 테스트")
    @Test
    void readWorkOrderTest() {
        // Given: 테스트 데이터 ID
        Long workOrderSeq = 2L; // 존재하는 테스트 데이터 ID를 사용

        // When: 상세 조회 호출
        WorkOrderResponse response = workOrderService.readWorkOrder(workOrderSeq);

        // Then: 결과 검증
        assertNotNull(response, "응답이 null이 아님");
        assertNotNull(response.getWorkOrderDetail(), "작업지시서 상세 정보가 존재해야 함");
        assertNotNull(response.getWorkOrderItem(), "작업지시서 품목 정보가 존재해야 함");
    }

    // 현황조회 테스트 데이터 생성
    private static Stream<org.junit.jupiter.params.provider.Arguments> getWorkOrderSituationArguments() {
        return Stream.of(
                arguments(LocalDate.of(2024, 1, 1), LocalDate.of(2024, 12, 31), null, null),
                arguments(LocalDate.of(2024, 6, 1), LocalDate.of(2024, 6, 30), "클라이언트A", null),
                arguments(LocalDate.of(2024, 1, 1), LocalDate.of(2024, 12, 31), null, "생산창고"),
                arguments(LocalDate.of(2024, 3, 1), LocalDate.of(2024, 6, 30), "클라이언트B", "창고B")
        );
    }

    @DisplayName("작업지시서 현황 조회 테스트")
    @ParameterizedTest
    @MethodSource("getWorkOrderSituationArguments")
    void readWorkOrderSituationTest(
            LocalDate startDate, LocalDate endDate, String clientName, String warehouseName) {
        // 테스트 대상 메서드 호출
        assertDoesNotThrow(
                () -> workOrderService.readWorkOrderSituation(startDate, endDate, clientName, warehouseName)
        );
    }

    @DisplayName("작업지시서 전표 조회 테스트")
    @Test
    void readWorkOrderSlipTest() {
        // Given: 테스트 데이터 ID
        Long workOrderSeq = 2L; // 존재하는 테스트 데이터 ID를 사용

        // When: 상세 조회 호출
        WorkOrderSlipResponse response = workOrderService.readWorkOrderSlip(workOrderSeq);

        // Then: 결과 검증
        assertNotNull(response, "응답이 null이 아님");
        assertNotNull(response.getSlipDTO(), "작업지시서 전표 정보가 존재해야 함");
        assertNotNull(response.getItems(), "작업지시서 전표 품목 정보가 존재해야 함");
    }


}