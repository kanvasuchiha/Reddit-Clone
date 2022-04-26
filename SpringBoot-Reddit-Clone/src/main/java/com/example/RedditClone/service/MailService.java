package com.example.RedditClone.service;


import com.example.RedditClone.exceptions.SpringRedditException;
import com.example.RedditClone.model.NotificationEmail;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

//Class created to send the email with the message built in MailContentBuilder.java
@Service
@AllArgsConstructor
@Slf4j
public class MailService {

    private final JavaMailSender mailSender;
    private final MailContentBuilder mailContentBuilder;

    @Async
    public void sendMail(NotificationEmail notificationEmail){
        MimeMessagePreparator messagePreparator = (mimeMessage) -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("springredditclone@gmail.com");
            messageHelper.setTo(notificationEmail.getRecipient());
            messageHelper.setSubject(notificationEmail.getSubject());
            //mailContentBuilder.build(notificationEmail.getBody()) will return the message in HTML format
            messageHelper.setText(mailContentBuilder.build(notificationEmail.getBody()));
        };
        try{
            mailSender.send(messagePreparator);
            log.info("Activation Email sent!!");
        } catch (MailException e){
            throw new SpringRedditException("Exception occured when sending mail to " + notificationEmail.getRecipient());
        }
    }

}
