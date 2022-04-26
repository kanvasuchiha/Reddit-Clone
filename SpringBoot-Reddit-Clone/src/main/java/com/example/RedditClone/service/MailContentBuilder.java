package com.example.RedditClone.service;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


//Class created to build the message to be sent in email
@Service
@AllArgsConstructor
public class MailContentBuilder {

    private final TemplateEngine templateEngine;

    String build(String message){
        Context context = new Context();
        context.setVariable("message", message);
        //At runtime, thymeleaf will directly render "message" attribute from the thymeleaf.Context
        //variable inside mailTemplate.html
        //Here mailTemplate is the email file name
        return templateEngine.process("mailTemplate", context);
    }

}
