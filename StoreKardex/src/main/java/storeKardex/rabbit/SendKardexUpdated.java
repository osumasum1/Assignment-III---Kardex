package storeKardex.rabbit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class SendKardexUpdated {
	private final static String QUEUE_NAME = "kardexUpdated";
	ConnectionFactory factory;
	
	
	public SendKardexUpdated() {
		factory = new ConnectionFactory();
		factory.setHost("localhost");
	}
	
	public void sendMessage(String productId) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String message = String.valueOf(productId);
            
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + message + "'");
        }
    }
}
