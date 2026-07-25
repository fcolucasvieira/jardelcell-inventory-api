package br.com.jardelcell.inventory.config;

import br.com.jardelcell.inventory.user.Role;
import br.com.jardelcell.inventory.user.User;
import br.com.jardelcell.inventory.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Profile("prod")
@RequiredArgsConstructor
public class ProdDatabaseSeeder implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(ProdDatabaseSeeder.class);

    @Value("${app.bootstrap.admin.full-name}")
    private String adminFullName;

    @Value("${app.bootstrap.admin.email}")
    private String adminEmail;

    @Value("${app.bootstrap.admin.password}")
    private String adminPassword;

    @Value("${app.bootstrap.employee1.full-name}")
    private String employee1FullName;

    @Value("${app.bootstrap.employee1.email}")
    private String employee1Email;

    @Value("${app.bootstrap.employee1.password}")
    private String employee1Password;

    @Value("${app.bootstrap.employee2.full-name}")
    private String employee2FullName;

    @Value("${app.bootstrap.employee2.email}")
    private String employee2Email;

    @Value("${app.bootstrap.employee2.password}")
    private String employee2Password;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        createUserIfNotExists(adminFullName, adminEmail, adminPassword, Role.ADMIN);

        createUserIfNotExists(employee1FullName, employee1Email, employee1Password, Role.EMPLOYEE);
        createUserIfNotExists(employee2FullName, employee2Email, employee2Password, Role.EMPLOYEE);
    }

    private void createUserIfNotExists(String fullName, String email, String password, Role role) {
        if (userRepository.findByEmail(email).isPresent()) {
            log.info("Default {} user already exists: {}", role, email);
            return;
        }

        User user = new User(fullName, email, passwordEncoder.encode(password), role);

        userRepository.save(user);
        log.info("Creating default {} user: {}", role, email);
    }
}