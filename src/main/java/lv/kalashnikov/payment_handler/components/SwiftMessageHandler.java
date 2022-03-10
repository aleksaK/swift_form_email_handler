package lv.kalashnikov.payment_handler.components;

import com.prowidesoftware.swift.model.mt.AbstractMT;
import org.springframework.integration.handler.GenericHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

@Component
public class SwiftMessageHandler implements GenericHandler<Object> {

    @Override
    public Object handle(Object payload, MessageHeaders headers) {
        return payload instanceof AbstractMT ? "Transaction Nr." + headers.get("mail_subject") + " accepted!"
                : "Transaction Nr." + headers.get("mail_subject") + " failed!";
    }

}