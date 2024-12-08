package error.pirate.backend.salesOrder.command.domain.service;

import error.pirate.backend.salesOrder.command.domain.aggregate.entity.SalesOrder;
import error.pirate.backend.salesOrder.command.domain.repository.SalesOrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SalesOrderDomainService {
    
    private final SalesOrderRepository salesOrderRepository;

    /* 주문서명으로 주문서 불러오기 */
    public SalesOrder findBySalesOrderName(String salesOrderName) {
        if ( salesOrderRepository.findBySalesOrderName(salesOrderName) == null ) {
            throw new IllegalArgumentException("SalesOrder not found: " + salesOrderName);
        }
        return salesOrderRepository.findBySalesOrderName(salesOrderName);
    }
}
