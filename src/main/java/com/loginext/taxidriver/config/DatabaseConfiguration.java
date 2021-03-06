package com.loginext.taxidriver.config;

import com.opentable.db.postgres.embedded.EmbeddedPostgres;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.flyway.FlywayDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@EnableJpaRepositories
public class DatabaseConfiguration {

    @Configuration
    @Profile({"!embedded"})
    static class CloudConfig {
        @Bean
        @Primary
        LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder) {
            return builder.dataSource(db())
                    .packages("com.loginext.taxidriver")
                    .persistenceUnit("postgres")
                    .build();
        }

        @Bean
        @Primary
        @ConfigurationProperties(prefix = "datasource.postgres")
        DataSource db(){
            HikariConfig dataSource = new HikariConfig();
            dataSource.setDriverClassName("org.postgresql.Driver");
            dataSource.setJdbcUrl(System.getenv("DB_CONN_URL"));
            dataSource.setUsername(System.getenv("DB_USERNAME"));
            dataSource.setPassword(System.getenv("DB_PASSWORD"));
            return  new HikariDataSource(dataSource);
        }
    }

    @Configuration
    @Profile("embedded")
    static class LocalConfig {
        @Bean
        @Primary
        LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder) throws IOException {
            return builder.dataSource(db())
                    .packages("com.loginext.taxidriver")
                    .persistenceUnit("postgres")
                    .build();
        }

        @Bean
        @Primary
        @FlywayDataSource
        DataSource db() throws IOException {
            EmbeddedPostgres pg = EmbeddedPostgres.builder().setPort(62776).start();
            return pg.getPostgresDatabase();
        }
    }


}
