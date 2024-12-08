package error.pirate.backend.salesOrder.command.domain.aggregate.entity;

import error.pirate.backend.item.command.domain.aggregate.entity.Item;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_sales_order_item") // 주문서 품목
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SalesOrderItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long salesOrderItemSeq;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "sales_order_seq")
    private SalesOrder salesOrder; // 주문서

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "item_seq")
    private Item item; // 품목

    private int salesOrderItemQuantity; // 주문서 품목 수량

    private int salesOrderItemPrice; // 주문서 품목 단가

    private String salesOrderItemNote; // 주문서 품목 비고
}
