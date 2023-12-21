package sidim.doma.undying.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain


@Configuration
@EnableWebSecurity
class SecurityConfig {

    private val AUTH_WHITELIST = arrayOf( // -- Swagger UI v2
        "/v2/api-docs",
        "/swagger-resources",
        "/swagger-resources/**",
        "/configuration/ui",
        "/configuration/security",
        "/swagger-ui.html",  // -- Swagger UI v3 (OpenAPI)
        "/v3/api-docs/**",
        "/swagger-ui/**"
    )

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .authorizeHttpRequests {
                it
                    .requestMatchers("/**").permitAll()
                    .requestMatchers(AUTH_WHITELIST).permitAll()
                    .anyRequest().fullyAuthenticated()
            }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
        return http.build()
    }

//    @Bean
//    @Throws(Exception::class)
//    fun filterChain(http: HttpSecurity): SecurityFilterChain {
//        http.
//            .antMatchers("/api/auth/login", "/api/auth/token").permitAll()
//            .antMatchers("/webjars/**")
//            .permitAll() //.antMatchers(AUTH_WHITELIST).permitAll() - чтобы включить доступ к Swagger,
//            // надо снять комментарий
//            //auth
//
//            .antMatchers(
//                "/api/auth/logout",
//                "/api/auth/refresh",
//                "/api/manager/account-info",
//                "/api/manager/update-data",
//                "/api/manager/role"
//            ).authenticated() //managers
//
//            .anyRequest().authenticated()
//            .and()
//            .httpBasic().disable()
//            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//
//        //httpSecurity.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)
//
//        return httpSecurity.build()
//    }
}