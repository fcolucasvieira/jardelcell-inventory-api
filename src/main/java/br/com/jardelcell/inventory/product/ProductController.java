package br.com.jardelcell.inventory.product;

import br.com.jardelcell.inventory.inventory.dto.InventoryMovementResponse;
import br.com.jardelcell.inventory.inventory.usecase.ListProductMovementsUseCase;
import br.com.jardelcell.inventory.product.dto.CreateProductRequest;
import br.com.jardelcell.inventory.product.dto.ProductResponse;
import br.com.jardelcell.inventory.product.usecase.CreateProductUseCase;
import br.com.jardelcell.inventory.product.usecase.GetProductByIdUseCase;
import br.com.jardelcell.inventory.product.usecase.ListProductsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final CreateProductUseCase createProductUseCase;
    private final ListProductsUseCase listProductsUseCase;
    private final GetProductByIdUseCase getProductByIdUseCase;
    private final ListProductMovementsUseCase listProductMovementsUseCase;


    @PostMapping
    public ResponseEntity<ProductResponse> create(@RequestBody CreateProductRequest request) {
        ProductResponse response = createProductUseCase.execute(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> list() {
        List<ProductResponse> response = listProductsUseCase.execute();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getById(@PathVariable UUID id) {
        ProductResponse response = getProductByIdUseCase.execute(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{productId}/movements")
    public ResponseEntity<List<InventoryMovementResponse>> listProductMovements(@PathVariable UUID productId) {
        List<InventoryMovementResponse> response = listProductMovementsUseCase.execute(productId);

        return ResponseEntity.ok(response);
    }
}
