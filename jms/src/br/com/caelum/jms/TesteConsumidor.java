package br.com.caelum.jms;

import javax.jms.*;
import javax.naming.InitialContext;
import java.util.Scanner;

public class TesteConsumidor {
    public static void main(String[] args) throws Exception {

        System.out.println("Iniciando JMS Listener");

        InitialContext context = new InitialContext();
        ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
        Connection connection = factory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Destination fila = (Destination) context.lookup("financeiro");
        MessageConsumer consumer = session.createConsumer(fila);

        consumer.setMessageListener(new MessageListener() {

            @Override
            public void onMessage(Message message) {

                TextMessage textMessage = (TextMessage) message;

                try {
                    System.out.println("Recebendo message: " + textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }

        });

        new Scanner(System.in).nextLine();
        System.out.println("Finalizando JMS Listener");

        session.close();
        connection.close();
        context.close();

    }
}
