package br.com.jardelcell.inventory.product;

import br.com.jardelcell.inventory.product.dto.CreateProductRequest;
import br.com.jardelcell.inventory.product.dto.ProductResponse;
import org.springframework.stereotype.Component;


@Component
public class ProductMapper {
    public Product toEntity(CreateProductRequest request, ProductStatus status) {
        return new Product(
                request.serialNumber(),
                request.imei(),
                request.brand(),
                request.model(),
                request.storage(),
                request.color(),
                request.purchasePrice(),
                request.salePrice(),
                status
        );
    }

    public ProductResponse toResponse(Product entity) {
        return new ProductResponse(
                entity.getId(),
                entity.getSerialNumber(),
                entity.getBrand(),
                entity.getModel(),
                entity.getStatus(),
                entity.getPurchasePrice(),
                entity.getSalePrice(),
                entity.getCreatedAt()
        );
    }
}
