package br.com.caelum.jms;

import javax.jms.*;
import javax.naming.InitialContext;
import java.util.Scanner;

public class TesteProdutor {
    public static void main(String[] args) throws Exception {

        System.out.println("Iniciando JMS Producer");

        InitialContext context = new InitialContext();
        ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
        Connection connection = factory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Destination fila = (Destination) context.lookup("financeiro");

        MessageProducer messageProducer = session.createProducer(fila);
        Message message = session.createTextMessage("<pedido><id>123</id></pedido>");
        messageProducer.send(message);

//        new Scanner(System.in).nextLine();
        System.out.println("Finalizando JMS Producer");

        session.close();
        connection.close();
        context.close();

    }
}
