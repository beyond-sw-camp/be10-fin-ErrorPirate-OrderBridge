package error.pirate.backend.shippingInstruction.command.domain.repository;

import error.pirate.backend.shippingInstruction.command.domain.aggregate.entity.ShippingInstructionItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShippingInstructionItemRepository extends JpaRepository<ShippingInstructionItem, Long> {
}
