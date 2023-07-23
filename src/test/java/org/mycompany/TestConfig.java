package org.mycompany;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.core.JmsTemplate;

@TestConfiguration
public class TestConfig {

    @Bean
    public JmsTemplate jmsTemplate() {
        return new JmsTemplate();
    }
}
