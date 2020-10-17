package co.com.mercadolibre.quasar.service;

import co.com.mercadolibre.quasar.model.RequestMessage;
import co.com.mercadolibre.quasar.model.ResponseMessage;
import org.springframework.stereotype.Service;

public class MessagesService {

    public ResponseMessage topSecret(RequestMessage requestMessage) {

        String[] receivedMessages = new String[5];
        String finalMessages = "";

        ResponseMessage responseMessage = new ResponseMessage();

        requestMessage.getSatellites().forEach((value) -> {

            for(int a = 0; a < value.getMessage().length; a++) {
                if (!value.getMessage()[a].isEmpty()){
                    receivedMessages[a] = value.getMessage()[a];
                }
            }
        });

        for(int b = 0; b < receivedMessages.length; b++) {
            finalMessages = finalMessages + " " + (receivedMessages[b] == null ? "" : receivedMessages[b]);
        }

        responseMessage.setMessage(finalMessages);

        return responseMessage;

    }
}
