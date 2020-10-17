package co.com.mercadolibre.quasar.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class RequestMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Satellite> satellites;

}
