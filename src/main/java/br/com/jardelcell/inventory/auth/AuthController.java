package br.com.jardelcell.inventory.auth;

import br.com.jardelcell.inventory.auth.dto.LoginRequest;
import br.com.jardelcell.inventory.auth.dto.LoginResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(
        name = "Authentication",
        description = "Authentication endpoints"
)
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;

    @Operation(
            summary = "Authenticate user",
            description = "Authenticates a user and returns a JWT access token."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "User authenticated successfully"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Invalid credentials"
            )
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authenticationService.login(request));
    }
}
