package com.ctrip.framework.apollo.portal.spi.github;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.ctrip.framework.apollo.portal.component.config.PortalConfig;
import com.ctrip.framework.apollo.portal.entity.bo.Email;
import com.ctrip.framework.apollo.portal.spi.EmailService;

/**
 * Email发送服务
 * 
 * @author Ternence
 * @date 2017年6月16日
 */
@Service
public class GithubEmailService implements EmailService {

  @Autowired
  private PortalConfig portalConfig;

  private Logger logger = LoggerFactory.getLogger(GithubEmailService.class);


  @Override
  public void send(Email email) {


    try {

      if (!portalConfig.emailEnabled()) {
        logger.info("邮件开关[email.enabled]未打开或未配置，不发送邮件");
        return;
      }

      String host = portalConfig.emailHost();
      String user = portalConfig.emailSender();
      String pwd = portalConfig.emailPassword();

      Properties props = new Properties();
      props.put("mail.smtp.host", host);
      props.put("mail.smtp.auth", "true");
      props.put("mail.transport.protocol", "smtp");


      Session session = Session.getDefaultInstance(props, new Authenticator() {

        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
          return new PasswordAuthentication(user, pwd);
        }

      });
      MimeMessage message = new MimeMessage(session);

      Multipart multipart = new MimeMultipart();
      BodyPart part = new MimeBodyPart();
      part.setContent(email.getBody(), "text/html; charset=utf-8");// set body
      multipart.addBodyPart(part);
      message.setContent(multipart);

      message.setFrom(email.getSenderEmailAddress()); // set sender address
      message.setSubject(email.getSubject());
      message.addRecipients(RecipientType.TO, toAddress(email.getRecipients()));

      Transport.send(message); // send
    } catch (Exception e) {
      logger.error("邮件发送失败", e);
    }

  }

  private Address[] toAddress(List<String> recipients) throws AddressException {
    if (CollectionUtils.isEmpty(recipients)) {
      return new Address[0];
    }
    List<Address> addressList = new ArrayList<>();


    for (String recipient : recipients) {
      Address address = new InternetAddress(recipient);
      addressList.add(address);
    }
    return addressList.toArray(new Address[0]);
  }
}
