package br.com.jardelcell.inventory.config;

import br.com.jardelcell.inventory.user.Role;
import br.com.jardelcell.inventory.user.User;
import br.com.jardelcell.inventory.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
@RequiredArgsConstructor
public class DatabaseSeeder implements CommandLineRunner {
    private static final String ADMIN_NAME = "Administrator";
    private static final String ADMIN_EMAIL = "admin@inventory.com";
    private static final String ADMIN_PASSWORD = "admin123";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.findByEmail(ADMIN_EMAIL).isPresent()) {
            return;
        }

        User admin = new User(
                ADMIN_NAME,
                ADMIN_EMAIL,
                passwordEncoder.encode(ADMIN_PASSWORD),
                Role.ADMIN
        );

        userRepository.save(admin);
    }

}
