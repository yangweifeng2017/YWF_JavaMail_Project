package com.mail.logs;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * ClassName Log
 * 功能: TODO
 * 运行方式与参数: TODO
 * Author yangweifeng
 * Date 2019-04-16 10:22
 * Version 1.0
 **/
public class Log {
    private static final String separator = System.getProperty("file.separator");
    static {
        String javaJarClassPath = System.getProperty("java.class.path");
        String paths[] = javaJarClassPath.split("\\/");
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < paths.length - 1; i++) {
            stringBuilder.append(paths[i]).append(separator);
        }
        PropertyConfigurator.configure(stringBuilder + "conf" + System.getProperty("file.separator") + "log4j.properties");
    }
    public static final Logger logger = Logger.getLogger(Log.class);
}
