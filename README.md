# THLTWWW_WEEK9
Bai tap tuan 9

# Thực hành theo hướng dẫn spring security với database

Khởi tạo với name và password:
```
spring.security.user.name=a
spring.security.user.password=a
```
In Memory Authentication:
```
    @Autowired
    public void configSecurityWeb (AuthenticationManagerBuilder auth, PasswordEncoder passwordEncoder ) throws Exception {
        auth.inMemoryAuthentication()
            .withUser(User.withUsername("Gnoodd")
                .password(passwordEncoder.encode("Gnoodd"))
                .roles("ADMIN")
                .build())
        .withUser(User.withUsername("Dong")
                .password(passwordEncoder.encode("Dong"))
                .roles("User")
                .build());

    }
```
fix lỗi: ```spring.main.allow-circular-references=true```

In JDBC Authentication:
```
    @Autowired
    public void configSecurityWeb (AuthenticationManagerBuilder auth, PasswordEncoder passwordEncoder, DataSource dataSource) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource).withDefaultSchema()
            .withUser(User.withUsername("admin")
                .password(passwordEncoder.encode("admin"))
                .roles("ADMIN")
                .build())
        .withUser(User.withUsername("user")
                .password(passwordEncoder.encode("user"))
                .roles("User")
                .build());

    }
```

