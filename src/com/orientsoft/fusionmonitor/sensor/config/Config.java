package com.orientsoft.fusionmonitor.sensor.config;


import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class Config {
	
	private String name;
	
	private int age;

	private double step;

	public Config() {
	}

	
	public Config(String name, int age, double step) {
		super();
		this.name = name;
		this.age = age;
		this.step = step;
	}

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}

	public double getStep() {
		return step;
	}

	/**
	 * 读取config.xml文件
	 * 
	 * @param filename
	 * @return class Config
	 * @throws FileNotFoundException
	 */
	public static Config loadConfig(String filename)throws FileNotFoundException {
		XStream xstream = new XStream(new DomDriver());
		xstream.alias("config", Config.class);
		Config config = (Config) xstream.fromXML(new FileInputStream(filename));
		return config;
	}

	/**
	 * 写入XML文件
	 * 
	 * @param filename
	 */
	public void writeXml(String filename) {
		XStream xstream = new XStream(new DomDriver());
		xstream.alias("config", Config.class);
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
	}
	
	public static void main(String[] args){
		
		String name = "xiao";
		int age = 20;
		double steps = 0.010;//单位：km
		Config config = new Config(name, age, steps);
		config.writeXml("conf/config.xml");
		System.out.println("write config.xml over.");
	}
}
