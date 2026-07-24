package br.com.jardelcell.inventory.dashboard;

import br.com.jardelcell.inventory.dashboard.usecase.GetDashboardUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(
        name = "Dashboard",
        description = "Dashboard and business metrics endpoints"
)
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {
    private final GetDashboardUseCase getDashboardUseCase;

    @Operation(
            summary = "Retrieve dashboard",
            description = "Returns inventory metrics, financial indicators and the latest inventory movements."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Dashboard retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Authentication required",
                    content = @Content
            )
    })
    @GetMapping
    public ResponseEntity<DashboardResponse> getDashboard() {
        DashboardResponse response = getDashboardUseCase.execute();

        return ResponseEntity.ok(response);
    }
}
