package br.com.rodslab.orderapi;

import org.springframework.stereotype.Component;

import br.com.rodslab.orderapi.logging.AuditLog;
import br.com.rodslab.orderapi.logging.Operation;

@Component
public class OrderPublisher {

	
	@AuditLog(Operation.NEW_ORDER)
	public void send() {
		
	}
}
