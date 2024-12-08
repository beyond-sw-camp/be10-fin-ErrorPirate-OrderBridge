package error.pirate.backend.invoice.query.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class InvoiceDTO {
    private Long invoiceSeq;
    private String invoiceName;
    private String clientName;
    private Integer invoiceExtendedPrice;
    private Integer invoiceTotalQuantity;
    private String userName;
    private LocalDateTime invoiceSaleDate;
    private String invoiceNote;
}
