package error.pirate.backend.warehouse.query.controller;

import error.pirate.backend.warehouse.query.dto.WarehouseFilterRequest;
import error.pirate.backend.warehouse.query.dto.WarehouseResponse;
import error.pirate.backend.warehouse.query.dto.WarehouseListResponse;
import error.pirate.backend.warehouse.query.service.WarehouseQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/warehouse")
@RequiredArgsConstructor
@Tag(name = "Warehouse", description = "창고 관리")
public class WarehouseQueryController {

    private final WarehouseQueryService warehouseQueryService;

    @GetMapping
    @Operation(summary = "창고 조회", description = "창고를 조회한다.")
    public ResponseEntity<WarehouseListResponse> readWarehouseList(WarehouseFilterRequest warehouseFilterRequest) {
        WarehouseListResponse response = warehouseQueryService.readWarehouseList(warehouseFilterRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{warehouseSeq}")
    @Operation(summary = "창고 상세 조회", description = "창고를 상세 조회한다.")
    public ResponseEntity<WarehouseResponse> readWarehouseList(
            @PathVariable Long warehouseSeq
    ) {

        WarehouseResponse response = warehouseQueryService.findWareHouseDetail(warehouseSeq);

        return ResponseEntity.ok(response);
    }
}