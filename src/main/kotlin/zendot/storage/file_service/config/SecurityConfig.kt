package zendot.storage.file_service.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import zendot.storage.file_service.config.repository.DomainBucketRepository

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val apiKeyRepository: DomainBucketRepository,
    @Value("\${dashboard.password}") val password: String,
    private val secretValue: String?
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf{csrf->csrf.disable()}
            .authorizeHttpRequests { requests ->
                requests
                    .requestMatchers(
                        HttpMethod.GET,
                        "/api/**",
                        "/swagger-ui",
                        "/swagger-ui/**",
                        "/v3/api-docs",
                        "/v3/api-docs/**",
                        "/actuator/**"
                    ).permitAll()
                    .requestMatchers(HttpMethod.OPTIONS, "/api/**").permitAll()
                    .requestMatchers(HttpMethod.HEAD, "/api/**").permitAll()
                    .anyRequest().authenticated()
            }
            .addFilterBefore(
                ApiKeyAuthenticationFilter(apiKeyRepository),
                UsernamePasswordAuthenticationFilter::class.java
            )
            .formLogin { }
            .httpBasic { }
        return http.build()
    }

    @Bean
    fun authenticationManager(http: HttpSecurity): AuthenticationManager {
        val authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder::class.java)
        authenticationManagerBuilder
            .inMemoryAuthentication()
            .withUser("admin")
            .password(password)
        return authenticationManagerBuilder.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}