package error.pirate.backend.workOrder.query.service;

import error.pirate.backend.exception.CustomException;
import error.pirate.backend.exception.ErrorCodeType;
import error.pirate.backend.workOrder.query.dto.*;
import error.pirate.backend.workOrder.query.mapper.WorkOrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class WorkOrderQueryService {

    private final WorkOrderMapper workOrderMapper;

    /* 작업지시서 목록 조회 */
    @Transactional
    public WorkOrderListResponse readWorkOrderList(WorkOrderFilterDTO filter) {
        log.info("-------------- 작업지시서 목록조회 서비스 진입 :목록조회 필터링 조건 - filter: {} --------------", filter);

        // 시작일이 종료일보다 나중인 경우 에러처리
        if (filter.getStartDate().isAfter(filter.getEndDate())) {
            throw new CustomException(ErrorCodeType.INVALID_DATE_RANGE);
        }

        // 기본 날짜 값 설정
        if (filter.getStartDate() == null) {
            filter.setStartDate(LocalDate.of(2023, 1, 1));
        }
        if (filter.getEndDate() == null) {
            filter.setEndDate(LocalDate.now());
        }

        // 페이지 설정
        int offset = (filter.getPage() - 1) * filter.getSize();

        // 총 개수
        long totalItems = workOrderMapper.readWorkOrderListCount(filter);
        // 총 페이지 수
        int totalPages = (int) Math.ceil((double) totalItems / filter.getSize());

        // 작업지시서 목록 조회
        List<WorkOrderListDTO> workOrderList = workOrderMapper.readWorkOrderList(filter, offset);

        log.info("-------------- readWorkOrderList 완료 - 페이지에 조회된 작업지시서 수 : {}, 총 작업지시서 수 : {}, 총 페이지 수 : {} --------------",
                                                                            workOrderList.size(), totalItems, totalPages);

        return WorkOrderListResponse.builder()
                .workOrderList(workOrderList)
                .currentPage(filter.getPage())
                .totalPages(totalPages)
                .totalItems(totalItems)
                .build();
    }

    /* 작업지시서 상세 조회 */
    @Transactional
    public WorkOrderResponse readWorkOrder(Long workOrderSeq) {
        log.info("-------------- 작업지시서 상세조회 서비스 진입 - workOrderSeq: {} --------------", workOrderSeq);

        // 품목 제외 작업지시서 조회
        WorkOrderDetailDTO workOrderDetail = workOrderMapper.readWorkOrder(workOrderSeq);
        if (workOrderDetail == null) {
            throw new CustomException(ErrorCodeType.WORK_ORDER_NOT_FOUND);
        }

        // 해당 품목 조회
        WorkOrderItemDTO workOrderItem = workOrderMapper.readItemByWorkOrderSeq(workOrderSeq);

        return WorkOrderResponse.builder()
                .workOrderDetail(workOrderDetail)
                .workOrderItem(workOrderItem)
                .build();
    }

    /* 작업지시서 현황조회 */
    @Transactional(readOnly = true)
    public WorkOrderSituationResponse readWorkOrderSituation(LocalDate startDate, LocalDate endDate, String clientName, String warehouseName) {
        log.info("-------------- 작업지시서 현황조회 서비스 진입 필터링 조건- startDate: {}, endDate: {}, clientName: {}, warehouseName: {} --------------"
                , startDate, endDate, clientName, warehouseName);

        // 시작일이 종료일보다 나중인 경우 에러처리
        if (startDate.isAfter(endDate)) {
            throw new CustomException(ErrorCodeType.INVALID_DATE_RANGE);
        }

        // 데이터 조회
        List<WorkOrderSituationDTO> situations = workOrderMapper.readWorkOrderSituations(startDate, endDate, clientName, warehouseName);

        // 월별 그룹화
        Map<String, List<WorkOrderSituationDTO>> groupedByMonth = situations.stream()
                                .collect(Collectors.groupingBy(situation ->
                                   situation.getWorkOrderIndicatedDate().format(DateTimeFormatter.ofPattern("yyyy/MM"))));

        // 월별 데이터 정렬
        List<Map.Entry<String, List<WorkOrderSituationDTO>>> sortedByMonth = groupedByMonth.entrySet().stream()
                .sorted(Map.Entry.comparingByKey()) // 키(월별 데이터)를 기준으로 정렬
                .toList();

        // 월별 데이터 생성
        List<MonthlyWorkOrderSituationDTO> monthlySituations = sortedByMonth.stream()
                .map(entry -> MonthlyWorkOrderSituationDTO.builder()
                        .month(entry.getKey())
                        .situations(entry.getValue())
                        .monthlyTotalQuantity(entry.getValue().stream()
                                .mapToInt(WorkOrderSituationDTO::getWorkOrderIndicatedQuantity)
                                .sum())
                        .build())
                .collect(Collectors.toList());

        // 응답 DTO 생성
        WorkOrderSituationResponse response = WorkOrderSituationResponse.builder()
                .monthlySituations(monthlySituations)
                .totalQuantity(situations.stream()
                        .mapToInt(WorkOrderSituationDTO::getWorkOrderIndicatedQuantity)
                        .sum())
                .build();

        log.info("-------------- 작업지시서 현황 서비스 완료: 총 작업지시서 수량 - {}, 월별 그룹 - {} --------------",
                response.getTotalQuantity(), monthlySituations.size());

        return response;
    }

    /* 전표 조회 */
    public WorkOrderSlipResponse readWorkOrderSlip(Long workOrderSeq) {
        log.info("-------------- 작업지시서 전표조회 서비스 진입 - workOrderSeq: {} --------------", workOrderSeq);

        // BOM(하위) 품목 제외 전표(완제품이름 포함) 조회
        WorkOrderSlipDTO workOrderSlip = workOrderMapper.readWorkOrderSlip(workOrderSeq);
        if (workOrderSlip == null) {
            throw new CustomException(ErrorCodeType.WORK_ORDER_NOT_FOUND);
        }

        // BOM 품목 조회
        List<WorkOrderSlipItemDTO> bomItems  = workOrderMapper.readWorkOrderSlipItemByWorkOrderSeq(workOrderSeq);

        return WorkOrderSlipResponse.builder()
                .slipDTO(workOrderSlip)
                .items(bomItems)
                .build();
    }
}
