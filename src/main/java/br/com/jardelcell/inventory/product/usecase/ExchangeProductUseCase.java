package br.com.jardelcell.inventory.product.usecase;

import br.com.jardelcell.inventory.common.exception.ResourceNotFoundException;
import br.com.jardelcell.inventory.inventory.InventoryMovementService;
import br.com.jardelcell.inventory.product.Product;
import br.com.jardelcell.inventory.product.ProductMapper;
import br.com.jardelcell.inventory.product.ProductRepository;
import br.com.jardelcell.inventory.product.dto.ExchangeProductRequest;
import br.com.jardelcell.inventory.product.dto.ProductResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExchangeProductUseCase {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final InventoryMovementService inventoryMovementService;

    @Transactional
    public ProductResponse execute(UUID productId, ExchangeProductRequest request){
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Product not found with Id: " + productId
                ));

        product.markAsExchanged();

        inventoryMovementService.registerExchange(
                product,
                request.differenceAmount(),
                request.observation()
        );

        return productMapper.toResponse(product);
    }
}
