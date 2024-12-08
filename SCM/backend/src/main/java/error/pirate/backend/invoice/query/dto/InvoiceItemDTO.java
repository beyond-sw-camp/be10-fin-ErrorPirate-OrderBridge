package error.pirate.backend.invoice.query.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class InvoiceItemDTO {
    private Long invoiceItemSeq;
    private Long itemSeq;
    private String itemImageUrl;
    private String itemDivision;
    private String itemName;
    private Integer invoiceItemQuantity;
    private Integer invoiceItemPrice;
    private String invoiceItemNote;
}
