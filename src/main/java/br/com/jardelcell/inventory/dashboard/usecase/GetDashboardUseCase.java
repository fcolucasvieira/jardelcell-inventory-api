package br.com.jardelcell.inventory.dashboard.usecase;

import br.com.jardelcell.inventory.dashboard.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetDashboardUseCase {
    private final DashboardService dashboardService;
}
