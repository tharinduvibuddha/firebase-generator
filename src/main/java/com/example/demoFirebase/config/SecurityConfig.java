package com.example.demoFirebase.config;

import com.example.demoFirebase.config.authFirebase.FirebaseAuthProvider;
import com.example.demoFirebase.config.authFirebase.FirebaseFilter;
import com.example.demoFirebase.service.FirebaseService;
import com.example.demoFirebase.service.UserService;
import com.example.demoFirebase.service.impl.UserServiceImpl;
import com.sun.xml.internal.ws.api.model.wsdl.WSDLBoundOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig {
    public static class Roles{
        public static final String ANONYMOUS = "ANONYMOUS";
        public static final String USER = "USER";
        static public  final String ADMIN = "ADMIN";

        private static final String ROLE_ = "ROLE_";
        public static final String ROLE_ANONYMOUS = ROLE_+ ANONYMOUS;
        public static final String ROLE_USER = ROLE_ + USER;
        public static final String ROLE_ADMIN  = ROLE_+ADMIN;


    }
    @Order(Ordered.HIGHEST_PRECEDENCE)
    @Configuration
    protected static class AuthenticationSecurity extends GlobalAuthenticationConfigurerAdapter{
        @Autowired
        @Qualifier(Value = UserServiceImpl.NAME)
        private UserDetailsService userService;

        @Value("true")
        private  Boolean firebaseEnabled;

        @Autowired
        private FirebaseAuthProvider firebaseProvider;

        @Override
        public void init(AuthenticationManagerBuilder auth)throws Exception{
            auth.userDetailsService(userService);
            if (firebaseEnabled){
                auth.authenticationProvider(firebaseProvider);

            }
        }


    }


    @Configuration
    @Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
    protected class ApplicationSecurity extends WebSecurityConfigurerAdapter{

        @Value("true")
        private Boolean firebaseEnabled;

        @Override
        public void configure(WebSecurity web) throws Exception{
            web.ignoring().antMatchers("/v2/api-docs","/configuration/ui","/swagger-resources",
                    "/configuration/security","/swagger-ui.html","/webjars/**","/v2/swagger.json");
        }


        @Override
        public void configure(HttpSecurity httpSecurity) throws Exception{

            if (firebaseEnabled){
                httpSecurity.addFilterBefore(tokenAuthorizationFilter(), BasicAuthenticationFilter.class).authorizeRequests()
                      .antMatchers("/api/open/**").hasAnyRole(Roles.ANONYMOUS)
                      .antMatchers("/api/client/**").hasRole(Roles.USER)
                      .antMatchers("/api/admin/**").hasAnyRole(Roles.ADMIN)
                      .antMatchers("/health/**").hasAnyRole(Roles.ADMIN)
                      .antMatchers("/**").denyAll()
                        .and().csrf().disable()
                        .anonymous().authorities(Roles.ROLE_ANONYMOUS);
            }else {
                httpSecurity.httpBasic().and().authorizeRequests()
                        .antMatchers("/api/open/**").hasAnyRole(Roles.ANONYMOUS)
                        .antMatchers("/api/client/**").hasRole(Roles.USER)
                        .antMatchers("/api/admin/**").hasAnyRole(Roles.ADMIN)
                        .antMatchers("/health/**").hasAnyRole(Roles.ADMIN)
                        .antMatchers("/**").denyAll()
                        .and().csrf().disable()
                        .anonymous().authorities(Roles.ROLE_ANONYMOUS);
            }


        }
    }

    @Autowired(required = false)
    private FirebaseService firebaseService;

    private  FirebaseFilter tokenAuthorizationFilter(){
        return new FirebaseFilter(firebaseService);
    }

}
