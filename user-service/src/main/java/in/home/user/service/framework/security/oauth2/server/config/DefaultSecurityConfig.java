package in.home.user.service.framework.security.oauth2.server.config;

import in.home.user.api.model.Role;
import in.home.user.api.model.User;
import in.home.user.api.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;
import java.util.stream.Collectors;

import static org.springframework.security.config.Customizer.withDefaults;

@Slf4j
@EnableWebSecurity
public class DefaultSecurityConfig {

    @Autowired
    UserService userService;

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests(authorizeRequests -> {
                    try {
                        authorizeRequests.antMatchers("/user-ws/**")
                                .access("hasAuthority('SCOPE_read')")
                                .and()
                                .oauth2ResourceServer()
                                .jwt();
                    }
                    catch (Exception e) {
                        log.error("Resource Server Authentication Error", e);
                    }
                })
                .authorizeRequests(authorizeRequests -> authorizeRequests.anyRequest()
                        .authenticated())
                .formLogin(withDefaults());
        return http.build();
    }

    @Bean
    UserDetailsService users() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

                User user = userService.loadUserByUsernameWithPassword(username);
                if (user == null) {
                    throw new UsernameNotFoundException("Invalid username or password.");
                }
                return new org.springframework.security.core.userdetails.User(user.getUserName(),
                        user.getPassword(),
                        mapRolesToAuthorities(user.getRoleSet()));
            }

            private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
                return roles.stream()
                        .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                        .collect(Collectors.toList());
            }
        };
    }
}
