package error.pirate.backend.shippingInstruction.query.controller;

import error.pirate.backend.shippingInstruction.query.dto.ShippingInstructionListRequest;
import error.pirate.backend.shippingInstruction.query.dto.ShippingInstructionListResponse;
import error.pirate.backend.shippingInstruction.query.dto.ShippingInstructionResponse;
import error.pirate.backend.shippingInstruction.query.dto.ShippingInstructionSituationRequest;
import error.pirate.backend.shippingInstruction.query.dto.ShippingInstructionSituationResponse;
import error.pirate.backend.shippingInstruction.query.service.ShippingInstructionQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Tag(name = "출하지시서")
@RestController
@RequiredArgsConstructor    // final 을 받은 필드의 생성자를 주입
@RequestMapping("/api/v1/shipping-instruction")
@Slf4j
public class ShippingInstructionQueryController {
    private final ShippingInstructionQueryService shippingInstructionQueryService;

    @Operation(summary = "출하지시서 조회", description = "출하지시서 조회")
    @GetMapping
    public ResponseEntity<ShippingInstructionListResponse> readShippingInstructionList(
            @ModelAttribute ShippingInstructionListRequest request
    ) {
        ShippingInstructionListResponse response
                = shippingInstructionQueryService.readShippingInstructionList(request);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "출하지시서 상세조회", description = "출하지시서 상세조회")
    @GetMapping("/{shippingInstructionSeq}")
    public ResponseEntity<ShippingInstructionResponse> readShippingInstruction(
            @PathVariable long shippingInstructionSeq
    ) {
        ShippingInstructionResponse response
                = shippingInstructionQueryService.readShippingInstruction(shippingInstructionSeq);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "출하지시서 현황 조회", description = "출하지시서 현황 조회")
    @GetMapping("/situation")
    public ResponseEntity<ShippingInstructionSituationResponse> readShippingInstructionSituation(
            @ModelAttribute ShippingInstructionSituationRequest request
    ) {
        ShippingInstructionSituationResponse response
                = shippingInstructionQueryService.readShippingInstructionSituation(request);

        return ResponseEntity.ok(response);
    }
}
