package io.bootify.kpo.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EntityScan("io.bootify.kpo.domain")
@EnableJpaRepositories("io.bootify.kpo.repos")
@EnableTransactionManagement
public class DomainConfig {
}
