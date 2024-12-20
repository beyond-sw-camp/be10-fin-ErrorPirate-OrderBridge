package error.pirate.backend.quotation.command.domain.aggregate.entity;

import error.pirate.backend.client.command.domain.aggregate.entity.Client;
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
@Table(name = "tb_quotation") // 견적서
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Quotation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long quotationSeq;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "userSeq")
    private User user; // 견적서 담당자

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "clientSeq")
    private Client client; // 거래처

    private String quotationName; // 견적서 명

    @Enumerated(EnumType.STRING)
    private QuotationStatus quotationStatus; // 견적서 상태

    @CreatedDate
    private LocalDateTime quotationRegDate; // 견적서 등록일

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime quotationModDate; // 견적서 수정일

    private LocalDateTime quotationQuotationDate; // 견적서 견적일

    private LocalDateTime quotationEffectiveDate; // 견적서 유효일

    private Integer quotationExtendedPrice; // 견적서 총금액

    private Integer quotationTotalQuantity; // 견적서 총수량

    private String quotationNote; // 견적서 비고

    public Quotation(
            User user, Client client, String quotationName, LocalDateTime quotationQuotationDate,
            Integer quotationExtendedPrice, Integer quotationTotalQuantity, String quotationNote) {
        this.user = user;
        this.client = client;
        this.quotationName = quotationName;
        this.quotationStatus = QuotationStatus.BEFORE;
        this.quotationQuotationDate = quotationQuotationDate;
        this.quotationEffectiveDate = LocalDateTime.now().plusMonths(1);
        this.quotationExtendedPrice = quotationExtendedPrice;
        this.quotationTotalQuantity = quotationTotalQuantity;
        this.quotationNote = quotationNote;
    }

    public void updateQuotation(
            LocalDateTime quotationQuotationDate, Client client, User user, String quotationNote,
            Integer quotationExtendedPrice, Integer quotationTotalQuantity) {
        if (quotationQuotationDate != null) {
            this.quotationQuotationDate = quotationQuotationDate;
        }
        if (client != null) {
            this.client = client;
        }
        if (user != null) {
            this.user = user;
        }
        if (quotationNote != null) {
            this.quotationNote = quotationNote;
        }
        this.quotationExtendedPrice = quotationExtendedPrice;
        this.quotationTotalQuantity = quotationTotalQuantity;
    }

    public void delete() {
        this.quotationStatus = QuotationStatus.DELETE;
    }
}
