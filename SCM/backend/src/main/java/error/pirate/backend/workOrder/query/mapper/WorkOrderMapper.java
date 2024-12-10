package error.pirate.backend.workOrder.query.mapper;


import error.pirate.backend.workOrder.query.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface WorkOrderMapper {

    /* 작업지시서 목록 조회 */
    List<WorkOrderListDTO> readWorkOrderList(@Param("filter") WorkOrderFilterDTO filter,
                                             @Param("offset") int offset);

    /* 작업지시서 목록 개수 조회 */
    long readWorkOrderListCount(@Param("filter") WorkOrderFilterDTO filter);

    /* 작업지시서 상세 조회 */
    WorkOrderDetailDTO readWorkOrder(@Param("workOrderSeq") Long workOrderSeq);

    /* 작업지시서 상세품목 조회 */
    WorkOrderItemDTO readItemByWorkOrderSeq(@Param("workOrderSeq") Long workOrderSeq);

    /* 작업지시서 현황 조회 */
    List<WorkOrderSituationDTO> readWorkOrderSituations(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("clientName") String clientName,
            @Param("warehouseName") String warehouseName);

    /* 작업지시서 전표 조회 */
    WorkOrderSlipDTO readWorkOrderSlip(@Param("workOrderSeq") Long workOrderSeq);

    /* 작업지시서 전표 품목 조회*/
    List<WorkOrderSlipItemDTO> readWorkOrderSlipItemByWorkOrderSeq(@Param("workOrderSeq") Long workOrderSeq);
}
