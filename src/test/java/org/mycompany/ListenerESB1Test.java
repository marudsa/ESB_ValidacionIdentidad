package org.mycompany;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.JMSException;
import javax.jms.TextMessage;

import static org.mockito.Mockito.*;

public class ListenerESB1Test {

    @Mock
    private JmsTemplate jmsTemplate;

    private ListenerESB1 listenerESB1;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        listenerESB1 = new ListenerESB1(jmsTemplate);
    }


    @Test
    public void testOnMessage() throws JMSException {
        // Mock del mensaje JMS
        TextMessage mockTextMessage = mock(TextMessage.class);
        when(mockTextMessage.getText()).thenReturn("Test Payload");

        // Simulación de llamada al método onMessage
        listenerESB1.onMessage(mockTextMessage);

        // Verificar que se llamó correctamente al método convertAndSend del JmsTemplate
        verify(jmsTemplate).convertAndSend(eq("AMQ.IA.REGISOAP.Q.IN"), anyString());
    }
}
