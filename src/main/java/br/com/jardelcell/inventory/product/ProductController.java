package br.com.jardelcell.inventory.product;

import br.com.jardelcell.inventory.inventory.dto.InventoryMovementResponse;
import br.com.jardelcell.inventory.inventory.usecase.ListProductMovementsUseCase;
import br.com.jardelcell.inventory.product.dto.*;
import br.com.jardelcell.inventory.product.usecase.*;
import jakarta.validation.Valid;
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
    private final SellProductUseCase sellProductUseCase;
    private final ReserveProductUseCase reserveProductUseCase;
    private final UnreserveProductUseCase unreserveProductUseCase;
    private final DefectiveProductUseCase defectiveProductUseCase;
    private final ExchangeProductUseCase exchangeProductUseCase;

    @PostMapping
    public ResponseEntity<ProductResponse> create(@RequestBody @Valid CreateProductRequest request) {
        ProductResponse response = createProductUseCase.execute(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/{productId}/sale")
    public ResponseEntity<ProductResponse> sell(@PathVariable UUID productId,
                                                @Valid @RequestBody SellProductRequest request) {
        ProductResponse response = sellProductUseCase.execute(productId, request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{productId}/exchange")
    public ResponseEntity<ProductResponse> exchange(@PathVariable UUID productId,
                                                    @Valid @RequestBody ExchangeProductRequest request) {
        ProductResponse response = exchangeProductUseCase.execute(productId, request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{productId}/reserve")
    public ResponseEntity<ProductResponse> reserve(@PathVariable UUID productId,
                                                   @Valid @RequestBody ReserveProductRequest request) {
        ProductResponse response = reserveProductUseCase.execute(productId, request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{productId}/unreserve")
    public ResponseEntity<ProductResponse> unreserve(@PathVariable UUID productId,
                                                     @Valid @RequestBody UnreserveProductRequest request) {

        ProductResponse response =
                unreserveProductUseCase.execute(productId, request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{productId}/defective")
    public ResponseEntity<ProductResponse> markAsDefective(@PathVariable UUID productId,
                                                           @Valid @RequestBody DefectiveProductRequest request) {
        ProductResponse response = defectiveProductUseCase.execute(productId, request);

        return ResponseEntity.ok(response);
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
