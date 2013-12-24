package im.dadoo.logger.client;

import java.io.IOException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.codehaus.jackson.map.ObjectMapper;

public class LoggerClient {

	private static final ObjectMapper mapper = new ObjectMapper();
	
	private CloseableHttpClient hc;
	
	private String baseUrl;
	
	public LoggerClient(String baseUrl) {
		this.hc = HttpClients.createDefault();
		this.baseUrl = baseUrl;
	}
	
	public void send(DadooLog log) {
		CloseableHttpResponse res = null;
		try {
			HttpPost hp = new HttpPost(baseUrl);
			String json = LoggerClient.mapper.writeValueAsString(log);
			StringEntity se = new StringEntity(json);
			se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
			hp.setEntity(se);
			res = this.hc.execute(hp);
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if (res != null) {
				try {
					res.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
