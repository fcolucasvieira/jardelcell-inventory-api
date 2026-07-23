package br.com.jardelcell.inventory.inventory;

import br.com.jardelcell.inventory.product.Product;
import br.com.jardelcell.inventory.product.ProductStatus;
import br.com.jardelcell.inventory.user.Role;
import br.com.jardelcell.inventory.user.User;
import br.com.jardelcell.inventory.user.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventoryMovementServiceTest {
    @Mock
    private InventoryMovementRepository inventoryMovementRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private InventoryMovementService inventoryMovementService;

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    private Product createProduct(ProductStatus status) {
        return new Product(
                "SN123456789",
                "356789123456789",
                "Apple",
                "iPhone 15",
                "128GB",
                "Black",
                new BigDecimal("4000.00"),
                new BigDecimal("5500.00"),
                status
        );
    }

    private User createUser() {
        return new User(
                "Lucas Vieira",
                "lucas@email.com",
                "123456",
                Role.ADMIN
        );
    }

    private User mockAuthenticatedUser() {
        User user = createUser();

        Authentication authentication = mock(Authentication.class);
        when(authentication.getName())
                .thenReturn(user.getEmail());

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication())
                .thenReturn(authentication);

        SecurityContextHolder.setContext(securityContext);

        when(userRepository.findByEmail(user.getEmail()))
                .thenReturn(Optional.of(user));

        return user;
    }

    @Test
    void shouldRegisterEntrySuccessfully() {
        Product product = createProduct(ProductStatus.IN_STOCK);

        User user = mockAuthenticatedUser();

        inventoryMovementService.registerEntry(product);

        ArgumentCaptor<InventoryMovement> captor =
                ArgumentCaptor.forClass(InventoryMovement.class);

        verify(inventoryMovementRepository).save(captor.capture());

        InventoryMovement movement = captor.getValue();

        assertEquals(product, movement.getProduct());
        assertEquals(user, movement.getUser());
        assertEquals(MovementType.ENTRY, movement.getType());
        assertEquals(product.getPurchasePrice(), movement.getMovementPrice());
        assertNull(movement.getObservation());
    }

    @ParameterizedTest
    @EnumSource(
            value = ProductStatus.class,
            names = {
                    "IN_STOCK", "RESERVED"
            }
    )
    void shouldRegisterSaleSuccessfully(ProductStatus status) {
        Product product = createProduct(status);

        User user = mockAuthenticatedUser();

        BigDecimal salePrice = new BigDecimal("4500.00");
        String observation = "Product sold for R$ 4500";

        inventoryMovementService.registerSale(
                product,
                salePrice,
                observation
        );

        ArgumentCaptor<InventoryMovement> captor =
                ArgumentCaptor.forClass(InventoryMovement.class);

        verify(inventoryMovementRepository).save(captor.capture());

        InventoryMovement movement = captor.getValue();

        assertEquals(product, movement.getProduct());
        assertEquals(user, movement.getUser());
        assertEquals(MovementType.SALE, movement.getType());
        assertEquals(salePrice, movement.getMovementPrice());
        assertEquals(observation, movement.getObservation());
    }

    @ParameterizedTest
    @EnumSource(
            value = ProductStatus.class,
            names = {
                    "IN_STOCK", "RESERVED"
            }
    )
    void shouldRegisterExchangeSuccessfully(ProductStatus status) {
        Product product = createProduct(status);

        User user = mockAuthenticatedUser();

        BigDecimal differenceAmount = new BigDecimal("1000.00");
        String observation = "Trading an iPhone 15 for an iPhone 16 with a price difference of R$ 1,000.";

        inventoryMovementService.registerExchange(
                product,
                differenceAmount,
                observation
        );

        ArgumentCaptor<InventoryMovement> captor =
                ArgumentCaptor.forClass(InventoryMovement.class);

        verify(inventoryMovementRepository).save(captor.capture());

        InventoryMovement movement = captor.getValue();

        assertEquals(product, movement.getProduct());
        assertEquals(user, movement.getUser());
        assertEquals(MovementType.EXCHANGE, movement.getType());
        assertEquals(differenceAmount, movement.getMovementPrice());
        assertEquals(observation, movement.getObservation());
    }

    @Test
    void shouldRegisterReserveSuccessfully() {
        Product product = createProduct(ProductStatus.IN_STOCK);

        User user = mockAuthenticatedUser();

        String observation = "A customer reserved a product for tomorrow, 24/07";

        inventoryMovementService.registerReserve(
                product,
                observation
        );

        ArgumentCaptor<InventoryMovement> captor =
                ArgumentCaptor.forClass(InventoryMovement.class);

        verify(inventoryMovementRepository).save(captor.capture());

        InventoryMovement movement = captor.getValue();

        assertEquals(product, movement.getProduct());
        assertEquals(user, movement.getUser());
        assertEquals(MovementType.RESERVE, movement.getType());
        assertNull(movement.getMovementPrice());
        assertEquals(observation, movement.getObservation());
    }

    @Test
    void shouldRegisterUnreserveSuccessfully() {
        Product product = createProduct(ProductStatus.RESERVED);

        User user = mockAuthenticatedUser();

        String observation =
                "The customer cancelled the product reservation for tomorrow, 24/07";

        inventoryMovementService.registerUnreserve(
                product,
                observation
        );

        ArgumentCaptor<InventoryMovement> captor =
                ArgumentCaptor.forClass(InventoryMovement.class);

        verify(inventoryMovementRepository).save(captor.capture());

        InventoryMovement movement = captor.getValue();

        assertEquals(product, movement.getProduct());
        assertEquals(user, movement.getUser());
        assertEquals(MovementType.UNRESERVE, movement.getType());
        assertNull(movement.getMovementPrice());
        assertEquals(observation, movement.getObservation());
    }

    @ParameterizedTest
    @EnumSource(
            value = ProductStatus.class,
            names = {
                    "IN_STOCK", "RESERVED"
            }
    )
    void shouldRegisterDefectiveSuccessfully(ProductStatus status) {
        Product product = createProduct(status);

        User user = mockAuthenticatedUser();

        String observation = "Product with a defective graphics card";

        inventoryMovementService.registerDefective(
                product,
                observation
        );

        ArgumentCaptor<InventoryMovement> captor =
                ArgumentCaptor.forClass(InventoryMovement.class);

        verify(inventoryMovementRepository).save(captor.capture());

        InventoryMovement movement = captor.getValue();

        assertEquals(product, movement.getProduct());
        assertEquals(user, movement.getUser());
        assertEquals(MovementType.DEFECTIVE, movement.getType());
        assertNull(movement.getMovementPrice());
        assertEquals(observation, movement.getObservation());
    }
}