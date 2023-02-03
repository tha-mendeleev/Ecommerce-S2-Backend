package com.qubiz.farm.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@Configuration
@EnableJpaAuditing
class AuditorBean {
    @Bean
    fun auditorAware(): CustomerAuditorAware {
        return CustomerAuditorAware()
    }
}
