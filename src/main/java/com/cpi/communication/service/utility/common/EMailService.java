/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: MailService
 * Author:   admin
 * Date:     2018/11/19 8:58
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.cpi.communication.service.utility.common;

import com.cpi.communication.service.CorrespondentContactQueryService;
import org.apache.commons.lang.CharEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author admin
 * @create 2018/11/19
 * @since 1.0.0
 */

@Service
public class EMailService {

    private final Logger log = LoggerFactory.getLogger(CorrespondentContactQueryService.class);

    @Autowired
    private JavaMailSenderImpl mailService;

    @Async
    public void sendEmail(String to, String sendFrom, String subject, String content) {
        log.debug("Send e-mail to '{}' with subject '{}' and content={}",
            to, subject, content);

        // Prepare message using a Spring helper
        MimeMessage mimeMessage = mailService.createMimeMessage();
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, CharEncoding.UTF_8);
            messageHelper.setTo(to);
            messageHelper.setFrom(sendFrom);
            messageHelper.setSubject(subject);
            messageHelper.setText(content, true);

//            messageHelper.addAttachment();

            mimeMessage.saveChanges();
            mailService.send(messageHelper.getMimeMessage());
            log.debug("Sent e-mail to User '{}'", to);
        } catch (Exception e) {
            log.warn("E-mail could not be sent to user '{}', exception is: {}", to, e.getMessage());
        }
    }

    @Async
    public void sendEmailWithAttachment(String to, String sendFrom, String subject, String content, Map<String, InputStream> attachments) {
        log.debug("Send e-mail to '{}' with subject '{}' and content={}",
            to, subject, content);

        // Prepare message using a Spring helper
        MimeMessage mimeMessage = mailService.createMimeMessage();
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, CharEncoding.UTF_8);
            messageHelper.setTo(to);
            messageHelper.setFrom(sendFrom);
            messageHelper.setSubject(subject);
            messageHelper.setText(content, true);

            for (String key : attachments.keySet() ) {
                InputStream inputStream = attachments.get(key);
                messageHelper.addAttachment(key, new InputStreamResource(inputStream));
            }

            mimeMessage.saveChanges();
            mailService.send(messageHelper.getMimeMessage());
            log.debug("Sent e-mail to User '{}'", to);
        } catch (Exception e) {
            log.warn("E-mail could not be sent to user '{}', exception is: {}", to, e.getMessage());
        }
    }
}
