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

    public ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getSerialNumber(),
                product.getBrand(),
                product.getModel(),
                product.getStatus(),
                product.getPurchasePrice(),
                product.getSalePrice(),
                product.getCreatedAt()
        );
    }
}
