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
import static yehor.epam.cinema_final_project_spring.utils.constants.Constants.ADMIN_ROLE;
import static yehor.epam.cinema_final_project_spring.utils.constants.Constants.USER_ROLE;

@Slf4j
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomUserDetailsService customUserDetailsService;

    @Autowired
    public WebSecurityConfig(CustomUserDetailsService customUserDetailsService) {
        log.info("Entry to WebSecurityConfig(CustomUserDetailsService customUserDetailsService)");
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(Constants.PASSWORD_ENCODE_STRENGTH);
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        log.info("Entry to DaoAuthenticationProvider authenticationProvider()");
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(this.customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        log.info("Entry to configure(AuthenticationManagerBuilder auth)");
        auth.authenticationProvider(authenticationProvider());
    }

   /* @Bean
    public AuthenticationEntryPoint getAuthenticationEntryPoint() {
        return (request, response, authException) -> {
            if (authException != null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.sendRedirect("/error/access-denied");
            }
        };
    }*/

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.info("Enter to configure(HttpSecurity http) method");
        http
                .authorizeRequests()
                    .antMatchers("/user/**").hasRole(USER_ROLE)
                    .antMatchers("/swagger-ui/**").hasRole(ADMIN_ROLE)
                    .antMatchers("/admins/**").hasRole(ADMIN_ROLE)
                    .antMatchers("/logout").hasAnyRole(USER_ROLE, ADMIN_ROLE)
                    .antMatchers("/signup*").anonymous()
                    .antMatchers("/films/**", "/sessions/**").permitAll()
                    .antMatchers("/", "/main", "/error/**").permitAll()
                    .antMatchers("/js/**", "/css/**", "/images/**", "/resources/**").permitAll()
                    .anyRequest().hasRole(ADMIN_ROLE)
                .and()
                    .exceptionHandling().accessDeniedPage("/error/access-denied")
                    /*.authenticationEntryPoint(getAuthenticationEntryPoint())*/
                .and()
                    .formLogin().loginPage("/login").permitAll()
                    .loginProcessingUrl("/process_login")
                    .defaultSuccessUrl("/user/profile")
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