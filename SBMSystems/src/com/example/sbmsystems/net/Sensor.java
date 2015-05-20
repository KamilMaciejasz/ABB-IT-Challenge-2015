package com.example.sbmsystems.net;

import java.net.InetSocketAddress;

/**
 * 
 * @author Alex
 *         <p>
 *         Basic interface for Sensors
 *         </p>
 */
public class Sensor {
	private String id;
	private String desc;
	private String data;

	Sensor(String id, String desc, String data) {
		this.id = id;
		this.desc = desc;
		this.data = data;
	}

	public String getID() {
		return id;
	}

	public String getDesc() {
		return desc;
	}

	public String getData() {
		return data;
	}
}