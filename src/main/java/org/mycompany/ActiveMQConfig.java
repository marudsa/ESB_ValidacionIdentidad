package org.mycompany;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

import javax.jms.ConnectionFactory;

@Configuration
@PropertySource("classpath:application-dev.properties")
public class ActiveMQConfig {

    @Value("${activemq.broker-url}")
    private String brokerUrl;

    @Value("${activemq.username}")
    private String username;

    @Value("${activemq.password}")
    private String password;

    @Bean
    public ConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
        factory.setBrokerURL(brokerUrl);
        factory.setUserName(username);
        factory.setPassword(password);
        return new CachingConnectionFactory(factory);
    }

    @Bean
    public JmsTransactionManager jmsTransactionManager() {
        return new JmsTransactionManager(connectionFactory());
    }

    @Bean
    public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory) {
        JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory(connectionFactory);
        return template;
    }

    @Bean
    public DefaultMessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory, ListenerESB1 listenerTransESB1) {
        DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setDestinationName("AMQ_VALIDENTIDADREGISTRADURIA.ESB1.Q.IN");
        container.setMessageListener(listenerTransESB1);
        return container;
    }

    @Bean
    public DefaultMessageListenerContainer responseListenerContainer(ConnectionFactory connectionFactory, ResponseTransformer responseTransformer) {
        DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setDestinationName("AMQ_VALIDENTIDADREGISTRADURIA.ESB1.Q.OUT");
        container.setMessageListener(responseTransformer);
        return container;
    }

}
