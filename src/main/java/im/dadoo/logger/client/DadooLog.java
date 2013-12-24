package im.dadoo.logger.client;

import java.util.Map;

public class DadooLog {
	
	protected String serviceName;
	protected String type;
	protected Long createDatetime;
	protected Map<String, Object> content;
	
	public DadooLog() {}
	
	public DadooLog(String serviceName, String type, 
			Long createDatetime, Map<String, Object> content) {
		this.serviceName = serviceName;
		this.type = type;
		this.createDatetime = createDatetime;
		this.content = content;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("serviceName:").append(this.serviceName).append(",");
		sb.append("type:").append(this.type).append(",");
		sb.append("createDatetime").append(this.createDatetime).append(",");
		sb.append("content:");
		sb.append("{");
		for (String key : this.content.keySet()) {
			sb.append(key).append(":").append(this.content.get(key));
		}
		sb.append("}");
		sb.append("}");
		return sb.toString();
	}
	
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Long getCreateDatetime() {
		return createDatetime;
	}
	public void setCreateDatetime(Long createDatetime) {
		this.createDatetime = createDatetime;
	}
	public Map<String, Object> getContent() {
		return content;
	}
	public void setContent(Map<String, Object> content) {
		this.content = content;
	}
	
}
