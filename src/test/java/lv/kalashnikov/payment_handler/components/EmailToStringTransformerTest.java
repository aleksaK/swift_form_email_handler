package lv.kalashnikov.payment_handler.components;

import com.prowidesoftware.swift.model.mt.AbstractMT;
import org.junit.Test;
import org.springframework.integration.support.AbstractIntegrationMessageBuilder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

import static org.junit.Assert.*;

public class EmailToStringTransformerTest {

    private final EmailToStringTransformer transformer = new EmailToStringTransformer();

    @Test
    public void EmailToStringTransformerSuccess() throws Exception {

        String body = "{1:F01BANKDEFMAXXX2039063581}{2:O1031609050901BANKDEFXAXXX89549829458949811609N}{4:\n" +
                ":20:007505327853\n" +
                ":23B:CRED\n" +
                ":32A:050902JPY3520000,\n" +
                ":33B:JPY3520000,\n" +
                ":50K:EUROXXXEI\n" +
                ":52A:FEBXXXM1\n" +
                ":53A:MHCXXXJT\n" +
                ":54A:FOOBICXX\n" +
                ":59:/13212312\n" +
                "RECEIVER NAME S.A\n" +
                ":70:FUTURES\n" +
                ":71A:SHA\n" +
                ":71F:EUR12,00\n" +
                ":71F:EUR2,34\n" +
                "-}";

        AbstractIntegrationMessageBuilder<Object> result = transformer.doTransform(getMessage(body));
        assertTrue(result.getPayload() instanceof AbstractMT);

    }

    @Test
    public void EmailToStringTransformerFailure() throws Exception {

        String body = ":20:007505327853\n" +
                ":23B:CRED\n" +
                ":32A:050902JPY3520000,\n" +
                ":33B:JPY3520000,\n" +
                ":50K:EUROXXXEI\n" +
                ":52A:FEBXXXM1\n" +
                ":53A:MHCXXXJT\n" +
                ":54A:FOOBICXX\n" +
                ":59:/13212312\n" +
                "RECEIVER NAME S.A\n" +
                ":70:FUTURES\n" +
                ":71A:SHA\n" +
                ":71F:EUR12,00\n" +
                ":71F:EUR2,34\n" +
                "-}";

        AbstractIntegrationMessageBuilder<Object> result = transformer.doTransform(getMessage(body));
        assertFalse(result.getPayload() instanceof AbstractMT);

    }

    private static Message getMessage(String body) throws Exception {
        Session session = Session.getInstance(new Properties());
        Message message = new MimeMessage(session);
        MimeBodyPart part = new MimeBodyPart();
        part.setText(body);
        message.setContent(new MimeMultipart(part));
        return message;
    }

}