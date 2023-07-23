package org.mycompany;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class ResponseTransformer implements MessageListener {

    @Autowired
    private JmsTemplate jmsTemplate;
    
    @Override
    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            try {
                TextMessage textMessage = (TextMessage) message;
                String messageText = textMessage.getText();
                
                // Transformar el mensaje eliminando los campos no deseados
                String transformedMessage = transformResponse(messageText);

                // Enviar el mensaje transformado a la cola "AMQ.CA.Q.RES"
                jmsTemplate.convertAndSend("AMQ.CA.Q.RES", transformedMessage);
            } catch (JMSException e) {
                // Manejar excepciones en caso de error al procesar el mensaje
                e.printStackTrace();
            }
        } else {
            // Manejar otros tipos de mensajes, si es necesario
        }
    }

    public String transformResponse(String message) {
        // Realizar la transformación del mensaje SOAP, eliminando los campos no deseados
        String transformedMessage = message.replaceAll("<particula>.*?</particula>\\s*", "")
                .replaceAll("<segNombre>.*?</segNombre>\\s*", "")
                .replaceAll("<munExp>.*?</munExp>\\s*", "")
                .replaceAll("<depExpn>.*?</depExpn>\\s*", "")
                .replaceAll("<fechaExp>.*?</fechaExp>\\s*", "")
                .replaceAll("<numRes>.*?</numRes>\\s*", "");
        
        return transformedMessage;
    }
}
