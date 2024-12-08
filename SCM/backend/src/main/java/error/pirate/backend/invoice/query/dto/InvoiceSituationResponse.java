package error.pirate.backend.invoice.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class InvoiceSituationResponse {
    private List<InvoiceSituationDTO> invoiceSituationList;
}
