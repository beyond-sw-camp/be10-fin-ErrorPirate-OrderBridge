package error.pirate.backend.invoice.query.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class InvoiceListItemDTO {
    private Long invoiceSeq;
    private String invoiceName;
    private String itemName;
    private String clientName;
    private LocalDate invoiceSaleDate;
}
