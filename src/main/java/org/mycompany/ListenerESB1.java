package org.mycompany;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class ListenerESB1 implements MessageListener {

    private JmsTemplate jmsTemplate;

    @Autowired
    public ListenerESB1(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }
    
    @Override
    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            try {
                TextMessage textMessage = (TextMessage) message;
                String messageText = textMessage.getText();
                
                // Transformar el mensaje eliminando el campo "tipoDocumento"
                String transformedMessage = transformMessage(messageText);

                // Enviar el mensaje transformado a la cola "AMQ.IA.REGISOAP.Q.IN"
                jmsTemplate.convertAndSend("AMQ.IA.REGISOAP.Q.IN", transformedMessage);
            } catch (JMSException e) {
                // Manejar excepciones en caso de error al procesar el mensaje
                e.printStackTrace();
            }
        } else {
            // Manejar otros tipos de mensajes, si es necesario
        }
    }

    public String transformMessage(String message) {
        // Eliminar el campo <tipoDocumento> y su contenido del mensaje SOAP, incluyendo el espacio en blanco
        String transformedMessage = message.replaceAll("<tipoDocumento>.*?</tipoDocumento>\\s*", "");
        return transformedMessage;
    }
}
