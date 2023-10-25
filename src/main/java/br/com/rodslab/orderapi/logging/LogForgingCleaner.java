package br.com.rodslab.orderapi.logging;

import java.util.Objects;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

public enum LogForgingCleaner {

	;

	private static final String NULL = "<NULL>";

	private static final char QUOTES_CHAR = '\'';

	private static final String LOG_LEVELS_REGEX = "(DEBUG|ERROR|INFO|TRACE|WARN)";
	private static final String LOG_LEVELS_REPLACEMENT = "{LOGLEVEL:$1}";

	public static String clean(final Object o) {
		final String s = StringUtils.trimToNull(Objects.toString(o, null));

		if (null == s) {
			return NULL;
		}

		return quotes(replaceLogLevels(escape(s)));
	}

	/**
	 * Really useful when using SLF4J. Can wrap many log arguments in a single call.
	 *
	 * @param args
	 *            All arguments that must be cleaned to be logged.
	 * @return All arguments, cleaned and as Objects (SLF4J requirements).
	 */
	public static Object[] clean(final Object... args) {
		Objects.requireNonNull(args, "args");
		final String[] result = new String[args.length];

		for (int i = 0, len = args.length; i < len; i++) {
			result[i] = clean(args[i]);
		}

		return result;
	}

	private static String quotes(final Object o) {
		return StringUtils.wrap(Objects.toString(o), QUOTES_CHAR);
	}

	private static String escape(final Object o) {
		return StringEscapeUtils.escapeJava(Objects.toString(o));
	}

	private static String replaceLogLevels(final String s) {
		return s.replaceAll(LOG_LEVELS_REGEX, LOG_LEVELS_REPLACEMENT);
	}

}
