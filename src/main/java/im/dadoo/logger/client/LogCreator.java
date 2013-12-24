package im.dadoo.logger.client;

import im.dadoo.logger.client.cons.Type;

import java.util.HashMap;
import java.util.Map;

public final class LogCreator {

	private LogCreator() {}
	
	public static DadooLog createFunLog(String serviceName, String functionName, Object[] args, Object ret, Long time) {
		Map<String, Object> content = new HashMap<String, Object>();
		content.put("functionName", functionName);
		content.put("args", args);
		content.put("ret", ret);
		content.put("time", time);
		DadooLog log = new DadooLog(serviceName, Type.FUN, System.currentTimeMillis(), content);
		return log;
	}
	
	public static DadooLog createExceptionLog(String serviceName, String description, Exception ex) {
		Map<String, Object> content = new HashMap<String, Object>();
		content.put("class", ex.getClass().getName());
		content.put("description", description);
		StringBuffer trace = new StringBuffer();
		for (StackTraceElement e : ex.getStackTrace()) {
			trace.append(e.toString());
		}
		content.put("trace", trace.toString());
		DadooLog log = new DadooLog(serviceName, Type.EXCEPTION, System.currentTimeMillis(), content);
		return log;
	}
}
