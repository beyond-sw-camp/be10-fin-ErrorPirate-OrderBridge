package error.pirate.backend.productionReceiving.command.domain.aggregate.entity;

import error.pirate.backend.item.command.domain.aggregate.entity.Item;
import error.pirate.backend.productionReceiving.command.application.dto.ProductionReceivingItemDTO;
import error.pirate.backend.warehouse.command.domain.aggregate.entity.Warehouse;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_production_receiving_item") // 생산 입고 품목
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductionReceivingItem {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productionReceivingItemSeq;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "itemSeq")
    private Item item; // 품목

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "productionReceivingSeq")
    private ProductionReceiving productionReceiving; // 생산 입고

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouseSeq")
    private Warehouse warehouse; // 생산 입고 창고

    private int productionReceivingItemQuantity; // 생산 입고 품목 수량

    private int productionReceivingUnitPrice; // 생산 단가

    private String productionReceivingItemNote; // 생산 입고 품목 비고

    public static ProductionReceivingItem createProductionReceivingItem(Item item, ProductionReceiving productionReceiving, Warehouse warehouse, ProductionReceivingItemDTO dto) {
        ProductionReceivingItem productionReceivingItem = new ProductionReceivingItem(dto);
        productionReceivingItem.specifyItem(item);
        productionReceivingItem.specifyProductionReceiving(productionReceiving);
        productionReceivingItem.specifyWarehouse(warehouse);
        return productionReceivingItem;
    }

    protected ProductionReceivingItem(ProductionReceivingItemDTO dto) {
        this.productionReceivingItemQuantity = dto.getProductionReceivingItemQuantity();
        this.productionReceivingUnitPrice = dto.getProductionReceivingUnitPrice();
        this.productionReceivingItemNote = dto.getProductionReceivingItemNote();
    }

    private void specifyProductionReceiving(ProductionReceiving productionReceiving) {
        this.productionReceiving = productionReceiving;
    }

    private void specifyItem(Item item) {
        this.item = item;
    }

    private void specifyWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }
}
