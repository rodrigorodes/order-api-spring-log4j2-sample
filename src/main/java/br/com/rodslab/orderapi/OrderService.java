package br.com.rodslab.orderapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import br.com.rodslab.orderapi.events.ServiceCaller;
import br.com.rodslab.orderapi.events.ServiceInvocationEvent;

@Service
public class OrderService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);
	
	private final OrderPublisher orderPublisher;
	public OrderService(OrderPublisher orderPublisher) {
		this.orderPublisher = orderPublisher;
	}

	public void execute(ServiceInvocationEvent event) {
		
		ServiceCaller.begin(event, "101000");
		
		orderPublisher.send();
		
		LOGGER.info("Send Order New");
		
		ServiceCaller.end();
	}

}
