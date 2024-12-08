package error.pirate.backend.shippingInstruction.command.application.controller;

import error.pirate.backend.shippingInstruction.command.application.dto.ShippingInstructionRequest;
import error.pirate.backend.shippingInstruction.command.application.service.ShippingInstructionApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "출하지시서", description = "출하지시서 조회/등록/수정/삭제")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/shipping-instruction")
@Slf4j
public class ShippingInstructionCommandController {

    private final ShippingInstructionApplicationService shippingInstructionApplicationService;

    // 출하지시서 작성
    @Operation(summary = "출하지시서 작성", description = "출하지시서를 작성한다.")
    @PostMapping
    public ResponseEntity<String> createShippingInstruction(
            @RequestBody ShippingInstructionRequest evaluationRequest
    ) {

        shippingInstructionApplicationService.createShippingInstruction(evaluationRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body("출하지시서 작성성공");
    }
}
