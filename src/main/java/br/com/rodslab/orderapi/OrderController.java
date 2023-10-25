package br.com.rodslab.orderapi;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rodslab.orderapi.events.RemoteServiceInvocationEventFactory;

@RestController
@RequestMapping("api")
public class OrderController {
	
	private final OrderService orderService;
	private final RemoteServiceInvocationEventFactory remoteServiceInvocationEventFactory;

	public OrderController(OrderService orderService,
			RemoteServiceInvocationEventFactory remoteServiceInvocationEventFactory) {
		this.orderService = orderService;
		this.remoteServiceInvocationEventFactory = remoteServiceInvocationEventFactory;
	}

	@GetMapping("order/new")
	public void newOrder(final HttpServletRequest request) {
		
		orderService.execute(remoteServiceInvocationEventFactory.getInstance(request));
	}

}
