package co.com.mercadolibre.quasar.service;

import co.com.mercadolibre.quasar.model.Position;
import co.com.mercadolibre.quasar.model.RequestMessage;
import co.com.mercadolibre.quasar.model.ResponseMessage;
import co.com.mercadolibre.quasar.utilities.Constants;
import co.com.mercadolibre.quasar.utilities.MathematicalsOperation;
import co.com.mercadolibre.quasar.utilities.Utilities;
import org.springframework.stereotype.Service;

@Service
public class SatellitesService {
    
    private Utilities utilities;
    
    public ResponseMessage topSecret(RequestMessage requestMessage, String satellite) {
        
        utilities = new Utilities();
        
        Boolean ubicationSuccesfull = false;
        ResponseMessage responseMessage = new ResponseMessage();

        MathematicalsOperation mathematicalsOperation = new MathematicalsOperation();
        String[] distancesOrder = mathematicalsOperation.satellitesOrder(requestMessage);

        //Tendremos un sistema de ecuaciones, en donde expandiremos la ecuación para KENOBI y SKYWALKER
        String[] operationA = mathematicalsOperation.quadraticEquation(utilities.roundOutNumber(distancesOrder[Constants.POSITION_ZERO]), Constants.KENOBI[Constants.POSITION_ZERO], Constants.KENOBI[Constants.POSITION_ONE]);
        String[] operationB = mathematicalsOperation.quadraticEquation(utilities.roundOutNumber(distancesOrder[Constants.POSITION_ONE]), Constants.SKYWALKER[Constants.POSITION_ZERO], Constants.SKYWALKER[Constants.POSITION_ONE]);

        //Restaremos las ecuaciones anteriores para que nos de una línea recta o eje radical
        String[] rootAxis = mathematicalsOperation.subtractEquation(operationA, operationB);

        //Reemplazaremos X en la primera ecuacion para obtener el valor de Y, obtendremos una ecuación cuadratica y tendremos las posibles soluciones de Y
        String[] valueYA = mathematicalsOperation.valueY(operationA, rootAxis);

        //Si Y = valueYa[Constants.POSITION_ZERO] sobre la ecuación de KENOBI Y SKYWALKER
        Double firtsPositionOne = mathematicalsOperation.replaceConstant(valueYA[Constants.POSITION_ZERO], utilities.roundOutNumber(distancesOrder[Constants.POSITION_ZERO]), Constants.KENOBI[Constants.POSITION_ZERO],Constants.KENOBI[Constants.POSITION_ONE]);
        Double secondPositionOne = mathematicalsOperation.replaceConstant(valueYA[Constants.POSITION_ZERO], utilities.roundOutNumber(distancesOrder[Constants.POSITION_ONE]), Constants.SKYWALKER[Constants.POSITION_ZERO],Constants.SKYWALKER[Constants.POSITION_ONE]);

        //Si Y = valueYa[Constants.POSITION_ONE] sobre la ecuación de KENOBI Y SKYWALKER
        Double firtsPositionTwo = mathematicalsOperation.replaceConstant(valueYA[Constants.POSITION_ONE], utilities.roundOutNumber(distancesOrder[Constants.POSITION_ZERO]), Constants.KENOBI[Constants.POSITION_ZERO],Constants.KENOBI[Constants.POSITION_ONE]);
        Double secondPositionTwo = mathematicalsOperation.replaceConstant(valueYA[Constants.POSITION_ONE], utilities.roundOutNumber(distancesOrder[Constants.POSITION_ONE]), Constants.SKYWALKER[Constants.POSITION_ZERO],Constants.SKYWALKER[Constants.POSITION_ONE]);

        //Tomar la posible solución
        ubicationSuccesfull = mathematicalsOperation.replaceConstantXY(firtsPositionOne, secondPositionOne, Constants.SATO[Constants.POSITION_ZERO], Constants.SATO[Constants.POSITION_ONE], distancesOrder[Constants.POSITION_TWO]);

        Position position = new Position();
        position.setX(firtsPositionOne);
        position.setY(secondPositionOne);

        if(!ubicationSuccesfull) {
            ubicationSuccesfull = mathematicalsOperation.replaceConstantXY(firtsPositionTwo, secondPositionTwo, Constants.SATO[Constants.POSITION_ZERO], Constants.SATO[Constants.POSITION_ONE], distancesOrder[Constants.POSITION_TWO]);

            position.setX(firtsPositionTwo);
            position.setY(secondPositionTwo);
        }

        responseMessage.setPosition(position);
        responseMessage.setMessage("");

        return responseMessage;


    }
}
