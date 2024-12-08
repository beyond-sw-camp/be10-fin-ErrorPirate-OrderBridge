package error.pirate.backend.workOrder.query.controller;

import error.pirate.backend.workOrder.query.dto.WorkOrderFilterDTO;
import error.pirate.backend.workOrder.query.dto.WorkOrderListDTO;
import error.pirate.backend.workOrder.query.dto.WorkOrderListResponse;
import error.pirate.backend.workOrder.query.service.WorkOrderQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/workOrder")
@Slf4j
@Tag(name = "Work_Order", description = "작업지시서 관리")
public class WorkOrderQueryController {

    private final WorkOrderQueryService workOrderQueryService;

    /* 목록조회 */
    @GetMapping
    @Operation(summary = "작업지시서 목록조회", description = "작업지시서 목록을 조회한다.")
    public ResponseEntity<WorkOrderListResponse> readWorkOrderList (
            @ModelAttribute WorkOrderFilterDTO filter) {

        log.info("-------------- GET /api/v1/workOrder 작업지시서 목록조회 요청 --------------");
        WorkOrderListResponse response = workOrderQueryService.readWorkOrderList(filter);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
