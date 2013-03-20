package com.orientsoft.fusionmonitor.sensor.message;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.orientsoft.fusionmonitor.sensor.config.MachineConfig;


/** 
 * This QueueSend to Send message to server.
 */
public class QueueSend
{
// Defines the JNDI context factory.
  //public final static String JNDI_FACTORY="weblogic.jndi.WLInitialContextFactory";

  // Defines the JMS context factory.
  //public final static String JMS_FACTORY="jms.SensorConnectionFactory";

  // Defines the queue.
  // public final static String QUEUE="jms.SensorQueue";

  private QueueConnectionFactory qconFactory;
  private QueueConnection qcon;
  private QueueSession qsession;
  private QueueSender qsender;
  private Queue queue;
  private TextMessage msg;
  
  public QueueSend(){
	  
  }

  /**
   * Creates all the necessary objects for sending
   * messages to a JMS queue.
   *
   * @param ctx JNDI initial context
   * @param queueName name of queue
   * @exception NamingException if operation cannot be performed
   * @exception JMSException if JMS fails to initialize due to internal error
   */
  
  public void init(Context ctx, String queueName, String jmsFactory)
    throws NamingException, JMSException
  {
    qconFactory = (QueueConnectionFactory) ctx.lookup(jmsFactory);
    qcon = qconFactory.createQueueConnection();
    qsession = qcon.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
    queue = (Queue) ctx.lookup(queueName);
    qsender = qsession.createSender(queue);
    msg = qsession.createTextMessage();
    qcon.start();
  }

  /**
   * Sends a message to a JMS queue.
   *
   * @param message  message to be sent
   * @exception JMSException if JMS fails to send message due to internal error
   */
  public void send(String message) throws JMSException {
    msg.setText(message);
    qsender.send(msg);
  }

  /**
   * Closes JMS objects.
   * @exception JMSException if JMS fails to close objects due to internal error
   */
  public void close() throws JMSException {
    qsender.close();
    qsession.close();
    qcon.close();
  }
  
  /**
   * 	Send single line xml message to server
   */
  public void sendMessagetoServer(String WLS_url, String jndifactory,String User,String Passwd, String jmsFactory, String QUEUE, String xmlline) throws Exception{

		System.out.println("SendMessagetoServer:" + WLS_url + jndifactory + User + Passwd + jmsFactory +  QUEUE);		

		InitialContext ic = getInitialContext(WLS_url, jndifactory, User, Passwd);
		QueueSend qs = new QueueSend();
		qs.init(ic, QUEUE,jmsFactory);
	    
		
		qs.send(xmlline);

		
		// Close the queue.
		qs.close();
			
			
}
  
  /** sendLinefromXMLFile() method.
  *
  * @param args WebLogic Server URL
  * @exception Exception if operation fails
  */

   public static void sendLinefromXMLFile(String configxml) throws Exception
    {
	    String line = "";;

        try
        {
    		MachineConfig cfg = new MachineConfig();
    		cfg = cfg.getConfig(configxml);

        	
        	String WLS_url = cfg.getServer();
        	String fileName = cfg.getFilename();            
				
			int sleeptime = cfg.getsleeptime();
			
			String jndifactory = cfg.getJNDI_Factory();
			String jmsFactory = cfg.getJmsFactory();
			String QUEUE = cfg.getQueue();
			String User  = cfg.getUser();
			String Passwd = cfg.getPasswd();
					
			InitialContext ic = getInitialContext(WLS_url, jndifactory, User, Passwd);
			QueueSend qs = new QueueSend();
			qs.init(ic, QUEUE,jmsFactory);
	
	       	// Create a new random access file.
	       	File raf = new File(fileName);
			//InputStreamReader read = new InputStreamReader (new FileInputStream(raf),"UTF-8");
			BufferedReader reader = new BufferedReader(new FileReader(raf));
	
           	//long fileLength = raf.length()-1;
				
			while((line=reader.readLine())!=null){
	
					System.out.println("line: " + line);
					
					if (line != null && line.trim().length() != 0) {
						qs.send(line);
					}
	
					Thread.sleep(sleeptime);
					
				}
				
			// Close the queue.
			qs.close();
			reader.close();
	
        }
        catch (IOException ex)
        {
            System.out.println(ex.toString());
        }
    }

  private static InitialContext getInitialContext(String url, String jndifactory, String user, String passwd)
    throws NamingException
  {
	  	
		Hashtable<String,String> env = new Hashtable<String,String>();
		env.put(Context.INITIAL_CONTEXT_FACTORY, jndifactory);
		env.put(Context.PROVIDER_URL, url);
		env.put(Context.SECURITY_PRINCIPAL, user);
		env.put(Context.SECURITY_CREDENTIALS, passwd);

		return new InitialContext(env);
    
  }

}

