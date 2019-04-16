package com.mail;

import com.mail.interfaces.EmailModel;

/**
 * ClassName App
 * 功能: 工程主入口
 * 运行方式与参数: 类路径
 * Author yangweifeng
 * Date 2019-04-16 10:04
 * Version 1.0
 **/
public class App {
    public static void main(String[] args) throws Exception {
        long start_time = System.currentTimeMillis();
        EmailModel mainClass = (EmailModel) Class.forName(args[0]).newInstance();
        String[] codeArgs = new String[args.length - 1];
        for (int i = 0; i < codeArgs.length; i++) {
            codeArgs[i] = args[i + 1];
        }
        mainClass.execute(codeArgs);
        long end_time = System.currentTimeMillis();
        System.out.println("job running cost time is " + (end_time - start_time) / 1000 + " s!");
    }
}
