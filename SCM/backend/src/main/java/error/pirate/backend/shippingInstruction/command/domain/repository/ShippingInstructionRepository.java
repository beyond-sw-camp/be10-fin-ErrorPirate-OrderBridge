package error.pirate.backend.shippingInstruction.command.domain.repository;

import error.pirate.backend.shippingInstruction.command.domain.aggregate.entity.ShippingInstruction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ShippingInstructionRepository extends JpaRepository<ShippingInstruction, Long> {
    long countByShippingInstructionRegDateBetween(LocalDateTime startOfDay, LocalDateTime endOfDay);
}
