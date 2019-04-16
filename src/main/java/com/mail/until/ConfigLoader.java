package com.mail.until;

import org.apache.commons.lang3.StringUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * ClassName AbstractCommonHelper
 * 功能: 配置文件加载类
 * Author yangweifeng
 * Date 2018/9/13 16:48
 * Version 1.0
 **/
public final class ConfigLoader {
	private static final String separator = System.getProperty("file.separator");
	private static final String userDir = System.getProperty("user.dir");
	private static Properties props = new Properties();
	private static String configPath = userDir + separator + "conf" + separator + "config.properties";
	static {
		if (StringUtils.isEmpty(configPath)) {
			throw new IllegalArgumentException(
					"Properties is not allow empty,must be as properties file name or classpath name");
		}
		InputStream in = null;
		try {
			in = new FileInputStream(configPath);
		} catch (Exception ex) {
			throw new NullPointerException("Can not found properties configure file: " + configPath);
		}
		if (in != null) {
			try {
				props.load(in);
				in.close();
			} catch (IOException ex) {
				throw new NullPointerException("Object propsLoaderClassPathUtil is null");
			}
		}
	}

	/**
	 * 根据Properties中的key获取value
	 * 
	 * @param key      键
	 * @return value   值
	 */
	public synchronized static String getProperties(String key) {
		return props.getProperty(key);
	}

	/**
	 * 根据Properties中的key和默认的值获取value,如果根据key获取不到defalutValue,则返回传入的默认值
	 * 
	 * @param key          查询值
	 * @param defalutValue 默认值
	 * @return  属性值
	 */
	public synchronized static String getProperties(String key, String defalutValue) {
		return props.getProperty(key, defalutValue);
	}
}
