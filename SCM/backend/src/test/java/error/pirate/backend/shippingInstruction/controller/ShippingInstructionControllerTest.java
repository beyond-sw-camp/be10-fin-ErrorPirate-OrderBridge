package error.pirate.backend.shippingInstruction.controller;

import error.pirate.backend.shippingInstruction.query.dto.*;
import error.pirate.backend.shippingInstruction.query.service.ShippingInstructionQueryService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ShippingInstructionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShippingInstructionQueryService shippingInstructionQueryService; // MockBean 설정

    private static ShippingInstructionListResponse shippingInstructionListResponse;
    private static ShippingInstructionResponse shippingInstructionResponse;

    @BeforeAll
    static void setUp() {
        // 테스트 데이터
        List<ShippingInstructionListDTO> shippingInstructionList = List.of(
                ShippingInstructionListDTO.builder()
                        .shippingInstructionSeq(1L)
                        .shippingInstructionName("출하지시서1")
                        .shippingInstructionStatus("결재 전")
                        .shippingInstructionScheduledShipmentDate(LocalDate.of(2024, 12, 15))
                        .clientName("고객사 A")
                        .itemName("품목1, 품목2")
                        .build(),
                ShippingInstructionListDTO.builder()
                        .shippingInstructionSeq(2L)
                        .shippingInstructionName("출하지시서2")
                        .shippingInstructionStatus("결재 후")
                        .shippingInstructionScheduledShipmentDate(LocalDate.of(2024, 12, 20))
                        .clientName("고객사 B")
                        .itemName("품목3, 품목4")
                        .build()
        );

        shippingInstructionListResponse = ShippingInstructionListResponse.builder()
                .shippingInstructionList(shippingInstructionList) // List 포함
                .currentPage(1)
                .totalPages(10)
                .totalItems(2)
                .build();

        ShippingInstructionDTO shippingInstruction
                = ShippingInstructionDTO.builder()
                .shippingInstructionSeq(1L)
                .shippingInstructionName("출하지시서1")
                .shippingInstructionStatus("결재 전")
                .shippingInstructionScheduledShipmentDate(LocalDateTime.of(2024, 12, 15, 10, 30))
                .clientName("고객사 A")
                .shippingInstructionTotalQuantity(15)
                .shippingInstructionAddress("서울특별시 강남구")
                .shippingInstructionNote("조심히 배송")
                .build();

        List<ShippingInstructionItemDTO> items = List.of(
                ShippingInstructionItemDTO.builder()
                        .itemName("품목1")
                        .itemDivision("A")
                        .itemPrice(1000)
                        .shippingInstructionItemQuantity(10)
                        .shippingInstructionItemNote("특별 관리")
                        .itemTotalQuantity(20)
                        .build(),
                ShippingInstructionItemDTO.builder()
                        .itemName("품목2")
                        .itemDivision("B")
                        .itemPrice(2000)
                        .shippingInstructionItemQuantity(5)
                        .shippingInstructionItemNote("고온 주의")
                        .itemTotalQuantity(10)
                        .build()
        );

        shippingInstructionResponse = ShippingInstructionResponse.builder()
                .shippingInstructionDTO(shippingInstruction)
                .itemList(items)
                .build();
    }

    @Test
    @DisplayName("출하지시서 리스트 조회 컨트롤러 테스트")
    void testReadShippingInstructionList() throws Exception {
        // Mock 데이터 생성 = 결과값
        ShippingInstructionListResponse response = shippingInstructionListResponse;

        // Given
        given(shippingInstructionQueryService.readShippingInstructionList(
                1, 10, null, null, null, null))
                .willReturn(response);

        // When: 출하지시서 조회 요청을 보냄
        mockMvc.perform(get("/api/v1/shipping-instruction"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shippingInstructionList[0].shippingInstructionSeq").value(1L))
                .andExpect(jsonPath("$.shippingInstructionList[0].shippingInstructionName").value("출하지시서1"))
                .andExpect(jsonPath("$.shippingInstructionList[0].shippingInstructionStatus").value("결재 전"))
                .andExpect(jsonPath("$.shippingInstructionList[0].shippingInstructionScheduledShipmentDate").value("2024-12-15"))
                .andExpect(jsonPath("$.shippingInstructionList[0].clientName").value("고객사 A"))
                .andExpect(jsonPath("$.shippingInstructionList[0].itemName").value("품목1, 품목2"))
                .andExpect(jsonPath("$.shippingInstructionList[1].shippingInstructionSeq").value(2L))
                .andExpect(jsonPath("$.shippingInstructionList[1].shippingInstructionName").value("출하지시서2"))
                .andExpect(jsonPath("$.shippingInstructionList[1].shippingInstructionStatus").value("결재 후"))
                .andExpect(jsonPath("$.shippingInstructionList[1].shippingInstructionScheduledShipmentDate").value("2024-12-20"))
                .andExpect(jsonPath("$.shippingInstructionList[1].clientName").value("고객사 B"))
                .andExpect(jsonPath("$.shippingInstructionList[1].itemName").value("품목3, 품목4"))
                .andExpect(jsonPath("$.currentPage").value(1))
                .andExpect(jsonPath("$.totalPages").value(10))
                .andExpect(jsonPath("$.totalItems").value(2))
                .andDo(print());

        // Then: shippingInstructionQueryService의 readShippingInstructionList 메서드가 올바르게 호출되었는지 검증
        verify(shippingInstructionQueryService).readShippingInstructionList(
                1, 10, null, null, null, null);
    }

    @Test
    @DisplayName("출하지시서 상세 조회 컨트롤러 테스트")
    void testReadShippingInstruction() throws Exception {
        // Mock 데이터 생성
        ShippingInstructionResponse response = shippingInstructionResponse;

        // Given
        long shippingInstructionSeq = 1L;
        given(shippingInstructionQueryService.readShippingInstruction(shippingInstructionSeq))
                .willReturn(response);

        // When: 출하지시서 상세 조회 요청을 보냄
        mockMvc.perform(get("/api/v1/shipping-instruction/" + shippingInstructionSeq))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shippingInstructionDTO.shippingInstructionSeq").value(1L))
                .andExpect(jsonPath("$.shippingInstructionDTO.shippingInstructionName").value("출하지시서1"))
                .andExpect(jsonPath("$.shippingInstructionDTO.shippingInstructionStatus").value("결재 전"))
                .andExpect(jsonPath("$.shippingInstructionDTO.shippingInstructionScheduledShipmentDate").value("2024-12-15T10:30:00"))
                .andExpect(jsonPath("$.shippingInstructionDTO.clientName").value("고객사 A"))
                .andExpect(jsonPath("$.shippingInstructionDTO.shippingInstructionTotalQuantity").value(15))
                .andExpect(jsonPath("$.shippingInstructionDTO.shippingInstructionAddress").value("서울특별시 강남구"))
                .andExpect(jsonPath("$.shippingInstructionDTO.shippingInstructionNote").value("조심히 배송"))
                .andExpect(jsonPath("$.itemList[0].itemName").value("품목1"))
                .andExpect(jsonPath("$.itemList[0].itemDivision").value("A"))
                .andExpect(jsonPath("$.itemList[0].itemPrice").value(1000))
                .andExpect(jsonPath("$.itemList[0].shippingInstructionItemQuantity").value(10))
                .andExpect(jsonPath("$.itemList[0].shippingInstructionItemNote").value("특별 관리"))
                .andExpect(jsonPath("$.itemList[0].itemTotalQuantity").value(20))
                .andExpect(jsonPath("$.itemList[1].itemName").value("품목2"))
                .andExpect(jsonPath("$.itemList[1].itemDivision").value("B"))
                .andExpect(jsonPath("$.itemList[1].itemPrice").value(2000))
                .andExpect(jsonPath("$.itemList[1].shippingInstructionItemQuantity").value(5))
                .andExpect(jsonPath("$.itemList[1].shippingInstructionItemNote").value("고온 주의"))
                .andExpect(jsonPath("$.itemList[1].itemTotalQuantity").value(10))
                .andDo(print());

        // Then: shippingInstructionQueryService의 readShippingInstruction 메서드가 올바르게 호출되었는지 검증
        verify(shippingInstructionQueryService).readShippingInstruction(shippingInstructionSeq);
    }

}