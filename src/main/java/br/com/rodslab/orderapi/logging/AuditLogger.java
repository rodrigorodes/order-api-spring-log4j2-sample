package br.com.rodslab.orderapi.logging;

import java.util.EnumMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.apache.logging.log4j.message.MapMessage;

import com.google.common.collect.ImmutableMap;


public class AuditLogger {

	private static final Logger LOGGER = LogManager.getLogger(AuditLogger.class);
	private static final Marker MARKER = MarkerManager.getMarker("AUDIT");

	private static final String DEFAULT_SUCCESS_MESSAGE = "Success";
	private static final String ERROR_MESSAGE_FORMAT = "[%s] %s";

	private static final String OPERATION_ID_KEY = "goid";
	private static final String REQUEST_ID_KEY = "grid";
	private static final String TEXT_KEY = "text";
	private static final String DURATION = "duration";
	private long startTime;
	private long endTime;


	// Cache of instances.
	private static final Map<Operation, AuditLogger> instances = new EnumMap<>(Operation.class);

	private final Operation operation;

	protected AuditLogger(final Operation operation) {
		startTime = System.nanoTime();
		this.operation = operation;
	}

	public static AuditLogger getInstance(final Operation operation) {
		if (instances.containsKey(operation)) {
			return instances.get(operation);
		}

		final AuditLogger instance = new AuditLogger(operation);
		instances.put(operation, instance);

		return instance;
	}

	private static String newRequestId() {
		return StringUtils.truncate(DigestUtils.sha1Hex(UUID.randomUUID().toString()), 7);
	}

	private static String escape(final String s) {
		return null == s //
				? "<NULL>" //
				: StringUtils.deleteWhitespace(s.replaceAll("\\s", "<space>"))//
						.replace("<space>", " ")//
						.replace('|', ';');
	}

	private MapMessage newMapMessage(final String text) {
		final ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();
		builder.put(OPERATION_ID_KEY, operation.code());
		builder.put(REQUEST_ID_KEY, newRequestId());
		builder.put(TEXT_KEY, escape(text));
		try{
			long duration = (System.nanoTime() - startTime);
			builder.put(DURATION, String.valueOf(duration));
		}catch (Exception e) {

		}
		return new MapMessage(builder.build());
	}

	private MapMessage newErrorMapMessage(final Class<? extends Throwable> tClass, final String tMessage) {
		return newMapMessage(String.format(ERROR_MESSAGE_FORMAT, tClass.getSimpleName(), tMessage));
	}

	public void success() {
		LOGGER.info(MARKER, newMapMessage(DEFAULT_SUCCESS_MESSAGE));
	}

	public void success(final String text) {
		LOGGER.info(MARKER, newMapMessage(text));
	}

	public void failure(final Throwable t) {
		LOGGER.error(MARKER, newErrorMapMessage(t.getClass(), t.getMessage()));
	}

	public void failure(final String text) {
		LOGGER.error(MARKER, newMapMessage(text));
	}

}
