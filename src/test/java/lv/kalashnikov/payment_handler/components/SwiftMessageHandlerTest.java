package lv.kalashnikov.payment_handler.components;

import com.prowidesoftware.swift.model.mt.mt1xx.MT101;
import com.prowidesoftware.swift.model.mt.mt1xx.MT103;
import org.junit.Test;
import org.springframework.messaging.MessageHeaders;

import java.util.Map;

import static org.junit.Assert.*;

public class SwiftMessageHandlerTest {

    private final SwiftMessageHandler handler = new SwiftMessageHandler();
    private final MessageHeaders headers = new MessageHeaders(
            Map.ofEntries(Map.entry("mail_subject", "A0001")));

    @Test
    public void messageHandlerAccept1() {
        Object payload = new MT103();
        Object result = handler.handle(payload, headers);
        Object expected = "Transaction Nr.A0001 accepted!";
        assertEquals(expected, result);
    }

    @Test
    public void messageHandlerAccept2() {
        Object payload = new MT101();
        Object result = handler.handle(payload, headers);
        Object expected = "Transaction Nr.A0001 accepted!";
        assertEquals(expected, result);
    }

    @Test
    public void messageHandlerFailure() {
        Object payload = "";
        Object result = handler.handle(payload, headers);
        Object expected = "Transaction Nr.A0001 failed!";
        assertEquals(expected, result);
    }

}