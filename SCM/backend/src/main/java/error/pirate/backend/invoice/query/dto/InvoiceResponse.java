package error.pirate.backend.invoice.query.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class InvoiceResponse {
    private Long invoiceSeq;
    private String invoiceName;
    private String clientName;
    private Integer invoiceExtendedPrice;
    private Integer invoiceTotalQuantity;
    private String userName;
    private LocalDateTime invoiceSaleDate;
    private String invoiceNote;
    @Setter
    private List<InvoiceItemDTO> invoiceItem;
}
