package io.github.tuyendev.msv.common.configurer;

import io.github.tuyendev.msv.common.constant.AuthorityType;
import io.github.tuyendev.msv.common.constant.EntityStatus;
import io.github.tuyendev.msv.common.constant.UserEntity;
import io.github.tuyendev.msv.common.entity.Authority;
import io.github.tuyendev.msv.common.entity.User;
import io.github.tuyendev.msv.common.repository.AuthorityRepository;
import io.github.tuyendev.msv.common.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import one.util.streamex.StreamEx;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.Instant;
import java.util.Set;

import static io.github.tuyendev.msv.common.constant.BeanName.BootstrapAppConfigurerBean;

@Slf4j
@Configuration(value = BootstrapAppConfigurerBean)
@RequiredArgsConstructor
public class BootstrapAppConfigurer {

    protected final PlatformTransactionManager txManager;

    private final Environment env;

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepo;

    private final AuthorityRepository authorityRepo;

    @PostConstruct
    public void afterInit() {
        TransactionTemplate tx = new TransactionTemplate(txManager);
        tx.executeWithoutResult(txStatus -> {
            createDefaultAuthorities();
            createDefaultUser();
        });

    }


    private void createDefaultAuthorities() {
        for (AuthorityType auth : AuthorityType.values()) {
            if (authorityRepo.nonExistedByName(auth.name())) {
                System.out.printf("\n\tCreate [ %s ] authority\n", auth.value());
                Authority authority = Authority.builder()
                        .name(auth.name())
                        .description(auth.desc())
                        .build();
                authorityRepo.save(authority);
            }

        }
    }

    private void createDefaultUser() {
        if (userRepo.nonExistedById(UserEntity.DEFAULT_USER_ADMIN_ID)) {
            final String defaultAdminUsername = env.getProperty("app.common.user.admin.username");
            final String defaultAdminPassword = env.getProperty("app.common.user.admin.password");
            final Instant now = Instant.now();
            final Set<Authority> authorities = StreamEx.of(authorityRepo.findAll()).toImmutableSet();
            User admin = User.builder()
                    .id(UserEntity.DEFAULT_USER_ADMIN_ID)
                    .name(defaultAdminUsername)
                    .unsignedName(defaultAdminUsername)
                    .username(defaultAdminUsername)
                    .email(defaultAdminUsername)
                    .emailVerified(UserEntity.EMAIL_VERIFIED)
                    .preferredUsername(defaultAdminUsername)
                    .password(passwordEncoder.encode(defaultAdminPassword))
                    .createdDate(now)
                    .createdBy(defaultAdminUsername)
                    .lastModifiedDate(now)
                    .lastModifiedBy(defaultAdminUsername)
                    .enabled(EntityStatus.ENABLED)
                    .locked(EntityStatus.UNLOCKED)
                    .authorities(authorities)
                    .build();
            System.out.printf("\n\tCreate [ admin ]  user with password [ %s ]\n\n", defaultAdminPassword);
            userRepo.save(admin);
        }
    }
}
