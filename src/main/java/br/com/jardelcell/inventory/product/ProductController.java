package br.com.jardelcell.inventory.product;

import br.com.jardelcell.inventory.inventory.dto.InventoryMovementResponse;
import br.com.jardelcell.inventory.inventory.usecase.ListProductMovementsUseCase;
import br.com.jardelcell.inventory.product.dto.*;
import br.com.jardelcell.inventory.product.usecase.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(
        name = "Products",
        description = "Product lifecycle management endpoints"
)
@SecurityRequirement(name = "bearerAuth")
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
    private final RepairProductUseCase repairProductUseCase;

    @Operation(
            summary = "Create product",
            description = "Registers a new Apple device in the inventory."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Product created successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request data",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Authentication required",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "A product with the same Serial Number or IMEI already exists",
                    content = @Content
            )
    })
    @PostMapping
    public ResponseEntity<ProductResponse> create(@RequestBody @Valid CreateProductRequest request) {
        ProductResponse response = createProductUseCase.execute(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Sell product",
            description = "Marks a product as sold and registers the sale movement."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Product sold successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request data",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Authentication required",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product not found",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Product cannot be sold in its current status",
                    content = @Content
            )
    })
    @PostMapping("/{productId}/sale")
    public ResponseEntity<ProductResponse> sell(@PathVariable UUID productId,
                                                @Valid @RequestBody SellProductRequest request) {
        ProductResponse response = sellProductUseCase.execute(productId, request);

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Exchange product",
            description = "Marks a product as exchanged and records the exchange transaction."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Product exchanged successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request data",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Authentication required",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product not found",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Product cannot be exchanged in its current status",
                    content = @Content
            )
    })
    @PostMapping("/{productId}/exchange")
    public ResponseEntity<ProductResponse> exchange(@PathVariable UUID productId,
                                                    @Valid @RequestBody ExchangeProductRequest request) {
        ProductResponse response = exchangeProductUseCase.execute(productId, request);

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Reserve product",
            description = "Reserves a product for a customer."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Product reserved successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request data",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Authentication required",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product not found",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Product cannot be reserved in its current status",
                    content = @Content
            )
    })
    @PostMapping("/{productId}/reserve")
    public ResponseEntity<ProductResponse> reserve(@PathVariable UUID productId,
                                                   @Valid @RequestBody ReserveProductRequest request) {
        ProductResponse response = reserveProductUseCase.execute(productId, request);

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Cancel reservation",
            description = "Cancels a product reservation and returns it to stock."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Reservation cancelled successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request data",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Authentication required",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product not found",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Product is not reserved",
                    content = @Content
            )
    })
    @PostMapping("/{productId}/unreserve")
    public ResponseEntity<ProductResponse> unreserve(@PathVariable UUID productId,
                                                     @Valid @RequestBody UnreserveProductRequest request) {

        ProductResponse response =
                unreserveProductUseCase.execute(productId, request);

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Mark product as defective",
            description = "Marks a product as defective and removes it from the available inventory."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Product marked as defective successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request data",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Authentication required",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product not found",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Product cannot be marked as defective in its current status",
                    content = @Content
            )
    })
    @PostMapping("/{productId}/defective")
    public ResponseEntity<ProductResponse> markAsDefective(@PathVariable UUID productId,
                                                           @Valid @RequestBody DefectiveProductRequest request) {
        ProductResponse response = defectiveProductUseCase.execute(productId, request);

        return ResponseEntity.ok(response);
    }


    @Operation(
            summary = "Repair product",
            description = "Repairs a defective product and returns it to stock."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Product repaired successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request data",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Authentication required",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product not found",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Product cannot be repaired in its current status",
                    content = @Content
            )
    })
    @PostMapping("/{productId}/repair")
    public ResponseEntity<ProductResponse> repair(@PathVariable UUID productId,
                                                  @Valid @RequestBody RepairProductRequest request) {
        ProductResponse response = repairProductUseCase.execute(productId, request);

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "List products",
            description = "Returns all products registered in the inventory."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Products retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Authentication required",
                    content = @Content
            )
    })
    @GetMapping
    public ResponseEntity<List<ProductResponse>> list() {
        List<ProductResponse> response = listProductsUseCase.execute();

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Retrieve product by ID",
            description = "Returns the details of a product by its unique identifier."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Product retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Authentication required",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product not found",
                    content = @Content
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getById(@PathVariable UUID id) {
        ProductResponse response = getProductByIdUseCase.execute(id);

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Retrieve product movements",
            description = "Returns the complete inventory movement history for a product."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Inventory movements retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Authentication required",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product not found",
                    content = @Content
            )
    })
    @GetMapping("/{productId}/movements")
    public ResponseEntity<List<InventoryMovementResponse>> listProductMovements(@PathVariable UUID productId) {
        List<InventoryMovementResponse> response = listProductMovementsUseCase.execute(productId);

        return ResponseEntity.ok(response);
    }
}
