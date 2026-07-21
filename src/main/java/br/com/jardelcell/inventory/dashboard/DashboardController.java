package br.com.jardelcell.inventory.dashboard;

import br.com.jardelcell.inventory.dashboard.usecase.GetDashboardUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {
    private final GetDashboardUseCase getDashboardUseCase;

    @GetMapping
    public ResponseEntity<DashboardResponse> getDashboard() {
        DashboardResponse response = getDashboardUseCase.execute();

        return ResponseEntity.ok(response);
    }
}
