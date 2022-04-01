package com.szero.security.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    /**
     * 忽略某些请求
     *
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    /**
     * 防御的露点
     *
     * @param http
     * @throws Exception
     */

    @Override
    protected void configure(HttpSecurity http) throws Exception {

//        http.authorizeRequests((requests) -> requests.anyRequest().authenticated());
        SimpleUrlAuthenticationSuccessHandler s = new SimpleUrlAuthenticationSuccessHandler();

        http
                // 资源一
                .authorizeRequests()
                .antMatchers("/assets/**").permitAll()

                .and()

                .authorizeRequests()
                .anyRequest().authenticated()

                .and()

                .formLogin().loginPage("http://localhost:8000/user/login").successHandler(s)

                        .and()

                                .logout().logoutUrl("http://localhost:8000/user/login");



        http.formLogin();
        http.httpBasic();
    }
}
