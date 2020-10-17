package co.com.mercadolibre.quasar.service;

import co.com.mercadolibre.quasar.model.RequestMessage;
import co.com.mercadolibre.quasar.model.ResponseMessage;
import co.com.mercadolibre.quasar.model.Satellite;
import co.com.mercadolibre.quasar.model.db.Satellites;
import co.com.mercadolibre.quasar.repository.SatelliteRepository;
import co.com.mercadolibre.quasar.utilities.Constants;
import com.google.gson.Gson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class InformationService {

    private SatellitesService satellitesService;
    private MessagesService messagesService;
    private ResponseMessage responseMessage;
    private RequestMessage requestMessage;

    private List<Satellite> satelliteList;

    @Autowired
    private SatelliteRepository satelliteRepository;

    public InformationService() {

    }

    /*
        Llamamos los metodos para la validación de la información de los satelites y los mensajes
        Primero realizará la ubicación de la nave y después la organización del mensaje.
     */
    public ResponseMessage getInformation(RequestMessage requestMessage) {

        ResponseMessage satelliteInformation;
        ResponseMessage messageInformation;

        try {

            satellitesService = new SatellitesService();
            messagesService = new MessagesService();
            responseMessage = new ResponseMessage();

            satelliteInformation = satellitesService.topSecret(requestMessage, null);
            messageInformation = messagesService.topSecret(requestMessage);

            responseMessage.setMessage(messageInformation.getMessage());
            responseMessage.setPosition(satelliteInformation.getPosition());

        }catch (Exception e) {
            responseMessage.setMessage(Constants.ERROR_404);
        }

        return responseMessage;

    }

    /*
        Empezamos a registrar la informaciuón de los satelites para buscar la ubicación de la nave,
        se podrá consultar cuando se tenga la información de los tres satelites
     */
    public Satellite saveInformation(Satellite requestSatellite, String satellite) {

        Satellites satellites = new Satellites();
        Gson gson = new Gson();

        try {

            requestSatellite.setName(satellite);

            satellites.setName(satellite);
            satellites.setJson(gson.toJson(requestSatellite));

            satelliteRepository.save(satellites);

        }catch (Exception e) {
            requestSatellite = null;
        }

        return requestSatellite;



    }

    /*
        Consultamos si está la información suficiente para empezar la localización de la nave.
        Información suficiente es tener la información de los tres satelites, kenobi, skywalker y sato.
     */
    public ResponseMessage findByInformation() {

        try {

            responseMessage = new ResponseMessage();
            requestMessage = new RequestMessage();

            Iterable<Satellites> satellites = new ArrayList<>();

            satellites = satelliteRepository.findAll();

            satelliteList = new ArrayList<>();

            satellites.forEach((value) -> {

                Gson gson = new Gson();

                if(value.getName().equals(Constants.KENOBI_NAME)){
                    satelliteList.add(gson.fromJson(value.getJson(), Satellite.class));
                }
                if(value.getName().equals(Constants.SKYWALKER_NAME)){
                    satelliteList.add(gson.fromJson(value.getJson(), Satellite.class));
                }
                if(value.getName().equals(Constants.SATO_NAME)){
                    satelliteList.add(gson.fromJson(value.getJson(), Satellite.class));
                }
            });

            if(satelliteList.size() == Constants.QUANTITY) {
                requestMessage.setSatellites(satelliteList);
                responseMessage = getInformation(requestMessage);
            }else {
                responseMessage.setMessage("Falta información!");
            }

        }catch (Exception e) {
            responseMessage = null;
        }

        return responseMessage;

    }
}
