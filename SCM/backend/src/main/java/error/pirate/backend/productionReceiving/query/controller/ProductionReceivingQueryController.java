package error.pirate.backend.productionReceiving.query.controller;

import error.pirate.backend.productionReceiving.query.dto.ProductionReceivingListRequest;
import error.pirate.backend.productionReceiving.query.dto.ProductionReceivingListResponse;
import error.pirate.backend.productionReceiving.query.service.ProductionReceivingQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/productionReceiving")
@Slf4j
@Tag(name = "생산입고 API", description = "생산입고 API")
public class ProductionReceivingQueryController {

    private final ProductionReceivingQueryService productionReceivingQueryService;

    @GetMapping
    @Operation(summary = "생산입고 리스트 조회")
    public ResponseEntity<ProductionReceivingListResponse> readProductionReceivingList(@ModelAttribute ProductionReceivingListRequest request, Pageable pageable) {
        return ResponseEntity.ok(productionReceivingQueryService.readProductionReceivingList(request, pageable));
    }
}