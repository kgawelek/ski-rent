package com.io.skirent;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    // URIs:
    private static final String adminUri = "/admin/**";
    private static final String employeeUri = "/employee/**";
    private static final String clientUri = "/client/**";
    private static final String apiUserUri = "/api/user/**";
    private static final String registerUri = "/api/user/register";

    // roles:
    private static final String rolePrefix = "ROLE_";
    private static final String adminRole = "ADMIN";
    private static final String employeeRole = "EMPLOYEE";
    private static final String clientRole = "CLIENT";

    // login:
    private static final String loginPage = "/login.jsp";
    private static final String accessDenied = "?forbidden=true";
    private static final String loginFailurePostfix = "?login=error";
    private static final String loginProcessingUrl = "/login";

    // logout:
    private static final String logoutUrl = "/logout";
    private static final String logoutSuccessfulUrl = "/index.html?logout=success";

    /**
     * Accounts configuration: setting users and their roles, pulling data from database.
     * @param auth security builder
     * @throws Exception when jdbcAuthentication() fails
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        String authoritiesQuery = """
                select email,'%s' as authority
                from accounts ac join "admin" ad on ac.user_id = ad.id
                union
                select email,'%s' as authority
                from accounts ac join employee em on ac.user_id = em.id
                union
                select email,'%s' as authority
                from accounts ac join client cl on ac.user_id = cl.id
                where email = ?
                """
                .formatted(rolePrefix + adminRole, // admin role
                    rolePrefix + employeeRole, // employee role
                    rolePrefix + clientRole); // client role

        // retrieve auth data from database:
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(NoOpPasswordEncoder.getInstance()) // TODO change to REAL password encoder, e.g. BCrypt
                .usersByUsernameQuery("""
                        select email,"password",true
                        from accounts ac
                        where email = ?
                        """
                ) // query for retrieving list of accounts
                .authoritiesByUsernameQuery(authoritiesQuery); // query for retrieving list of roles of these accounts
    }

    /**
     * Configuration of http requests permissions: assigning URIs to roles, defining login page and redirects.
     * @param http http requests config
     * @throws Exception when configuration fails
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(adminUri).hasRole(adminRole)
                .antMatchers(employeeUri).hasAnyRole(employeeRole, adminRole)
                .antMatchers(clientUri).hasRole(clientRole)
                .antMatchers(registerUri).anonymous()
                .antMatchers(apiUserUri).hasAnyRole(adminRole, employeeRole, clientRole)
                .anyRequest().permitAll()
                .and()
                .formLogin().loginPage(loginPage + accessDenied)
                .failureUrl(loginPage + loginFailurePostfix)
                .loginProcessingUrl(loginProcessingUrl)
                .permitAll()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher(logoutUrl))
                .logoutSuccessUrl(logoutSuccessfulUrl)
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true);

        http.csrf().disable();  // disable CSRF, used for testing POST requests, not needed (?) in final version
    }
}
