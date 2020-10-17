package co.com.mercadolibre.quasar.utilities;

import java.text.DecimalFormat;

public class Utilities {

    //Redondear numero decimales
    public Double roundOutNumber(String number) {

        DecimalFormat decimalFormat = new DecimalFormat("#.0");
        String finalNumber = decimalFormat.format(Double.parseDouble(number));
        finalNumber = finalNumber.replace(",",".");

        return Double.parseDouble(finalNumber);

    }

    //Redondear numero decimales
    public Double roundOutNumber(Double number) {

        DecimalFormat decimalFormat = new DecimalFormat("#.0");
        String finalNumber = decimalFormat.format(number);


        return Double.parseDouble(finalNumber.replace(",","."));

    }
}
