package br.com.rodslab.orderapi.events;

import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

public class RemoteServiceInvocationEvent implements ServiceInvocationEvent {

	private static final String REMOTE_ADDRESS_KEY = "ra";
	private static final String USER_ID_KEY = "uid";
	private static final String CONTRACT_ID_KEY = "cid";
	private static final String SESSION_ID_KEY = "sessionId";
	private static final String QUERY_STRING_KEY = "queryString";

	private final String serviceId;
	private final String transactionId;
	private final long occurredOn;

	private final String customerId;
	private final String remoteAddress;
	private final Optional<String> contractId;
	private final Optional<String> sessionId;
	private final Optional<String> queryString;

	private RemoteServiceInvocationEvent(final String serviceId, final String transactionId, final long occurredOn,
			final String remoteAddress, final String customerId,
		    final Optional<String> contractId,
			final Optional<String> sessionId, final Optional<String> queryString) {
		this.serviceId = serviceId;
		this.transactionId = transactionId;
		this.occurredOn = occurredOn;
		this.customerId = customerId;
		this.remoteAddress = remoteAddress;
		this.contractId = contractId;
		this.sessionId = sessionId;
		this.queryString = queryString;
	}

	public RemoteServiceInvocationEvent(final String remoteAddress, final String customerId,
			final Optional<String> contractId, final Optional<String> sessionId, final Optional<String> queryString) {
		this.customerId = customerId;
		this.remoteAddress = remoteAddress;
		this.contractId = contractId;
		this.sessionId = sessionId;
		this.queryString = queryString;

		occurredOn = System.currentTimeMillis();
		serviceId = null;
		transactionId = UUID.randomUUID().toString();
	}

	@Override
	public Map<String, String> toMap() {
		final Map<String, String> map = new TreeMap<>();
		map.put(REMOTE_ADDRESS_KEY, remoteAddress);
		map.put(SERVICE_ID_KEY, serviceId);
		map.put(TRANSACTION_ID_KEY, transactionId);
		map.put(USER_ID_KEY, customerId);
		map.put(CONTRACT_ID_KEY, contractId.orElse(StringUtils.EMPTY));
		map.put(SESSION_ID_KEY, sessionId.orElse(StringUtils.EMPTY));
		map.put(QUERY_STRING_KEY, queryString.orElse(StringUtils.EMPTY));
		return map;
	}

	@Override
	public ServiceInvocationEvent withServiceId(final String id) {
		if (null != serviceId) {
			return this;
		}

		return new RemoteServiceInvocationEvent(id, transactionId, occurredOn, remoteAddress, customerId,
				   contractId, sessionId, queryString);
	}

	@Override
	public String serviceId() {
		return serviceId;
	}

	@Override
	public String transactionId() {
		return transactionId;
	}

	@Override
	public long occurredOn() {
		return occurredOn;
	}

	@Override
	public String customerId() {
		return customerId;
	}


	@Override
	public String remoteAddress() {
		return remoteAddress;
	}

}
