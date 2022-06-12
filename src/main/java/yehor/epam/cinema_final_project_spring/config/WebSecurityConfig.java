package yehor.epam.cinema_final_project_spring.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import yehor.epam.cinema_final_project_spring.security.CustomUserDetailsService;
import yehor.epam.cinema_final_project_spring.utils.constants.Constants;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomUserDetailsService customUserDetailsService;

    @Autowired
    public WebSecurityConfig(CustomUserDetailsService customUserDetailsService) {
        log.warn("Entry to WebSecurityConfig(CustomUserDetailsService customUserDetailsService)");
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(Constants.PASSWORD_ENCODE_STRENGTH);
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        log.warn("Entry to DaoAuthenticationProvider authenticationProvider()");
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(this.customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        log.warn("Entry to configure(AuthenticationManagerBuilder auth)");
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public AuthenticationEntryPoint getAuthenticationEntryPoint() {
        return (request, response, authException) -> {
            if (authException != null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.sendRedirect("/error/access-denied");
            }
        };
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.warn("Enter to configure(HttpSecurity http) method");
        http
                .authorizeRequests()
                    .antMatchers("/user/profile").hasAuthority("USER")
                    .antMatchers("/swager/**").hasAuthority("ADMIN")
                    .antMatchers("/logout").hasAnyAuthority("USER", "ADMIN")
                    .antMatchers("/login*", "/signup*").anonymous()
                    .antMatchers("/", "/main", "/error/**").permitAll()
                    .antMatchers("/js/**", "/css/**", "/images/**", "/resources/**").permitAll()
                    .anyRequest().hasAuthority("ADMIN")
                .and()
                    .exceptionHandling().accessDeniedPage("/error/access-denied")
                    .authenticationEntryPoint(getAuthenticationEntryPoint())
                .and()
                    .formLogin().loginPage("/login")
                    .loginProcessingUrl("/process_login")
                    .defaultSuccessUrl("/user/profile", true)
                    .failureUrl("/login?error")
                .and()
                    .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/")
                    .deleteCookies("JSESSIONID")
                    .deleteCookies("REMEMBER-ME")
                    .invalidateHttpSession(true)
                .and()
                    .rememberMe()
                    .key("remember-me-key")
                    .rememberMeParameter("rememberMe")
                    .rememberMeCookieName("REMEMBER-ME")
                    .tokenValiditySeconds(Constants.COOKIE_LOGIN_LIFETIME)
                    .userDetailsService(this.customUserDetailsService)
        ;
    }


}