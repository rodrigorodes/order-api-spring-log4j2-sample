package br.com.rodslab.orderapi.events;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

@Component
public class RemoteServiceInvocationEventFactory {

	public ServiceInvocationEvent getInstance(final HttpServletRequest request) {

		final String customerId = "1858544";
		final var contractId = Optional.of("10111444");

		final String remoteAddress = "10.1.1.1.1";
		final Optional<String> sessionId = Optional.ofNullable(request.getRequestedSessionId());
		final Optional<String> queryString = Optional.ofNullable(request.getQueryString());

		return new RemoteServiceInvocationEvent(remoteAddress, customerId, contractId, sessionId, queryString);
	}

}
