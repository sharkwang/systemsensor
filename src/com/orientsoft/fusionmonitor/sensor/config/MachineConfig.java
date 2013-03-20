package com.orientsoft.fusionmonitor.sensor.config;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Scanner;

import com.thoughtworks.xstream.XStream;

public class MachineConfig{

	/*
	 * Sensor target Server info
	 */
	private String Server;			// t3://192.168.0.1:7001
	private String JNDI_Factory;	// weblogic.jndi.WLInitialContextFactory
	private String jmsFactory; 		// Target jms Factory: jms.SensorConnectionFactory
	private String Queue;			// Target Queue JNDI name :jms.SensorQueue
	private String User;
	private String Passwd;
	private int    sleeptime; 		// sleep time as second

	/*
	 *  App Middleware DB and Hardware info
	 */
	private String App; 		//本机运行应用系统
	private String MultiApp; 	//多个应用系统名称，以|隔离
	private String Type; 		//本机系统类型：AS-J2EE服务器 DB-数据库服务器 
								//Tux-Tuxedo Any-其他类型
	private String ActiveDisk; 	//监控的本机磁盘 
	
	/**
	 *  Application File interface
	 */
	private String Filename;

	public MachineConfig(){
		
	}

	
	public int getsleeptime(){
		return sleeptime * 1000;
	}

	public String getJNDI_Factory() {
		return JNDI_Factory;
	}


	public void setJNDI_Factory(String jNDI_Factory) {
		JNDI_Factory = jNDI_Factory;
	}

	public void setUser(String user) {
		User = user;
	}

	public void setPasswd(String passwd) {
		Passwd = passwd;
	}


	public String getFilename() {
		return Filename;
	}


	public void setFilename(String filename) {
		Filename = filename;
	}


	public String getJmsFactory() {
		return jmsFactory;
	}


	public void setJmsFactory(String jmsFactory) {
		this.jmsFactory = jmsFactory;
	}

	public String getServer(){
		return Server;
	}
	
	public String getQueue(){
		return Queue;
	}
	
	public String getUser(){
		return User;
	}

	public String getPasswd(){
		return Passwd;
	}

	
	public String getApp(){
		return App;
	}

	public String getMultiApp(){
		return MultiApp;
	}

	public String getType(){
		return Type;
	}
	public String getActiveDisk(){
		return ActiveDisk;
	}

	public MachineConfig loadConfig(String filename) throws Exception {

    	XStream xstream = new XStream();
    	xstream.alias("config", MachineConfig.class);
    	MachineConfig config = (MachineConfig)xstream.fromXML(new FileInputStream(filename));

		return config;

	}

	public MachineConfig createConfig(String filename){
		 
		 Scanner sc = new Scanner(System.in);
		 
		 System.out.println("Sensor config file start create:");

		 System.out.println("Input the Sensor server address[t3://ip:port]:");
		 this.Server = sc.nextLine();

		 System.out.println("Input the Sensor server JNDI Factory[weblogic.jndi.WLInitialContextFactory]:");
		 this.JNDI_Factory = sc.nextLine();

		 System.out.println("Input the Sensor jms Connection Factory[jms.SensorConnectionFactory]:");
		 this.jmsFactory = sc.nextLine();
		 
		 System.out.println("Input the Sensor Active Queue[jms.demoQueue]:");
		 this.Queue = sc.nextLine();
		 
		 System.out.println("Input the Sensor server user name[weblogic]:");
		 this.setUser(sc.nextLine());
		 
		 System.out.println("Input the Sensor server password[welcome1]:");
		 this.setPasswd(sc.nextLine());
		 
		 System.out.println("Input the Sensor sleeptime[10]:");
		 this.sleeptime = sc.nextInt();
		 
		 System.out.println("Input the main Application name in this server[bankapp]:");
		 this.App = sc.nextLine();
		 
		 System.out.println("Input others Applications name in this server[loanapp:testapp]:");
		 this.MultiApp = sc.nextLine();
		 
		 System.out.println("Input the machine Type[AS|Tux|DB|Any]:");
		 this.Type = sc.nextLine();
		 
		 System.out.println("Input the monitor file system[/opt]:");
		 this.ActiveDisk = sc.nextLine();

		 System.out.println("Input the application monitor file interface[app1_log.xml]:");
		 this.Filename = sc.nextLine();

	     XStream xstream = new XStream();
		 xstream.alias("MachineConfig", MachineConfig.class);
         
         String xml = xstream.toXML(this);  
         try {  
             PrintWriter out = new PrintWriter(new BufferedWriter(  
                     new OutputStreamWriter(new FileOutputStream(filename),  
                             "UTF-8")));  
             out.write(xml);  
             out.close();  
             
             
         } catch (Exception e) {  
             e.printStackTrace();  
         }  
         
         sc.close();
         return this;

	}
	
	public MachineConfig getConfig(String filename){
          
	  MachineConfig config = new MachineConfig();

	  try{	
		File f = new File(filename);    
			
	        if (f.exists()) {
	           System.out.println("Sensor Info: load configuration from:" +  filename );
	           config = loadConfig(filename);
	           
	        } else {
 	           System.out.println("Sensor Warning: config file does NOT exist! start create new config file");
	           System.out.println("Sensor config.xml start:");
	           
	           config = createConfig(filename);

	           } 
	   
           } catch (Exception e) {  
             e.printStackTrace();  
           } 
	   
   	   return config;
  
	}
	
	public static void main(String[] args) throws Exception{
		
		MachineConfig cfg = new MachineConfig();
		cfg.getConfig(args[0]);
		
		
	}

}
