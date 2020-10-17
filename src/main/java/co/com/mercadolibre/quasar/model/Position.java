package co.com.mercadolibre.quasar.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Position implements Serializable {

    private Double x;
    private Double y;
}
