package com.cpi.communication.config;

import org.apache.commons.lang3.CharEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.StringTemplateResolver;

import java.util.Collections;

@Configuration
public class ThymeleafConfiguration {

    @SuppressWarnings("unused")
    private final Logger log = LoggerFactory.getLogger(ThymeleafConfiguration.class);

    @Bean
    @Description("Thymeleaf template resolver serving HTML 5 emails")
    public ClassLoaderTemplateResolver emailTemplateResolver() {
        ClassLoaderTemplateResolver emailTemplateResolver = new ClassLoaderTemplateResolver();
        emailTemplateResolver.setPrefix("mails/");
        emailTemplateResolver.setSuffix(".html");
        emailTemplateResolver.setTemplateMode("HTML5");
        emailTemplateResolver.setCharacterEncoding(CharEncoding.UTF_8);
        emailTemplateResolver.setOrder(1);
        return emailTemplateResolver;
    }

//    @Bean
//    public ResourceBundleMessageSource emailMessageSource() {
//        final ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
//        messageSource.setBasename("mails/MailMessages");
//        return messageSource;
//    }
//
//    @Bean
//    public TemplateEngine emailTemplateEngine() {
//        final SpringTemplateEngine templateEngine = new SpringTemplateEngine();
//        // Resolver for TEXT emails
//        templateEngine.addTemplateResolver(textTemplateResolver());
//        // Resolver for HTML emails (except the editable one)
//        templateEngine.addTemplateResolver(htmlTemplateResolver());
//        // Resolver for HTML editable emails (which will be treated as a String)
//        templateEngine.addTemplateResolver(stringTemplateResolver());
//        // Message source, internationalization specific to emails
//        templateEngine.setTemplateEngineMessageSource(emailMessageSource());
//        return templateEngine;
//    }
//
//    private ITemplateResolver textTemplateResolver() {
//        final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
//        templateResolver.setOrder(Integer.valueOf(1));
//        templateResolver.setResolvablePatterns(Collections.singleton("text/*"));
//        templateResolver.setPrefix("/mail/");
//        templateResolver.setSuffix(".txt");
//        templateResolver.setTemplateMode(TemplateMode.TEXT);
//        templateResolver.setCharacterEncoding(EMAIL_TEMPLATE_ENCODING);
//        templateResolver.setCacheable(false);
//        return templateResolver;
//    }
//
//    private ITemplateResolver htmlTemplateResolver() {
//        final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
//        templateResolver.setOrder(Integer.valueOf(2));
//        templateResolver.setResolvablePatterns(Collections.singleton("html/*"));
//        templateResolver.setPrefix("/mail/");
//        templateResolver.setSuffix(".html");
//        templateResolver.setTemplateMode(TemplateMode.HTML);
//        templateResolver.setCharacterEncoding(EMAIL_TEMPLATE_ENCODING);
//        templateResolver.setCacheable(false);
//        return templateResolver;
//    }
//
//    private ITemplateResolver stringTemplateResolver() {
//        final StringTemplateResolver templateResolver = new StringTemplateResolver();
//        templateResolver.setOrder(Integer.valueOf(3));
//        // No resolvable pattern, will simply process as a String template everything not previously matched
//        templateResolver.setTemplateMode("HTML5");
//        templateResolver.setCacheable(false);
//        return templateResolver;
//    }
}
