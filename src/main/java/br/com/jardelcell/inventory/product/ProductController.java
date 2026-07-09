package br.com.jardelcell.inventory.product;

import br.com.jardelcell.inventory.product.dto.CreateProductRequest;
import br.com.jardelcell.inventory.product.dto.ProductResponse;
import br.com.jardelcell.inventory.product.usecase.CreateProductUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final CreateProductUseCase createProductUseCase;

    @PostMapping
    public ResponseEntity<ProductResponse> create(@RequestBody CreateProductRequest request) {
        ProductResponse response = createProductUseCase.execute(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
