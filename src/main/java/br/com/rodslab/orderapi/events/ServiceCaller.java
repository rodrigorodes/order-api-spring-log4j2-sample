package br.com.rodslab.orderapi.events;

import java.util.Map;
import java.util.function.Supplier;

import org.apache.logging.log4j.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum ServiceCaller {

	;

	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceCaller.class);

	public static final void begin(final ServiceInvocationEvent event, final String serviceId) {
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("BEGIN Service {}", serviceId);
		}

		ThreadContext.putAll(event.withServiceId(serviceId).toMap());
	}

	public static final void end() {
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("END Service {}", ThreadContext.get(ServiceInvocationEvent.SERVICE_ID_KEY));
		}

		ThreadContext.clearMap();
	}

	public static final void call(final Runnable service) {
		// Store the context to restore it later.
		final Map<String, String> previousContext = ThreadContext.getImmutableContext();

		// Run the service.
		service.run();

		// Restore the original context.
		ThreadContext.putAll(previousContext);
	}

	public static final <T> T callWithResult(final Supplier<T> service) {
		// Store the context to restore it later.
		final Map<String, String> previousContext = ThreadContext.getImmutableContext();

		// Run the service.
		final T result = service.get();

		// Restore the original context.
		ThreadContext.putAll(previousContext);

		return result;
	}

}
