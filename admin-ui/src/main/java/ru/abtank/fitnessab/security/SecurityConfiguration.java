package ru.abtank.fitnessab.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.abtank.fitnessab.persist.repositories.UserRepository;

import javax.servlet.http.HttpServletResponse;

//@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(securedEnabled = true)  //включаем защиту на уровне метод
//@Profile("unknown")
public class SecurityConfiguration {
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    public void setJwtRequestFilter(JwtRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }


    @Autowired
    public void authConfigure(AuthenticationManagerBuilder auth
            , UserDetailsService userDetailsService
            , PasswordEncoder passwordEncoder
    ) throws Exception {
//         -----DAO auth----
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        auth.authenticationProvider(provider);

//        создание пользователя в in memory при запуске системы
        auth.inMemoryAuthentication()
                .withUser("in_user")
                .password(passwordEncoder.encode("123")) // генерим пароль https://bcrypt-generator.com/
                .roles("ADMIN")
        ;

    }

    //    определяем области доступа через внутренний клас (можно и без него через наследования напрямую)
    @Configuration
    @Order(100) //порядок загрузки
    public static class UiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests()
                    .antMatchers("/").permitAll()  // доступ для всех
                    .antMatchers("/css/*").permitAll()
                    .antMatchers("/js/*").permitAll()
                    .antMatchers("/font-awesome/*").permitAll()
                    .antMatchers("/plugins/*").permitAll()
                    .antMatchers("/exercise/**").authenticated()
                    .antMatchers("/role/**").hasRole("ADMIN")  // ограничение по роли
                    .antMatchers("/mode/**").hasRole("ADMIN")  // ограничение по роли
                    .antMatchers("/type/**").hasRole("ADMIN")  // ограничение по роли
                    .antMatchers("/user/**").hasAnyRole("ADMIN", "USER")  // ограничение по роли
                    .and()
                    .formLogin();
        }

    }

    // пока не получается чтоб работали оба адаптера разом.. Кто первый тот и работает )))
    //    настройка области доступак к REST API
//    @Configuration
//    @Order(10)
//    public static class APIWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
//        @Override
//        protected void configure(HttpSecurity http) throws Exception {
//            http
//                    .authorizeRequests()  //начинаем описывать конфогурацию
//                    .antMatchers("/api/**").authenticated() // доступ для всех
////                    .antMatchers("/api/**").hasRole("ADMIN")  // доступ для всех
//                    .and()
//                    .httpBasic()//базовая авторизация
//                    .authenticationEntryPoint((httpServletRequest, httpServletResponse, exception) -> {  // обработчик ошибок при авторизации
//                        httpServletResponse.setContentType("application/json");                          // вывод ошибки в формате json, а не редиректило на html страницу
//                        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                        httpServletResponse.setCharacterEncoding("UTF-8");
//                        httpServletResponse.getWriter().println("{\"error\": \"" + exception.getMessage() + "\" }");
//                    })
//                    .and()
//                    .csrf().disable()// отключаем проверку csrf так как для REST сервисов она не нужна. (так как нет сессии (только State Less))
//                    .sessionManagement()
//                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        }
//
//    }

//   JwtRequest oauth
    @Configuration
    @Order(5)
    public static class RestSecurityConfig extends WebSecurityConfigurerAdapter {
        private JwtRequestFilter jwtRequestFilter;
        private UserDetailsService userDetailsService;

        @Autowired
        public void setUserDetailsService(UserDetailsService userDetailsService) {
            this.userDetailsService = userDetailsService;
        }

        @Autowired
        public void setJwtRequestFilter(JwtRequestFilter jwtRequestFilter) {
            this.jwtRequestFilter = jwtRequestFilter;
        }
        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService);
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable().authorizeRequests()
                    .antMatchers("/api/**").authenticated()
                    .anyRequest().permitAll()
                    .and()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        }

        @Override
        @Bean
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }
    }

}

