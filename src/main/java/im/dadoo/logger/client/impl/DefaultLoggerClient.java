/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package im.dadoo.logger.client.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import im.dadoo.log.Log;
import im.dadoo.logger.client.LoggerClient;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 *
 * @author codekitten
 */
public class DefaultLoggerClient implements LoggerClient {
  
  private static final Logger logger = LoggerFactory.getLogger(DefaultLoggerClient.class);
  
  private static final String SERVER_URL = "http://localhost:8084/logger";
  
  private final CloseableHttpAsyncClient httpClient;
  
  private final HttpPost post;
  
  private final ObjectMapper mapper;
  
  public DefaultLoggerClient() {
    this.httpClient = HttpAsyncClients.createDefault();
    this.post = new HttpPost(SERVER_URL);
    this.post.setHeader(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
    this.mapper = new ObjectMapper();
  }
  
  @Override
  public void send(final Log log) {
    logger.info(String.format("准备发送日志,内容:%s", log.toString()));
    try {
      this.post.setEntity(new StringEntity(this.mapper.writeValueAsString(log)));
      if (!this.httpClient.isRunning()) {
        this.httpClient.start();
      }
      this.httpClient.execute(this.post, new FutureCallback<HttpResponse>() {
        @Override
        public void completed(HttpResponse response) {
          HttpEntity entity = response.getEntity();
          try {
            String rs = EntityUtils.toString(entity);
            logger.info(String.format("从日志服务器接收到的内容:%s", rs));
            Boolean result = mapper.readValue(rs, Boolean.class);
            if (result) {
              logger.info(String.format("日志已接收,基本信息:%s", log.toPropertyString()));
            } else {
              logger.warn(String.format("日志接收失败,基本信息:%s", log.toPropertyString()));
            }
          } catch (IOException ex) {
            logger.error(String.format("日志系统IO异常,基本信息:%s", log.toPropertyString()), ex);
          } catch (ParseException ex) {
            logger.error(String.format("日志服务器返回信息无法解析,基本信息:%s", log.toPropertyString()), ex);
          } finally {
            EntityUtils.consumeQuietly(entity);
          }
        }

        @Override
        public void failed(Exception ex) {
          logger.error(String.format("日志发送失败,基本信息:%s", log.toPropertyString()), ex);
        }

        @Override
        public void cancelled() {
          logger.warn(String.format("日志发送被取消,基本信息:%s", log.toPropertyString()));
        }
      });
    } catch (JsonProcessingException ex) {
      logger.error(String.format("日志内容无法被转换成json,基本信息:%s", log.toPropertyString()), ex);
    } catch (UnsupportedEncodingException ex) {
      logger.error(String.format("日志编码格式不支持,基本信息:%s", log.toPropertyString()), ex);
    }
  }

  public void close() {
    try {
      this.httpClient.close();
    } catch (IOException ex) {
      logger.error("日志客户端关闭失败",ex);
    }
  }
}
