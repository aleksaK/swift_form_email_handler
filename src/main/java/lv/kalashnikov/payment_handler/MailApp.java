package lv.kalashnikov.payment_handler;

import lv.kalashnikov.payment_handler.components.EmailProperties;
import lv.kalashnikov.payment_handler.components.EmailToStringTransformer;
import lv.kalashnikov.payment_handler.components.ReplyMessageHandler;
import lv.kalashnikov.payment_handler.components.SwiftMessageHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.mail.dsl.Mail;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@SpringBootApplication
public class MailApp {

    public static void main(String[] args) {
        new SpringApplicationBuilder(MailApp.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }

    @Bean
    public IntegrationFlow swiftPaymentFlow(EmailProperties emailProperties,
                                            EmailToStringTransformer transformer,
                                            SwiftMessageHandler swiftMessageHandler,
                                            ReplyMessageHandler replyMessageHandler) {
        return IntegrationFlows
                .from(Mail.imapInboundAdapter(emailProperties.getImapUrl())
                                .javaMailProperties(p -> p.put("mail.debug", "true"))
                                .autoCloseFolder(false),
                        e -> e.poller(
                                Pollers.fixedDelay(emailProperties.getPollRate())))
                .transform(transformer)
                .handle(swiftMessageHandler)
                .handle(replyMessageHandler)
                .get();
    }

    @Bean
    public JavaMailSenderImpl getReplySender(@Value("${reply.email.host}") String host,
                                             @Value("${reply.email.port}") int port,
                                             @Value("${reply.email.username}") String username,
                                             @Value("${reply.email.password}") String password) {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost(host);
        sender.setPort(port);
        sender.setUsername(username);
        sender.setPassword(password);

        Properties props = sender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return sender;
    }

}