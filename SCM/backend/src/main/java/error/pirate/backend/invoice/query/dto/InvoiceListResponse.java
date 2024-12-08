package error.pirate.backend.invoice.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class InvoiceListResponse {
    private List<InvoiceListItemDTO> invoice;
    private int currentPage;
    private int totalPages;
    private int totalInvoice;
}
