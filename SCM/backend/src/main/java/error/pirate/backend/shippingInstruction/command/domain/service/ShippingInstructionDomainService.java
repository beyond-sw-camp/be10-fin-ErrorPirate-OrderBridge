package error.pirate.backend.shippingInstruction.command.domain.service;

import error.pirate.backend.salesOrder.command.domain.aggregate.entity.SalesOrder;
import error.pirate.backend.shippingInstruction.command.application.dto.ShippingInstructionItemDTO;
import error.pirate.backend.shippingInstruction.command.application.dto.ShippingInstructionRequest;
import error.pirate.backend.shippingInstruction.command.domain.aggregate.entity.ShippingInstruction;
import error.pirate.backend.shippingInstruction.command.domain.repository.ShippingInstructionRepository;
import error.pirate.backend.shippingInstruction.command.mapper.ShippingInstructionMapper;
import error.pirate.backend.user.command.domain.aggregate.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShippingInstructionDomainService {

    private final ShippingInstructionRepository shippingInstructionRepository; ;

    /* 도메인 객체를 생성하는 로직 */
    public ShippingInstruction createShippingInstruction(
            ShippingInstructionRequest shippingInstructionRequest,
            SalesOrder salesOrder, User user, String shippingInstructionName,
            LocalDateTime shippingInstructionScheduledShipmentDate,
            int itemTotalQuantity) {

        /* dto to entity */
        ShippingInstruction newShippingInstruction = ShippingInstructionMapper.toEntity(
                shippingInstructionRequest, salesOrder, user, shippingInstructionName, shippingInstructionScheduledShipmentDate, itemTotalQuantity);
        return newShippingInstruction;
    }

    /* 도메인 객체를 저장하는 로직 */
    public ShippingInstruction saveShippingInstruction(ShippingInstruction newShippingInstruction) {
        return shippingInstructionRepository.save(newShippingInstruction);
    }

    /* 출하예정일 서울시간 설정 */
    public LocalDateTime setShippingInstructionScheduledShipmentDate(LocalDateTime shippingInstructionScheduledShipmentDate) {
        ZonedDateTime systemZonedDateTime = shippingInstructionScheduledShipmentDate.atZone(ZoneId.systemDefault());
        ZonedDateTime seoulZonedDateTime = systemZonedDateTime.withZoneSameInstant(ZoneId.of("Asia/Seoul"));
        return seoulZonedDateTime.toLocalDateTime();
    }

    /* 오늘날짜의 등록된 출하지지서 갯수 찾기 */
    public long countTodayShippingInstruction() {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1).minusNanos(1);
        return shippingInstructionRepository.countByShippingInstructionRegDateBetween(startOfDay, endOfDay);
    }

    /* 출하지시서 이름 설정 */
    public String setShippingInstructionName(long count) {
        // 서울 기준 현재 날짜 가져오기
        LocalDate today = LocalDate.now(ZoneId.of("Asia/Seoul"));

        // yyyy-MM-dd 형식으로 변환
        String formattedDate = today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        return formattedDate + " - " + (count+1);
    }

    /* 총수량 계산 */
    public int calculateTotalQuantity(ShippingInstructionRequest request) {
        // Null 검사를 수행하여 NPE 방지
        if (request.getShippingInstructionItems() == null || request.getShippingInstructionItems().isEmpty()) {
            return 0;
        }

        // stream을 사용해 shippingInstructionItemQuantity 필드 합산
        return request.getShippingInstructionItems()
                .stream()
                .mapToInt(ShippingInstructionItemDTO::getShippingInstructionItemQuantity)
                .sum();
    }
}
