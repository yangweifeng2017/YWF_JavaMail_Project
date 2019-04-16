package com.mail.until;

import com.mail.common.AbstractEmailHelper;
import com.mail.interfaces.EmailModel;
import com.report.contant.Contant;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.*;

/**
 * ClassName SendMailText
 * 功能: 发送文本邮件
 * 运行方式与参数: TODO
 * Author yangweifeng
 * Date 2019-04-15 17:38
 * Version 1.0
 **/
public class SendMailText extends AbstractEmailHelper implements EmailModel {
    /**
     * 获得创建一封邮件的实例对象
     * @param session
     * @return
     * @throws MessagingException
     * @throws AddressException
     */
    public MimeMessage getMimeMessage(Session session) throws Exception{
        //创建一封邮件的实例对象
        MimeMessage msg = new MimeMessage(session);
        //设置发件人地址
        msg.setFrom(new InternetAddress(ConfigLoader.getProperties(Contant.MAIL_SENDER_ADDRESS)));
        /**
         * 设置收件人地址（可以增加多个收件人、抄送、密送），即下面这一行代码书写多行
         * MimeMessage.RecipientType.TO:发送
         * MimeMessage.RecipientType.CC：抄送
         * MimeMessage.RecipientType.BCC：密送
         */
        msg.setRecipients(MimeMessage.RecipientType.TO, this.getMailsRecipientTypeTo());
        msg.setRecipients(MimeMessage.RecipientType.CC, this.getMailsRecipientTypeCC());
        msg.setRecipients(MimeMessage.RecipientType.BCC, this.getMailsRecipientTypeBCC());
        //设置邮件主题
        msg.setSubject(getEmailHead("2019-03-25"),"UTF-8");
        //设置邮件正文
        msg.setContent(getEmailContentText("2019-03-25"), "text/html;charset=UTF-8");
        //设置邮件的发送时间,默认立即发送
        msg.setSentDate(new Date());
        //结束
        return msg;
    }

    @Override
    public void execute(String[] args) throws Exception {
        Session session = this.getSession();
        //3、创建邮件的实例对象
        Message msg = this.getMimeMessage(session);
        //4、根据session对象获取邮件传输对象Transport
        Transport transport = session.getTransport();
        //设置发件人的账户名和密码
        transport.connect(ConfigLoader.getProperties(Contant.MAIL_SENDER_ADDRESS), ConfigLoader.getProperties(Contant.MAIL_SENDER_PASSWORD));
        //发送邮件，并发送到所有收件人地址，message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
        transport.sendMessage(msg,msg.getAllRecipients());
        //5、关闭邮件连接
        transport.close();
    }

    public String getEmailHead(String date)  {
        return date;
    }
    public String getEmailContentText(String date)  {
        return date;
    }

    /**
     * 获取收件人列表
     * @return
     * @throws AddressException
     */
    private InternetAddress [] getMailsRecipientTypeTo() throws AddressException {
        List<InternetAddress> listRecipientTypeTo = new ArrayList<>();
        String [] recipientAddressTo = ConfigLoader.getProperties(Contant.MAIL_RECIPIENT_TO_ADDRESS).split(",");
        for (String mail : recipientAddressTo){
            listRecipientTypeTo.add(new InternetAddress(mail));
        }
        InternetAddress mailsRecipientTypeTo[] = new InternetAddress[listRecipientTypeTo.size()];
        listRecipientTypeTo.toArray(mailsRecipientTypeTo);
        return mailsRecipientTypeTo;
    }

    /**
     * 获取抄送收件人列表
     * @return
     * @throws AddressException
     */
    private InternetAddress [] getMailsRecipientTypeCC() throws AddressException {
        List<InternetAddress> listRecipientTypeCC = new ArrayList<>();
        String [] recipientAddressCC = ConfigLoader.getProperties(Contant.MAIL_RECIPIENT_CC_ADDRESS).split(",");
        for (String mail : recipientAddressCC){
            listRecipientTypeCC.add(new InternetAddress(mail));
        }
        InternetAddress mailsRecipientTypeTo[] = new InternetAddress[listRecipientTypeCC.size()];
        listRecipientTypeCC.toArray(mailsRecipientTypeTo);
        return mailsRecipientTypeTo;
    }

    /**
     * 获取秘密抄送收件人列表
     * @return
     * @throws AddressException
     */
    private InternetAddress [] getMailsRecipientTypeBCC() throws AddressException {
        List<InternetAddress> listRecipientTypeBCC = new ArrayList<>();
        String [] recipientAddressBCC = ConfigLoader.getProperties(Contant.MAIL_RECIPIENT_BCC_ADDRESS).split(",");
        for (String mail : recipientAddressBCC){
            listRecipientTypeBCC.add(new InternetAddress(mail));
        }
        InternetAddress mailsRecipientTypeTo[] = new InternetAddress[listRecipientTypeBCC.size()];
        listRecipientTypeBCC.toArray(mailsRecipientTypeTo);
        return mailsRecipientTypeTo;
    }
    private Session getSession(){
        Properties properties = this.getProperties();
        Session session = null;
        if (Boolean.parseBoolean(properties.getProperty(Contant.MAIL_SMTP_AUTH))){
            // 创建会话对象（授权方式）
            session = Session.getDefaultInstance(properties, new Authenticator() {
                // 认证信息，需要提供"用户账号","授权码"
                public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(ConfigLoader.getProperties(Contant.MAIL_SENDER_ADDRESS), ConfigLoader.getProperties(Contant.MAIL_SENDER_ADDRESS_AUTH_CODE));
                }
            });
        }else {
            //2、创建定义整个应用程序所需的环境信息的 Session 对象
            session = Session.getInstance(properties); //非授权方式
        }
        //设置调试信息在控制台打印出来
        session.setDebug(true);
       return session;
    }
    /**
     * 获取邮件相关配置
     * @return
     */
    private Properties getProperties(){
        //1、连接邮件服务器的参数配置
        Properties props = new Properties();
        //设置用户的认证方式
        props.setProperty("mail.smtp.auth", "true");
        //设置传输协议
        props.setProperty("mail.transport.protocol", "smtp");
        //设置发件人的SMTP服务器地址
        props.setProperty(Contant.MAIL_SMTP_HOST, ConfigLoader.getProperties(Contant.MAIL_SMTP_HOST));
        props.setProperty(Contant.MAIL_SMTP_AUTH, "true"); // 请求认证，参数名称与具体实现有关
        return props;
    }
}
