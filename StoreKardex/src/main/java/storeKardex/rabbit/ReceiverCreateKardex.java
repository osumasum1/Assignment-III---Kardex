package storeKardex.rabbit;


import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import storeKardex.resources.StoreKardexResource;

public class ReceiverCreateKardex extends Thread {

  private final static String QUEUE_NAME = "createKardex";
  private ConnectionFactory factory;
  private Connection connection;
  private Channel channel;
  private StoreKardexResource resource;
  
  public ReceiverCreateKardex(StoreKardexResource resource) throws IOException, TimeoutException {
	  factory = new ConnectionFactory();
      factory.setHost("localhost");
      this.resource = resource;
      
  }
  
  @Override
  public void run() {
	  try {
		connection = factory.newConnection();
		channel = connection.createChannel();
		
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
	      System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

	      DeliverCallback deliverCallback = (consumerTag, delivery) -> {
	          String message = new String(delivery.getBody(), "UTF-8");
	          System.out.println(" [x] Received '" + message + "'");
	          try {
				resource.createKudo(message);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	      };
	      channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
		
	} catch (IOException | TimeoutException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
      
  }

  public static void main(String[] argv) throws Exception {
      

      
  }
}
