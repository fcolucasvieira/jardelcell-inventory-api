package br.com.jardelcell.inventory.product.usecase;

import br.com.jardelcell.inventory.product.ProductMapper;
import br.com.jardelcell.inventory.product.ProductRepository;
import br.com.jardelcell.inventory.product.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ListProductsUseCase {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public List<ProductResponse> execute() {
        return productRepository.findAll().stream()
                .map(productMapper::toResponse)
                .toList();
    }
}
