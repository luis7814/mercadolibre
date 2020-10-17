package co.com.mercadolibre.quasar.controller;

import co.com.mercadolibre.quasar.model.RequestMessage;
import co.com.mercadolibre.quasar.model.ResponseMessage;
import co.com.mercadolibre.quasar.service.SatellitesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class SatellitesController {

    private SatellitesService satellitesService;

    public SatellitesController(SatellitesService satellitesService) {
        this.satellitesService = satellitesService;
    }

    @PostMapping("/topsecret")
    public ResponseEntity<ResponseMessage> topSecret(@RequestBody RequestMessage requestMessage) {

        ResponseMessage responseMessage = satellitesService.topSecret(requestMessage, null);

        return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
    }

    @PostMapping("/topsecret_split/{satellite}")
    public ResponseEntity<ResponseMessage> topSecretSplit(@PathVariable String satellite, @RequestBody RequestMessage requestMessage) {

        ResponseMessage responseMessage = satellitesService.topSecret(requestMessage, satellite);

        return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
    }

    @GetMapping("/topsecret_split")
    public ResponseEntity<ResponseMessage> findInformation() {

        ResponseMessage responseMessage = new ResponseMessage();


        return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
    }

}
