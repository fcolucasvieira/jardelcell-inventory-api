package br.com.jardelcell.inventory.auth;

import br.com.jardelcell.inventory.auth.dto.LoginRequest;
import br.com.jardelcell.inventory.auth.dto.LoginResponse;
import br.com.jardelcell.inventory.security.TokenService;
import br.com.jardelcell.inventory.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public LoginResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        User authenticatedUser = (User) authentication.getPrincipal();

        String token = tokenService.generateToken(authenticatedUser);

        return new LoginResponse(token);
    }
}
