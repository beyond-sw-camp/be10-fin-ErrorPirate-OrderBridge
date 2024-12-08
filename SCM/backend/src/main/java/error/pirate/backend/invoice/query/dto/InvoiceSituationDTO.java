package error.pirate.backend.invoice.query.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class InvoiceSituationDTO {
    private Long invoiceSeq;
    private LocalDate invoiceSaleDate;
    private String invoiceName;
    private Integer invoiceTotalQuantity;
    private Integer invoiceExtendedPrice;
    private String clientName;
    private String invoiceNote;
}
