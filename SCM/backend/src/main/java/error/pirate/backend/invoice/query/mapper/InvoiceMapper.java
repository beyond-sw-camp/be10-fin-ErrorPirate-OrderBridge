package error.pirate.backend.invoice.query.mapper;

import error.pirate.backend.invoice.query.dto.InvoiceDTO;
import error.pirate.backend.invoice.query.dto.InvoiceItemDTO;
import error.pirate.backend.invoice.query.dto.InvoiceListItemDTO;
import error.pirate.backend.invoice.query.dto.InvoiceSituationDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface InvoiceMapper {

    // 거래 명세서 목록 조회
    List<InvoiceListItemDTO> selectInvoiceList(
            @Param("offset") int offset,
            @Param("limit") Integer limit,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("clientName") String clientName);

    // 거래 명세서 목록 개수 조회
    int countInvoiceList(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("clientName") String clientName);

    // 거래 명세서 상세 조회
    InvoiceDTO selectInvoice(
            @Param("invoiceSeq") Long invoiceSeq);

    // 거래 명세서 상세 품목 조회
    List<InvoiceItemDTO> selectInvoiceItem(
            @Param("invoiceSeq") Long invoiceSeq);

    // 거래 명세서 현황 조회
    List<InvoiceSituationDTO> selectInvoiceSituation(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("clientName") String clientName);
}
