package error.pirate.backend.workOrder.query.mapper;


import error.pirate.backend.workOrder.query.dto.WorkOrderFilterDTO;
import error.pirate.backend.workOrder.query.dto.WorkOrderListDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface WorkOrderMapper {

    List<WorkOrderListDTO> readWorkOrderList(@Param("filter") WorkOrderFilterDTO filter,
                                             @Param("offset") int offset);

    long readWorkOrderListCount(@Param("filter") WorkOrderFilterDTO filter);
}
