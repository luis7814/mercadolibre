package co.com.mercadolibre.quasar.controller;

import co.com.mercadolibre.quasar.model.RequestMessage;
import co.com.mercadolibre.quasar.model.ResponseMessage;
import co.com.mercadolibre.quasar.model.Satellite;
import co.com.mercadolibre.quasar.service.InformationService;
import co.com.mercadolibre.quasar.utilities.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SatellitesController {

    private InformationService informationService;

    public SatellitesController(InformationService informationService) {
        this.informationService = informationService;
    }

    @PostMapping("/topsecret")
    public ResponseEntity<ResponseMessage> topSecret(@RequestBody RequestMessage requestMessage) {

        ResponseMessage responseMessage = informationService.getInformation(requestMessage);

        if(responseMessage.getMessage().equals(Constants.ERROR_404)){
            return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
        }


    }

    @PostMapping("/topsecret_split/{satellite}")
    public ResponseEntity<String> topSecretSplit(@PathVariable String satellite, @RequestBody Satellite requestSatellite) {

        Satellite satelliteResponse = informationService.saveInformation(requestSatellite, satellite);

        if(satelliteResponse == null) {
            return new ResponseEntity<String>("" + satellite, HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<String>("Se ha ingresado información del satellite " + satellite, HttpStatus.OK);
        }


    }

    @GetMapping("/topsecret_split")
    public ResponseEntity<ResponseMessage> findInformation() {

        ResponseMessage responseMessage = informationService.findByInformation();

        if(responseMessage == null) {
            return new ResponseEntity<ResponseMessage>((ResponseMessage) null, HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
        }

    }

}
