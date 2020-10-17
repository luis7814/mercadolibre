package co.com.mercadolibre.quasar.utilities;

import co.com.mercadolibre.quasar.model.RequestMessage;

public class MathematicalsOperation {

    private String[] order2 = null;
    private Utilities utilities;

    //Se expande la ecuación de cada satelite con su distancia
    public String[] quadraticEquation(Double distance, Double satelliteX, Double satelliteY) {

        String ax = "x";
        Double bx = satelliteX * 2;
        Double cx = satelliteX * satelliteX;
        String ay = "y";
        Double by = satelliteY * 2;
        Double cy = satelliteY * satelliteY;

        String[] equation = new String[5];

        utilities = new Utilities();

        equation[Constants.POSITION_ZERO] = ax;
        equation[Constants.POSITION_ONE] = bx.toString();
        equation[Constants.POSITION_TWO] = ay;
        equation[Constants.POSITION_THREE] = by.toString();
        equation[Constants.POSITION_FOUR] = String.valueOf(distance - (cx + cy));

        return equation;
    }

    //Restaremos la ecuaciones ingresadas para obtener el eje radical
    public String[] subtractEquation(String[] equation1, String[] equation2) {

        String[] subtractEquation = new String[Constants.POSITION_THREE];

        String position1 = "x";
        String position2 = String.valueOf((0 - utilities.roundOutNumber(equation1[Constants.POSITION_ONE])) + utilities.roundOutNumber(equation2[Constants.POSITION_ONE]));
        String position3 = String.valueOf((0 - utilities.roundOutNumber(equation1[Constants.POSITION_THREE])) + utilities.roundOutNumber(equation2[Constants.POSITION_THREE]));
        String position4 = String.valueOf(0 - utilities.roundOutNumber(equation1[Constants.POSITION_FOUR]) + utilities.roundOutNumber(equation2[Constants.POSITION_FOUR]));

        Integer positionInt = (int) Double.parseDouble(position4);

        String resultPosition = String.valueOf(utilities.roundOutNumber(position4) / utilities.roundOutNumber(position2) + (Math.abs(positionInt)));
        String resultPositionY = String.valueOf(Math.abs(positionInt));

        subtractEquation[Constants.POSITION_ZERO] = "x";
        subtractEquation[Constants.POSITION_ONE] = resultPosition;
        subtractEquation[Constants.POSITION_TWO] = resultPositionY;

        return subtractEquation;
    }

    //Reeemplazaremos X para obtener el valor de Y, obteniendo un ecuación cuadratica
    public String[] valueY(String[] quadraticEquation, String[] subtractEquation) {

        String[] returnValueY;

        //x2 - 10x + y2 - 8y = -16

        Integer quadratic = (int) Double.parseDouble(quadraticEquation[Constants.POSITION_FOUR]);
        
        Double a = utilities.roundOutNumber(subtractEquation[Constants.POSITION_ONE]) * utilities.roundOutNumber(subtractEquation[Constants.POSITION_ONE]); //Entero
        Double by = utilities.roundOutNumber(subtractEquation[Constants.POSITION_ONE]) * 2; //Valor Y
        Double cy = utilities.roundOutNumber(subtractEquation[Constants.POSITION_TWO]) * utilities.roundOutNumber(subtractEquation[Constants.POSITION_TWO]); //Valor y2
        Double dy = utilities.roundOutNumber(quadraticEquation[Constants.POSITION_ONE]) * utilities.roundOutNumber(subtractEquation[Constants.POSITION_ONE]); //Entero
        Double ey = utilities.roundOutNumber(quadraticEquation[Constants.POSITION_ONE]) * utilities.roundOutNumber(subtractEquation[Constants.POSITION_TWO]); //Valor y
        Double fy = utilities.roundOutNumber(quadraticEquation[Constants.POSITION_THREE]); //Valor y2
        Double gy = utilities.roundOutNumber(String.valueOf(Math.abs(quadratic))); //Entero

        Double valueNumber = a + dy + gy;
        Double valueY = by + ey;
        Double valueY2 = cy + fy;

        //Obtenemos una ecuación de segundo grado para obtener las primeras coordenadas
        returnValueY = secondGradeEquation(valueY2, valueY, valueNumber);

        return returnValueY;


    }

    private String[] secondGradeEquation(Double valueX2, Double valueX, Double valueNumber) {

        String[] results = new String[Constants.POSITION_TWO];

        Double equationX = utilities.roundOutNumber((Math.sqrt(Math.pow(valueX,2))));
        Double equationY = utilities.roundOutNumber(equationX - ((4 * valueX2) * valueNumber));
        Double equationZ = utilities.roundOutNumber(equationY / (2 * valueX2));

        Double resultPositive = 0 - valueX + equationZ;
        Double resultNegative = 0 - valueX - equationZ;

        results[Constants.POSITION_ZERO] = String.valueOf(resultPositive);
        results[Constants.POSITION_ONE] = String.valueOf(resultNegative);

        return results;

    }

    //Reemplazamos Y
    public Double replaceConstant(String secondGrade, Double distance, Double satelliteX, Double satelliteY) {

        String[] resultConstans = new String[Constants.POSITION_TWO];
        Double resultNumber = 0.0;

        Double ay = utilities.roundOutNumber(secondGrade);
        Double by = utilities.roundOutNumber(Math.sqrt(distance + Math.pow(ay + satelliteY, Constants.MATH_POW)));
        Double resultXPositive = (satelliteX + by) / Constants.OPERATION_FINAL;
        Double resultXNegative = (satelliteX - by) / Constants.OPERATION_FINAL;

        resultConstans[Constants.POSITION_ZERO] = String.valueOf(resultXPositive);
        resultConstans[Constants.POSITION_ONE] = String.valueOf(resultXNegative);

        //Validación no muy seguro.
        if(utilities.roundOutNumber(resultConstans[Constants.POSITION_ZERO]) < 0) {
            resultNumber = utilities.roundOutNumber(resultConstans[Constants.POSITION_ZERO]);
        }else {
            resultNumber = utilities.roundOutNumber(resultConstans[Constants.POSITION_ONE]);
        }

        return resultNumber;

    }

    //Reemplazamos X y Y en ecuación SATO, para validar que coordenada es correcta
    public Boolean replaceConstantXY(Double x, Double y, Double satelliteX, Double satelliteY, String distance) {

        Double result = utilities.roundOutNumber((Math.pow((x + satelliteX), Constants.MATH_POW)) + (Math.pow((y + satelliteY), Constants.MATH_POW)));

        if(result == utilities.roundOutNumber(distance)) {
            return true;
        }else {
            return false;
        }

    }

    //Ordenamiento de satelites de la forma KENOBI, SKYWALKER y SATO
    public String[] satellitesOrder(RequestMessage requestMessage) {

        String[] order = new String[Constants.POSITION_THREE];

        requestMessage.getSatellites().forEach((value)-> {

            if(value.getName().equals(Constants.KENOBI_NAME)) {
                order[Constants.POSITION_ZERO] = String.valueOf(value.getDistance());
            }else if(value.getName().equals(Constants.SKYWALKER_NAME)) {
                order[Constants.POSITION_ONE] = String.valueOf(value.getDistance());
            }else {
                order[Constants.POSITION_TWO] = String.valueOf(value.getDistance());
            }
        });

        return order;

    }
}
