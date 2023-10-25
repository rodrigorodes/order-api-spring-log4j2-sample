package br.com.rodslab.orderapi.events;

import java.util.Map;

public interface ServiceInvocationEvent {

	public static final String OCCURRED_ON_KEY = "t";
	public static final String SERVICE_ID_KEY = "sid";
	public static final String TRANSACTION_ID_KEY = "tid";

	Map<String, String> toMap();

	/**
	 * <p>
	 * Returns a new instance of this class with <code>id</code> as the service ID, but only if the service ID was not previously set. In other words, <b>this
	 * method must not overwrite a service ID</b>.
	 * </p>
	 *
	 * <p>
	 * If the service ID was already set, <code>this</code> must be returned.
	 * </p>
	 *
	 * @param id
	 * @return (Read description)
	 */
	ServiceInvocationEvent withServiceId(String id);

	String serviceId();

	String transactionId();

	long occurredOn();

	String customerId();

	String remoteAddress();

}
