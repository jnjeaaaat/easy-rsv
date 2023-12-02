package shop.jnjeaaaat.easyrsv.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import shop.jnjeaaaat.easyrsv.config.filter.JwtAuthenticationFilter;
import shop.jnjeaaaat.easyrsv.utils.JwtTokenProvider;

@Configuration
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        log.info("[configure] Security configure start");
        httpSecurity.httpBasic().disable()

                .csrf().disable()

                .sessionManagement()
                .sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS
                )

                .and()
                .authorizeRequests()
                .antMatchers("/**/sign-up").permitAll()
                .antMatchers("/**/sign-in").permitAll()
                .antMatchers("/easy-rsv/v1/exception").permitAll()
                .antMatchers(HttpMethod.PUT,"/easy-rsv/admin/auth").permitAll()


                // 유저 관련 API
                .antMatchers("/easy-rsv/v1/user/**").hasAnyRole("USER")

                // 상점 관련 API
                .antMatchers(HttpMethod.GET, "/easy-rsv/v1/shop/**").hasAnyRole("USER")
                .antMatchers(HttpMethod.POST, "/easy-rsv/v1/shop").hasAnyRole("PARTNER")
                .antMatchers(HttpMethod.PUT, "easy-rsv/v1/shop/**").hasAnyRole("PARTNER")
                .antMatchers(HttpMethod.DELETE, "easy-rsv/v1/shop/**").hasAnyRole("PARTNER")

                .antMatchers("**exception**").permitAll()

                .anyRequest().hasRole("ADMIN")

                .and()
                .exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler())
                .and()
                .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint())

                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class)
                ;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers(
                        "/swagger-resources/**",
                        "/swagger-ui.html",
                        "/webjars/**",
                        "/swagger/**",
                        "/easy-rsv/v1/exception"
                );
    }
}
