package error.pirate.backend.shippingInstruction.command.domain.aggregate.entity;

import error.pirate.backend.salesOrder.command.domain.aggregate.entity.SalesOrder;
import error.pirate.backend.user.command.domain.aggregate.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_shipping_instruction") // 출하 지시서
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShippingInstruction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shippingInstructionSeq;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "sales_order_seq")
    private SalesOrder salesOrder; // 주문서

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq")
    private User user; // 출하 지시서 담당자

    private String shippingInstructionName; // 출하지시서 명

    private String shippingInstructionAddress; // 출하지시서 주소

    @Enumerated(EnumType.STRING)
    private ShippingInstructionStatus shippingInstructionStatus; // 출하지시서 상태

    @CreatedDate
    private LocalDateTime shippingInstructionRegDate; // 출하 지시서 등록일

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime shippingInstructionModDate; // 출하 지시서 수정일

    private LocalDateTime shippingInstructionScheduledShipmentDate; // 출하 지시서 출하 예정일

    private int shippingInstructionTotalQuantity; // 출하 지시서 총 수량

    private String shippingInstructionNote; // 출하 지시서 비고

    private ShippingInstruction(SalesOrder salesOrder, User user, String shippingInstructionName, String shippingInstructionAddress, String status, LocalDateTime shippingInstructionScheduledShipmentDate, int itemTotalQuantity, String shippingInstructionNote) {
        this.salesOrder = salesOrder;
        this.user = user;
        this.shippingInstructionName = shippingInstructionName;
        this.shippingInstructionAddress = shippingInstructionAddress;
        this.shippingInstructionScheduledShipmentDate = shippingInstructionScheduledShipmentDate;
        this.shippingInstructionStatus = ShippingInstructionStatus.결재전;
        this.shippingInstructionTotalQuantity = itemTotalQuantity;
        this.shippingInstructionNote = shippingInstructionNote;
    }

    public static ShippingInstruction create(SalesOrder salesOrder, User user, String shippingInstructionName, String shippingInstructionAddress, String status, LocalDateTime shippingInstructionScheduledShipmentDate, int itemTotalQuantity, String shippingInstructionNote) {
        return new ShippingInstruction(salesOrder, user, shippingInstructionName, shippingInstructionAddress, status, shippingInstructionScheduledShipmentDate, itemTotalQuantity, shippingInstructionNote);
    }
}
