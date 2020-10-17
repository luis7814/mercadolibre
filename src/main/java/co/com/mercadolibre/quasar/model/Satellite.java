package co.com.mercadolibre.quasar.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Satellite implements Serializable {

    private String name;
    private Double distance;
    private String[] message;

}
