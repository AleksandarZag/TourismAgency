package com.empirica.tourismagency.configuration;


import com.empirica.tourismagency.maintenance.implementation.UserSecurityMaintenance;
import com.empirica.tourismagency.applicability.SecurityUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


	@Autowired
	private UserSecurityMaintenance userSecurityMaintenance;

	@Autowired
	private CustomLoginHandler customLoginHandler;

	private BCryptPasswordEncoder passwordEncoder() {
		return SecurityUtility.passwordEncoder();
	}

	private static final String[] PUBLIC_MATCHERS = {
			"/**",
			"/css/**",
			"/js/**",
			"/image/**",
			"/img/**",
			"/scss/**",
			"/fonts/**",
			"/login",
			"/newUser",
			"/forgetPassword",
			"/fonts/**",
			"/tourguide",
			"/tourDetail/**",
			"/hours",
			"faq",
			"/searchByCategory",
			"/searchTour",
			"/admin"

	};
	private static final String [] ADMIN_MATCHERS = {
			"/myProfile",
			"/forgetPassword",
			"/listOfCreditCards",
			"/addNewCreditCard",
			"/"
	};

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.authorizeRequests()
				/*.antMatchers("/**").permitAll()*/
				.antMatchers(PUBLIC_MATCHERS).permitAll()
                .antMatchers("/home").hasAnyAuthority("ROLE_ADMIN")
				.antMatchers(ADMIN_MATCHERS).hasAnyAuthority("ROLE_USER")
				.anyRequest().authenticated();


		http
				.csrf().disable().cors().disable()
				.formLogin().failureUrl("/login?error")
				.successHandler(customLoginHandler)
				.loginPage("/login").permitAll()
				.and()
				.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/?logout").deleteCookies("remember-me").permitAll()
				.and()
				.rememberMe();


	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userSecurityMaintenance).passwordEncoder(passwordEncoder());
	}


}
