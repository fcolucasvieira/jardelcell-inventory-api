package br.com.jardelcell.inventory.dashboard;

import br.com.jardelcell.inventory.product.ProductRepository;
import br.com.jardelcell.inventory.product.ProductStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {
    private final ProductRepository productRepository;

    public DashboardResponse getDashboard() {
        long inStock = productRepository.countByStatus(ProductStatus.IN_STOCK);
        long reserved = productRepository.countByStatus(ProductStatus.RESERVED);
        long sold = productRepository.countByStatus(ProductStatus.SOLD);
        long defective = productRepository.countByStatus(ProductStatus.DEFECTIVE);
        long exchanged = productRepository.countByStatus(ProductStatus.EXCHANGED);

        return new DashboardResponse(
                inStock,
                reserved,
                sold,
                defective,
                exchanged,
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                List.of()
        );
    }
}
