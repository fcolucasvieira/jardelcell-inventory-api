package br.com.jardelcell.inventory.product.usecase;

import br.com.jardelcell.inventory.product.Product;
import br.com.jardelcell.inventory.product.ProductMapper;
import br.com.jardelcell.inventory.product.ProductRepository;
import br.com.jardelcell.inventory.product.ProductStatus;
import br.com.jardelcell.inventory.product.dto.CreateProductRequest;
import br.com.jardelcell.inventory.product.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateProductUseCase {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductResponse execute(CreateProductRequest request){
        if (productRepository.existsBySerialNumber(request.serialNumber())) {
            throw new RuntimeException("Product already exists with Serial Number: " + request.serialNumber());
        }

        if (request.imei() != null &&
                !request.imei().isBlank() &&
                productRepository.existsByImei(request.imei())) {
            throw new RuntimeException("Product already exists with IMEI: " + request.imei());
        }

        Product product = productMapper.toEntity(request, ProductStatus.IN_STOCK);

        productRepository.save(product);

        return productMapper.toResponse(product);
    }
}
