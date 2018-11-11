package pl.pollub.edu.cardGame.authentication.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import pl.pollub.edu.cardGame.authentication.filter.AuthenticationFilter;
import pl.pollub.edu.cardGame.authentication.handler.AuthenticationFailureHandler;
import pl.pollub.edu.cardGame.authentication.handler.AuthenticationSuccessHandler;
import pl.pollub.edu.cardGame.authentication.handler.LogoutSuccessHandler;
import pl.pollub.edu.cardGame.authentication.handler.RestAuthenticationEntryPoint;
import pl.pollub.edu.cardGame.authentication.notifier.UserLogoutNotifier;
import pl.pollub.edu.cardGame.authentication.provider.Authenticator;
import pl.pollub.edu.cardGame.common.config.SessionListener;
import pl.pollub.edu.cardGame.game.organization.destroyer.GameDestroyer;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
@RequiredArgsConstructor
public class AuthenticationConfig extends WebSecurityConfigurerAdapter {

    private static final String AUTH_PATH = "/api/authentication";

    private final Authenticator authenticator;
    private final ObjectMapper objectMapper;
    private final GameDestroyer gameDestroyer;
    private final UserLogoutNotifier userLogoutNotifier;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new SessionListener(gameDestroyer);
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public AuthenticationFilter authenticationFilter() throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter();
        authenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler());
        authenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler());
        authenticationFilter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(AUTH_PATH, HttpMethod.POST.name()));
        authenticationFilter.setAuthenticationManager(authenticationManagerBean());
        authenticationFilter.setSessionAuthenticationStrategy(sessionAuthenticationStrategy());
        authenticationFilter.setAllowSessionCreation(true);
        return authenticationFilter;
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new AuthenticationSuccessHandler(objectMapper);
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new AuthenticationFailureHandler();
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new LogoutSuccessHandler();
    }

    @Bean
    public SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new SessionAuthenticationStrategyImpl(sessionRegistry(), userLogoutNotifier);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authenticator).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.headers().cacheControl();

        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, AUTH_PATH, "/api/authentication/registration").permitAll()
                .anyRequest().authenticated();

        http.exceptionHandling()
                .authenticationEntryPoint(new RestAuthenticationEntryPoint())
                .and()
                .csrf().disable()
                .formLogin()
                .permitAll()
                .loginProcessingUrl(AUTH_PATH)
                .usernameParameter("login")
                .passwordParameter("password")
                .successHandler(authenticationSuccessHandler())
                .failureHandler(authenticationFailureHandler())
                .and()
                .addFilterBefore(authenticationFilter(), AuthenticationFilter.class)
                .logout()
                .deleteCookies()
                .invalidateHttpSession(true)
                .permitAll()
                .logoutRequestMatcher(new AntPathRequestMatcher(AUTH_PATH, HttpMethod.DELETE.name()))
                .logoutSuccessHandler(logoutSuccessHandler())
                .and()
                .sessionManagement()
                .sessionAuthenticationStrategy(sessionAuthenticationStrategy())
                .maximumSessions(-1)
                .sessionRegistry(sessionRegistry())
                .maxSessionsPreventsLogin(true);
    }

}
