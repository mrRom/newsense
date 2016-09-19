package com.mr.newsense;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.annotation.Order;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

@SpringBootApplication
@RestController
public class UiApplication {

    @RequestMapping("/user")
    public Principal user(Principal user) {
	return user;
    }
     
    @RequestMapping("/resource")
    public Map<String, Object> home() {
	Map<String, Object> model = new HashMap<String, Object>();
	model.put("id", UUID.randomUUID().toString());
	model.put("content", "newsense");
	return model;
    }

    public static void main(String[] args) {
	SpringApplication.run(UiApplication.class, args);
    }

    @Bean
    public HibernateJpaSessionFactoryBean sessionFactory() {
	return new HibernateJpaSessionFactoryBean();
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
	ResourceBundleMessageSource source = new ResourceBundleMessageSource();
	source.setBasename("classpath:properties/messages");
	source.setDefaultEncoding("UTF-8");
	return source;
    }

    @Autowired
    DataSource dataSource;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth)
	    throws Exception {

	auth.jdbcAuthentication()
		.dataSource(dataSource)
		.usersByUsernameQuery(
			"select username,password, enabled from users where username=?")
		.authoritiesByUsernameQuery(
			"select username, role from user_roles where username=?");
    }

    @Configuration
    @Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
    protected static class SecurityConfiguration extends
	    WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	    //TODO
	    //have to implement proper security
	    http.httpBasic()
		    .and()
		    .authorizeRequests()
		    .antMatchers("/index.html", "/home.html", "/login.html",
			    "/registration.html", "/register", "/news/**",
			    "/stats/**", "/notify", "/listOfSites", "/")
		    .permitAll().anyRequest().authenticated().and().csrf()
		    .csrfTokenRepository(csrfTokenRepository()).and()
		    .addFilterAfter(csrfHeaderFilter(), CsrfFilter.class);
	}

	private Filter csrfHeaderFilter() {
	    return new OncePerRequestFilter() {
		@Override
		protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		    CsrfToken csrf = (CsrfToken) request
			    .getAttribute(CsrfToken.class.getName());
		    if (csrf != null) {
			Cookie cookie = WebUtils.getCookie(request,
				"XSRF-TOKEN");
			String token = csrf.getToken();
			if (cookie == null || token != null
				&& !token.equals(cookie.getValue())) {
			    cookie = new Cookie("XSRF-TOKEN", token);
			    cookie.setPath("/");
			    response.addCookie(cookie);
			}
		    }
		    filterChain.doFilter(request, response);
		}
	    };
	}

	private CsrfTokenRepository csrfTokenRepository() {
	    HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
	    repository.setHeaderName("X-XSRF-TOKEN");
	    return repository;
	}
    }

}
