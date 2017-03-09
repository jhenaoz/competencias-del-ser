package co.com.psl.evaluacionser.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import java.io.IOException;
import java.util.Collections;
import java.util.Properties;

/**
 * This class configure the java bean for spring in order to send and modified a html template
 */
@Configuration
@PropertySource("classpath:mail/emailconfig.properties")
public class SpringMailConfig implements ApplicationContextAware, EnvironmentAware {

    private String host = "mail.server.host";
    private String port = "mail.server.port";
    private String protocol = "mail.server.protocol";
    private String username = "mail.server.username";
    private String password = "mail.server.password";

    private ApplicationContext applicationContext;
    private Environment environment;

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setEnvironment(final Environment environment) {
        this.environment = environment;
    }

    @Bean
    public JavaMailSender mailSender() throws IOException {

        final JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        /**
         * Basic mail sender configuration, based on emailconfig.properties
         */
        mailSender.setHost(this.environment.getProperty(host));
        mailSender.setPort(Integer.parseInt(this.environment.getProperty(port)));
        mailSender.setProtocol(this.environment.getProperty(protocol));
        mailSender.setUsername(this.environment.getProperty(username));
        mailSender.setPassword(this.environment.getProperty(password));

        /**
         * JavaMail-specific mail sender configuration, based on javamail.properties
         */
        final Properties javaMailProperties = new Properties();
        String javaMailFile = "classpath:mail/javamail.properties";
        javaMailProperties.load(this.applicationContext.getResource(javaMailFile).getInputStream());
        mailSender.setJavaMailProperties(javaMailProperties);

        return mailSender;
    }

    @Bean
    public TemplateEngine emailTemplateEngine() {
        final SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.addTemplateResolver(htmlTemplateResolver());
        return templateEngine;
    }

    private ITemplateResolver htmlTemplateResolver() {
        final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setResolvablePatterns(Collections.singleton("html/*"));
        templateResolver.setPrefix("/mail/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML");
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setCacheable(false);
        return templateResolver;
    }
}
