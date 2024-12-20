package error.pirate.backend.productionReceiving.query.dto;

import error.pirate.backend.productionReceiving.command.domain.aggregate.entity.ProductionReceivingStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductionReceivingListResponse {
    private Page<ProductionReceivingListDTO> productionReceivingList;
    private List<ProductionReceivingStatus.ProductionReceivingStatusResponse> productionReceivingStatusList;
}
