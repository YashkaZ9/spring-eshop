package com.baykov.springeshop;

import com.baykov.springeshop.config.Postgres;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import javax.transaction.Transactional;

@ActiveProfiles("test")
@SpringBootTest
@ContextConfiguration(initializers = {
        Postgres.Initializer.class
})
@Transactional
//@Sql()
public abstract class SpringEshopApplicationTests {
    @BeforeAll
    static void init() {
        Postgres.container.start();
    }
}

