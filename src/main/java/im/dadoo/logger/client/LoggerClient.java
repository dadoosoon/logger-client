/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package im.dadoo.logger.client;

import im.dadoo.log.Log;

/**
 *
 * @author codekitten
 */
public interface LoggerClient {
  
  public void send(final Log log);
  
}
