package error.pirate.backend.workOrder.command.domain.aggregate.entity;

import error.pirate.backend.client.command.domain.aggregate.entity.Client;
import error.pirate.backend.item.command.domain.aggregate.entity.Item;
import error.pirate.backend.salesOrder.command.domain.aggregate.entity.SalesOrder;
import error.pirate.backend.user.command.domain.aggregate.entity.User;
import error.pirate.backend.warehouse.command.domain.aggregate.entity.Warehouse;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_work_order") // 작업지시서
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WorkOrder {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long workOrderSeq;
    
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "client_seq")
    private Client client; // 거래처(납품처)

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq")
    private User user; // 작업지시서 담당자

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "sales_order_seq")
    private SalesOrder salesOrder; // 주문서

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_seq")
    private Warehouse warehouse; // 창고

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "item_seq")
    private Item item; // 품목

    private String workOrderName; // 작업지시서 명

    @CreatedDate
    private LocalDateTime workOrderRegDate; // 작업지시서 등록일

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime workOrderModDate; // 작업지시서 수정일

    private LocalDateTime workOrderIndicatedDate; // 작업지시서 작업 지시일

    private LocalDateTime workOrderEndDate; // 작업지시서 작업 완료일

    private LocalDateTime workOrderDueDate; // 작업지시서 납기일

    private Integer workOrderIndicatedQuantity; // 작업지시서 주문수량

    private int workOrderProductionQuantity; // 작업지시서 생산수량

    private Integer workOrderWorkQuantity; // 작업지시서 작업완료수량

    @Enumerated(EnumType.STRING)
    private WorkOrderStatus workOrderStatus; // 작업지시서 상태

    private Integer workOrderPrice; // 작업지시서 총금액

    private String workOrderNote; // 작업지시서 비고
}
