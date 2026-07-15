package br.com.jardelcell.inventory.product.usecase;

import br.com.jardelcell.inventory.common.exception.ProductAlreadyExistsException;
import br.com.jardelcell.inventory.inventory.InventoryMovementService;
import br.com.jardelcell.inventory.product.Product;
import br.com.jardelcell.inventory.product.ProductMapper;
import br.com.jardelcell.inventory.product.ProductRepository;
import br.com.jardelcell.inventory.product.ProductStatus;
import br.com.jardelcell.inventory.product.dto.CreateProductRequest;
import br.com.jardelcell.inventory.product.dto.ProductResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateProductUseCase {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final InventoryMovementService inventoryMovementService;

    @Transactional
    public ProductResponse execute(CreateProductRequest request){
        if (productRepository.existsBySerialNumber(request.serialNumber())) {
            throw new ProductAlreadyExistsException(
                    "Product already exists with Serial Number: " + request.serialNumber()
            );
        }

        if (request.imei() != null &&
                !request.imei().isBlank() &&
                productRepository.existsByImei(request.imei())) {
            throw new ProductAlreadyExistsException(
                    "Product already exists with IMEI: " + request.imei()
            );
        }

        Product product = productMapper.toEntity(request, ProductStatus.IN_STOCK);

        productRepository.save(product);

        inventoryMovementService.registerEntry(product);

        return productMapper.toResponse(product);
    }
}
