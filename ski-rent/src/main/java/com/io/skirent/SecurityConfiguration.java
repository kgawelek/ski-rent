package com.io.skirent;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    private static final String adminUri = "/admin/**";
    private static final String employeeUri = "/employee/**";
    private static final String clientUri = "/client/**";
    private static final String apiUserUri = "/api/user/**";

    private static final String adminRole = "ADMIN";
    private static final String employeeRole = "EMPLOYEE";
    private static final String clientRole = "CLIENT";
    private static final String userRole = "USER";

    private static final String loginPage = "/login.jsp";
    private static final String loginFailurePostfix = "?error=1";
    private static final String loginProcessingUrl = "/login";
    private static final String logoutSuccessfulUrl = "/index.html";

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        // TODO: change to database ↓
//        auth.inMemoryAuthentication()
//                .withUser("user")
//                .password(encoder.encode("password"))
//                .roles("USER")
//                .and()
//                .withUser("admin")
//                .password(encoder.encode("admin"))
//                .roles("ADMIN");

        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .withDefaultSchema()
                .usersByUsernameQuery(
                        "select email,\"password\",true "
                                + "from accounts ac join \"admin\" ad on ac.user_id = ad.id"
                                + "where email = ?"
                )
                .authoritiesByUsernameQuery(
                        "select email,ROLE_ADMIN "
                                + "from accounts ac join \"admin\" ad on ac.user_id = ad.id"
                                + "where email = ?"
                );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(adminUri).hasRole(adminRole)
                .antMatchers(employeeUri).hasRole(employeeRole)
                .antMatchers(clientUri).hasRole(clientRole)
                .antMatchers(apiUserUri).hasRole(userRole)
                .anyRequest().permitAll()
                .and()
                .formLogin().loginPage(loginPage)
                .failureUrl(loginPage + loginFailurePostfix)
                .loginProcessingUrl(loginProcessingUrl)
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl(logoutSuccessfulUrl);
    }
}
