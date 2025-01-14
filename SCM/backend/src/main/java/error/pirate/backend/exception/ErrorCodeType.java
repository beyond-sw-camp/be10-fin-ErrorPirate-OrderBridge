package error.pirate.backend.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCodeType {

    // 게이트웨이 관련 오류
    GATE_WAY_BAD_GATEWAY_ERROR(HttpStatus.BAD_GATEWAY, "GATEWAY_ERROR_001", "서버가 응답하지 않습니다."),
    GATE_WAY_TIMEOUT_ERROR(HttpStatus.GATEWAY_TIMEOUT, "GATEWAY_ERROR_002", "게이트웨이 시간 초과"),

    // 스프링 시큐리티 관련 오류
    SECURITY_ACCESS_ERROR(HttpStatus.FORBIDDEN, "SECURITY_ERROR_001", "접근 권한이 없습니다."),
    SECURITY_TOKEN_ERROR(HttpStatus.UNAUTHORIZED, "SECURITY_ERROR_002", "로그인 후 다시 시도해 주세요."),
    SECURITY_LOGIN_ERROR(HttpStatus.BAD_REQUEST, "SECURITY_ERROR_003", "로그인 실패하였습니다. 관리자에게 문의해 주세요."),

    // 주문서 관련 오류
    SALES_ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "SALES_ORDER_ERROR_001", "주문서를 찾을 수 없습니다."),
    SALES_ORDER_STATE_BAD_REQUEST(HttpStatus.BAD_REQUEST, "SALES_ORDER_ERROR_002", "주문서의 상태를 확인해주세요"),
    SALES_ORDER_ITEM_NOT_MATCH(HttpStatus.BAD_REQUEST, "SALES_ORDER_ERROR_003", "주문서의 아이템이 견적서와 다릅니다."),

    // 주문서 품목 관련 오류
    SALES_ORDER_ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "SALES_ORDER_ITEM_ERROR_001", "주문서의 품목을 찾을 수 없습니다."),
    SALES_ORDER_ITEM_QUANTITY_REQUIRED(HttpStatus.BAD_REQUEST, "SALES_ORDER_ITEM_ERROR_002", "주문 품목 수량이 설정되어 있지 않습니다."),

    // 거래 명세서 관련 오류
    INVOICE_ITEM_NOT_MATCH(HttpStatus.BAD_REQUEST, "INVOICE_ITEM_ERROR_001", "거래 명세서의 품목이 주문서와 다릅니다."),

    //발주서 관련 오류
    PURCHASE_NOT_FOUND(HttpStatus.NOT_FOUND, "PURCHASE_ERROR_001", "발주서를 찾을 수 없습니다."),
    PURCHASE_UPDATE_ERROR(HttpStatus.BAD_REQUEST, "PURCHASE_ERROR_002", "이미 결재가 완료된 주문입니다."),

    // 출하지시서 관련 오류
    SHIPPING_INSTRUCTION_NOT_FOUND(HttpStatus.NOT_FOUND, "SHIPPING_INSTRUCTION_ERROR_001", "출하지시서를 찾을 수 없습니다."),
    SHIPPING_INSTRUCTION_STATE_BAD_REQUEST(HttpStatus.BAD_REQUEST, "SHIPPING_INSTRUCTION_ERROR_002", "출하지시서의 상태를 확인해주세요"),
    SHIPPING_INSTRUCTION_ITEM_NOT_MATCH(HttpStatus.BAD_REQUEST, "SHIPPING_INSTRUCTION_ERROR_003", "출하지시서의 아이템이 주문서와 다릅니다."),

    // 출하전표 관련 오류
    SHIPPING_SLIP_NOT_FOUND(HttpStatus.NOT_FOUND, "SHIPPING_SLIP_ERROR_001", "출하전표를 찾을 수 없습니다."),
    SHIPPING_SLIP_DELETE_STATE(HttpStatus.BAD_REQUEST, "SHIPPING_SLIP_ERROR_002", "이미 삭제된 출하전표입니다."),
    SHIPPING_SLIP_ITEM_NOT_MATCH(HttpStatus.BAD_REQUEST, "SHIPPING_SLIP_ERROR_003", "출하전표의 아이템이 출하지시서와 다릅니다."),
    SHIPPING_SLIP_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "SHIPPING_SLIP_ERROR_004", "해당하는 출하전표가 이미 존재합니다."),

    // 공통 오류
    COMMON_ERROR(HttpStatus.BAD_REQUEST, "COMMON_ERROR", "오류가 발생하였습니다. 관리자에게 문의 바랍니다."),
    INVALID_DATE_RANGE(HttpStatus.BAD_REQUEST, "COMMON_ERROR_002", "유효하지 않은 날짜 범위입니다."),

    // 상품입고 관련 오류
    PRODUCTION_RECEIVING_NOT_FOUND(HttpStatus.NOT_FOUND, "PRODUCTION_RECEIVING_ERROR_001", "생산입고를 찾을 수 없습니다."),
    PRODUCTION_RECEIVING_UPDATE_ERROR(HttpStatus.BAD_REQUEST, "PRODUCTION_RECEIVING_ERROR_002", "이미 결재가 완료된 상품입고입니다."),
    PRODUCTION_RECEIVING_UPDATE_COMPLETE_ERROR(HttpStatus.BAD_REQUEST, "PRODUCTION_RECEIVING_ERROR_003", "결재가 완료된 상품입고만 상품입고 완료 상태로 변경할 수 있습니다."),

    // 창고 관련 오류
    WAREHOUSE_NOT_FOUND(HttpStatus.NOT_FOUND, "WAREHOUSE_ERROR_001", "창고를 찾을 수 없습니다."),

    // 작업지시서 오류
    WORK_ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "WORK_ORDER_ERROR_001", "작업지시서를 찾을 수 없습니다."),
    WORK_ORDER_STATUS_ERROR(HttpStatus.BAD_REQUEST, "WORK_ORDER_ERROR_002", "작업지시가 완료된 경우만 생산 입고를 작성할 수 있습니다."),
    WORK_ORDER_REQUIRED_INFORMATION(HttpStatus.BAD_REQUEST, "WORK_ORDER_ERROR_003", "필수 입력 항목입니다."),
    WORK_ORDER_DUPLICATE(HttpStatus.CONFLICT, "WORK_ORDER_ERROR_004", "이미 작업지시서가 생성된 주문서입니다."),
    WORK_ORDER_STATE_BAD_REQUEST(HttpStatus.BAD_REQUEST, "WORK_ORDER_ERROR_005", "작업지시서의 상태를 확인해주세요."),
    WORK_ORDER_INVALID_QUANTITY(HttpStatus.BAD_REQUEST, "WORK_ORDER_ERROR_006", "지시수량을 확인해주세요."),

    // 생산불출 오류
    PRODUCTION_DISBURSEMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "PRODUCTION_DISBURSEMENT_ERROR_001", "생산불출을 찾을 수 없습니다."),
    PRODUCTION_DISBURSEMENT_STATE_BAD_REQUEST(HttpStatus.BAD_REQUEST, "PRODUCTION_DISBURSEMENT_ERROR_002", "생산불출의 상태를 확인해주세요."),
    PRODUCTION_DISBURSEMENT_DUPLICATE(HttpStatus.CONFLICT, "PRODUCTION_DISBURSEMENT_ERROR_003", "이미 생산불출이 생성된 작업지시서입니다."),
    PRODUCTION_DISBURSEMENT_REQUIRED_INFORMATION(HttpStatus.BAD_REQUEST, "PRODUCTION_DISBURSEMENT_ERROR_004", "필수 입력 항목입니다."),

    // 회원 오류
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_ERROR_001", "회원을 찾을 수 없습니다."),

    // 거래처 오류
    CLIENT_NOT_FOUND(HttpStatus.NOT_FOUND, "CLIENT_ERROR_001", "거래처를 찾을 수 없습니다."),
    CLIENT_STATUS_ERROR(HttpStatus.BAD_REQUEST, "CLIENT_ERROR_002", "이미 삭제된 거래처입니다."),

    // 물품 오류
    ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "ITEM_ERROR_001", "물품을 찾을 수 없습니다."),
    ITEM_STATUS_ERROR(HttpStatus.BAD_REQUEST, "ITEM_ERROR_002", "이미 삭제된 상품입니다."),
    BOM_ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "ITEM_ERROR_003", "BOM 항목이 존재하지 않습니다."),

    // 물품 재고 오류
    OUT_OF_STOCK_ERROR(HttpStatus.BAD_REQUEST, "STOCK_ERROR_001", "재고가 부족합니다."),

    // 물품 단위 오류
    ITEM_UNIT_NOT_FOUND(HttpStatus.NOT_FOUND, "ITEM_UNIT_ERROR_001", "물품 단위를 찾을 수 없습니다."),

    // 엑셀 다운 오류
    EXCEL_DOWN_ERROR(HttpStatus.BAD_REQUEST, "EXCEL_DOWN_ERROR_001", "엑셀 다운로드 도중 오류가 발생하였습니다."),
    ;
    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

}