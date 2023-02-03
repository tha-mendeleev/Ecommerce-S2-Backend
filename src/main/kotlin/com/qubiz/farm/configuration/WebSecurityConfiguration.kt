package com.qubiz.farm.configuration

import com.qubiz.farm.AppConstants
import com.qubiz.farm.securities.JwtUserDetailsService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@Configuration
@EnableWebSecurity
class WebSecurityConfiguration {

    @Bean
    @Throws(exceptionClasses = [Exception::class])
    fun filterChain(httpSecurity: HttpSecurity, jwtAuthenticationFilter: JwtAuthenticationFilter): SecurityFilterChain {
        httpSecurity
            .csrf().disable()
            .cors()
            .and()
            .authorizeHttpRequests { req ->
                req
                    .antMatchers(AppConstants.BASE_PATH + "/seller/**")
                    .hasAnyAuthority("ROLE_SELLER")
                    .antMatchers(AppConstants.BASE_PATH + "/admin/**")
                    .hasAnyAuthority("ROLE_ADMIN")
                    .anyRequest()
                    .permitAll()
                    .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            }
            .httpBasic()
        return httpSecurity.build()
    }

    @Bean
    @kotlin.jvm.Throws
    fun authManager(httpSecurity: HttpSecurity, jwtUserDetailsService: JwtUserDetailsService, customAuthenticationProvider: CustomAuthenticationProvider): AuthenticationManager {
        val authBuilder = httpSecurity.getSharedObject(AuthenticationManagerBuilder::class.java)
        authBuilder.userDetailsService(jwtUserDetailsService)
            .passwordEncoder(passwordEncoder())
            .and()
            .authenticationProvider(customAuthenticationProvider)
        return authBuilder.build()
    }

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource? {
        val configuration = CorsConfiguration()
        configuration.allowedOriginPatterns = listOf("*")
        configuration.allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS")
        configuration.allowCredentials = true
        configuration.addAllowedOriginPattern("*")
        configuration.addAllowedHeader("*")
        configuration.addAllowedMethod("*")
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }

    @Bean
    fun corsConfigurer(): WebMvcConfigurer? {
        return object : WebMvcConfigurer {
            override fun addCorsMappings(registry: CorsRegistry) {
                registry.addMapping("/**").allowedOriginPatterns("*")
                    .allowCredentials(true)
                    .allowedMethods("POST", "GET", "PUT")
            }
        }
    }
}
