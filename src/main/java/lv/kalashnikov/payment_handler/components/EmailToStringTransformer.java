package lv.kalashnikov.payment_handler.components;

import com.prowidesoftware.swift.model.mt.AbstractMT;
import org.springframework.integration.mail.transformer.AbstractMailMessageTransformer;
import org.springframework.integration.support.AbstractIntegrationMessageBuilder;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;
import javax.mail.Message;
import javax.mail.internet.MimeMultipart;

@Component
public class EmailToStringTransformer extends AbstractMailMessageTransformer<Object> {

    @Override
    protected AbstractIntegrationMessageBuilder<Object> doTransform(Message mailMessage) throws Exception {
        String body = ((MimeMultipart) mailMessage.getContent()).getBodyPart(0).getContent().toString();
        AbstractMT fin = AbstractMT.parse(body);
        return fin != null ? MessageBuilder.withPayload(fin) : MessageBuilder.withPayload("");
    }

}