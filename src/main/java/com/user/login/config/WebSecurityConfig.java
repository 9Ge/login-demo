package com.user.login.config;

import com.user.login.filter.JWTAuthenticationFilter;
import com.user.login.filter.JWTAuthorizationFilter;
import com.user.login.web.handler.SelfAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("userDetailsServiceImpl")
    private UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //关闭跨域保护
        http.cors().and().csrf().disable()
                // 无状态模式，不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                /**
                 * 设置指定一个url需要ADMIN权限，只要是测试用
                 * 这里指定的ADMIN，但交给SpringSecurity的时候需要ROLE_ADMIN
                 */
                .authorizeRequests()
                .antMatchers("/500").permitAll()
                .antMatchers( "/tasks/**").hasRole("ADMIN")

                /**
                 * 其他的都要登录后才能访问
                 */
                .anyRequest().authenticated()
                .and()

                /**
                 * 添加一个拦截器
                 */
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .addFilter(new JWTAuthorizationFilter(authenticationManager()))

                //添加无权限和未登录的处理时的处理
                .exceptionHandling().authenticationEntryPoint(new SelfAuthenticationEntryPoint())
                .accessDeniedHandler(new SelfAuthenticationEntryPoint());

//        http.cors().and().csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/login").permitAll()
//                // 测试用资源，需要验证了的用户才能访问
//                .antMatchers("/tasks/**")
//                .authenticated()
//                .antMatchers(HttpMethod.DELETE, "/tasks/**")
//                .hasRole("ADMIN")
//                .anyRequest().permitAll()
//                .and()
//                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
//                .addFilter(new JWTAuthorizationFilter(authenticationManager()))
//                // 不需要session
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }
}
