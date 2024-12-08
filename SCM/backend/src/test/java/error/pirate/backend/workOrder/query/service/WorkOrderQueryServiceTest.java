package error.pirate.backend.workOrder.query.service;

import error.pirate.backend.workOrder.query.dto.WorkOrderFilterDTO;
import error.pirate.backend.workOrder.query.dto.WorkOrderListResponse;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class WorkOrderQueryServiceTest {

    @Autowired
    private WorkOrderQueryService workOrderService;

    @DisplayName("회원 전체 목록 조회 테스트")
    @Test
    void readWorkOrderList() {
        // Given : 테스트 초기 설정
        WorkOrderFilterDTO filter = new WorkOrderFilterDTO();
        filter.setWarehouseName(null);
        filter.setWorkOrderStatus(null);
        filter.setStartDate(LocalDateTime.of(2024, 3, 1, 0, 0)); // 테스트용 날짜 범위 시작
        filter.setEndDate(LocalDateTime.of(2024, 12, 31, 23, 59)); // 테스트용 날짜 범위 시작
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
}