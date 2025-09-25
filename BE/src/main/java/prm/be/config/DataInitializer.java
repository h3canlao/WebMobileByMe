package prm.be.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import prm.be.entity.Account;
import prm.be.enums.Role;
import prm.be.repository.AccountRepository;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        if (accountRepository.findByUsername("admin69").isEmpty()) {
            Account admin = Account.builder()
                    .username("admin69")
                    .password(passwordEncoder.encode("admin123"))
                    .email("admin@example.com")
                    .role(Role.ADMIN)
                    .build();

            accountRepository.save(admin);
            System.out.println(" Admin account created: admin69 / admin123");
        }
    }
}

