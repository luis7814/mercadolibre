package co.com.mercadolibre.quasar.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResponseMessage implements Serializable {

    private Position position;
    private String message;

}
