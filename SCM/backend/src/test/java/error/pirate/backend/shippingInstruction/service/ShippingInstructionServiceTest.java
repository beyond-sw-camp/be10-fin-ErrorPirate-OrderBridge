package error.pirate.backend.shippingInstruction.service;

import error.pirate.backend.shippingInstruction.query.dto.*;
import error.pirate.backend.shippingInstruction.query.mapper.ShippingInstructionMapper;
import error.pirate.backend.shippingInstruction.query.service.ShippingInstructionQueryService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ShippingInstructionServiceTest {

    @Autowired
    ShippingInstructionQueryService shippingInstructionQueryService;

    @MockBean
    ShippingInstructionMapper shippingInstructionMapper;

    private static List<ShippingInstructionListDTO> mockShoppingInstructionList;
    private static ShippingInstructionDTO mockShippingInstruction;
    private static List<ShippingInstructionItemDTO> mockItemList;

    @BeforeAll
    public static void setUp() {
        // mockShoppingInstructionList 데이터 설정
        mockShoppingInstructionList = List.of(
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

        // mockShippingInstruction 데이터 설정
        mockShippingInstruction = ShippingInstructionDTO.builder()
                .shippingInstructionSeq(1L)
                .shippingInstructionName("출하지시서1")
                .shippingInstructionStatus("결재 전")
                .shippingInstructionScheduledShipmentDate(LocalDateTime.of(2024, 12, 15, 12,0,0))
                .clientName("고객사 A")
                .shippingInstructionTotalQuantity(100)
                .shippingInstructionAddress("주소")
                .shippingInstructionNote("비고")
                .build();

        // mockItemList 데이터 설정
        mockItemList = List.of(
                ShippingInstructionItemDTO.builder()
                        .itemName("품목1")
                        .itemDivision("Division1")
                        .itemPrice(1000)
                        .shippingInstructionItemQuantity(10)
                        .shippingInstructionItemNote("비고1")
                        .itemTotalQuantity(100)
                        .build(),
                ShippingInstructionItemDTO.builder()
                        .itemName("품목2")
                        .itemDivision("Division2")
                        .itemPrice(2000)
                        .shippingInstructionItemQuantity(5)
                        .shippingInstructionItemNote("비고2")
                        .itemTotalQuantity(50)
                        .build()
        );
    }


    @Test
    @DisplayName("출하지시서 리스트 조회 서비스 테스트")
    void testReadShippingInstructionList() throws Exception {
        // Given
        int page = 1;
        int size = 10;
        LocalDate startDate = LocalDate.of(2024, 12, 1);
        LocalDate endDate = LocalDate.of(2024, 12, 31);
        String clientName = null;
        String shippingInstructionStatus = null;

        // selectShippingInstructionList 메서드가 반환할 값 설정
        Mockito.when(shippingInstructionMapper.selectShippingInstructionList(
                        anyInt(), anyInt(), eq(startDate), eq(endDate), eq(clientName), eq(shippingInstructionStatus)))
                .thenReturn(mockShoppingInstructionList);

        long totalItems = 2L;  // 전체 아이템 수 설정 (mock 데이터와 일치)

        // countShippingInstruction 메서드가 반환할 값 설정
        Mockito.when(shippingInstructionMapper.countShippingInstruction(eq(startDate), eq(endDate), eq(clientName), eq(shippingInstructionStatus)))
                .thenReturn(totalItems);

        // When
        ShippingInstructionListResponse response = shippingInstructionQueryService.readShippingInstructionList(page, size, startDate, endDate, clientName, shippingInstructionStatus);

        // Then
        assertNotNull(response);
        assertEquals(2, response.getShippingInstructionList().size());  // 리스트 크기 확인
        // 첫 번째 리스트 내용 검증
        assertThat(response.getShippingInstructionList().get(0).getShippingInstructionSeq()).isEqualTo(1L);
        assertThat(response.getShippingInstructionList().get(0).getShippingInstructionName()).isEqualTo("출하지시서1");
        assertThat(response.getShippingInstructionList().get(0).getShippingInstructionStatus()).isEqualTo("결재 전");
        assertThat(response.getShippingInstructionList().get(0).getShippingInstructionScheduledShipmentDate()).isEqualTo(LocalDate.of(2024, 12, 15));
        assertThat(response.getShippingInstructionList().get(0).getClientName()).isEqualTo("고객사 A");
        assertThat(response.getShippingInstructionList().get(0).getItemName()).isEqualTo("품목1, 품목2");
        // 두 번째 리스트 내용 검증
        assertThat(response.getShippingInstructionList().get(1).getShippingInstructionSeq()).isEqualTo(2L);
        assertThat(response.getShippingInstructionList().get(1).getShippingInstructionName()).isEqualTo("출하지시서2");
        assertThat(response.getShippingInstructionList().get(1).getShippingInstructionStatus()).isEqualTo("결재 후");
        assertThat(response.getShippingInstructionList().get(1).getShippingInstructionScheduledShipmentDate()).isEqualTo(LocalDate.of(2024, 12, 20));
        assertThat(response.getShippingInstructionList().get(1).getClientName()).isEqualTo("고객사 B");
        assertThat(response.getShippingInstructionList().get(1).getItemName()).isEqualTo("품목3, 품목4");
        // 페이징 정보 검증
        assertEquals(page, response.getCurrentPage());
        assertEquals(1, response.getTotalPages());  // 전체 페이지 수 계산이 올바른지 확인
        assertEquals(totalItems, response.getTotalItems());  // 총 아이템 수 확인

        // 메서드 호출 여부 검증
        Mockito.verify(shippingInstructionMapper).selectShippingInstructionList(
                anyInt(), anyInt(), eq(startDate), eq(endDate), eq(clientName), eq(shippingInstructionStatus));
        Mockito.verify(shippingInstructionMapper).countShippingInstruction(eq(startDate), eq(endDate), eq(clientName), eq(shippingInstructionStatus));
    }

    @Test
    @DisplayName("출하지시서 상세 조회 서비스 테스트")
    void testReadShippingInstruction() {
        // Given
        long shippingInstructionSeq = 1L;

        // selectShippingInstructionByShippingInstructionSeq 메서드가 반환할 값 설정
        Mockito.when(shippingInstructionMapper.selectShippingInstructionByShippingInstructionSeq(eq(shippingInstructionSeq)))
                .thenReturn(mockShippingInstruction);

        // selectItemListByShippingInstructionSeq 메서드가 반환할 값 설정
        Mockito.when(shippingInstructionMapper.selectItemListByShippingInstructionSeq(eq(shippingInstructionSeq)))
                .thenReturn(mockItemList);

        // When
        ShippingInstructionResponse response = shippingInstructionQueryService.readShippingInstruction(shippingInstructionSeq);

        // Then
        assertNotNull(response);  // 응답 객체가 null이 아님을 확인
        assertEquals(shippingInstructionSeq, response.getShippingInstructionDTO().getShippingInstructionSeq());
        assertEquals("출하지시서1", response.getShippingInstructionDTO().getShippingInstructionName());
        assertEquals("결재 전", response.getShippingInstructionDTO().getShippingInstructionStatus());
        assertEquals(LocalDateTime.of(2024, 12, 15, 12, 0, 0), response.getShippingInstructionDTO().getShippingInstructionScheduledShipmentDate());
        assertEquals("고객사 A", response.getShippingInstructionDTO().getClientName());
        assertEquals(100, response.getShippingInstructionDTO().getShippingInstructionTotalQuantity());
        assertEquals("주소", response.getShippingInstructionDTO().getShippingInstructionAddress());
        assertEquals("비고", response.getShippingInstructionDTO().getShippingInstructionNote());
        assertEquals(2, response.getItemList().size());
        assertEquals("품목1", response.getItemList().get(0).getItemName());
        assertEquals("Division1", response.getItemList().get(0).getItemDivision());
        assertEquals(1000, response.getItemList().get(0).getItemPrice());
        assertEquals(10, response.getItemList().get(0).getShippingInstructionItemQuantity());
        assertEquals("비고1", response.getItemList().get(0).getShippingInstructionItemNote());
        assertEquals(100, response.getItemList().get(0).getItemTotalQuantity());
        assertEquals("품목2", response.getItemList().get(1).getItemName());
        assertEquals("Division2", response.getItemList().get(1).getItemDivision());
        assertEquals(2000, response.getItemList().get(1).getItemPrice());
        assertEquals(5, response.getItemList().get(1).getShippingInstructionItemQuantity());
        assertEquals("비고2", response.getItemList().get(1).getShippingInstructionItemNote());
        assertEquals(50, response.getItemList().get(1).getItemTotalQuantity());
    }
}