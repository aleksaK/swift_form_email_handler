package lv.kalashnikov.payment_handler.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.handler.GenericHandler;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import static java.util.Objects.requireNonNull;

@Component
public class ReplyMessageHandler implements GenericHandler<Object> {

    final private JavaMailSenderImpl sender;

    @Autowired
    public ReplyMessageHandler(JavaMailSenderImpl sender) {
        this.sender = sender;
    }

    @Override
    public Object handle(Object payload, MessageHeaders headers) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(requireNonNull(sender.getUsername()));
        message.setTo(requireNonNull(headers.get("mail_from")).toString());
        message.setSubject(payload.toString());
        message.setText((String) payload);
        sender.send(message);

        return null;

    }

}