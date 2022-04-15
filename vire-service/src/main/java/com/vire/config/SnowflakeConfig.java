package com.vire.config;

import com.vire.utils.Snowflake;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SnowflakeConfig {

    @Bean
    //@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Snowflake snowflake() {
        return new Snowflake();
    }
}
