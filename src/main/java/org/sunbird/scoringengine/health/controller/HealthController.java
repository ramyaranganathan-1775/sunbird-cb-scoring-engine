package org.sunbird.scoringengine.health.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.sunbird.scoringengine.health.service.HealthService;
import org.sunbird.scoringengine.models.Response;

import java.util.UUID;


@RestController
public class HealthController {

    @Autowired
    private HealthService healthService;

    @GetMapping("/health")
    public ResponseEntity<Response> healthCheck() throws Exception {
        String requestId = UUID.randomUUID().toString();
        Response response = healthService.checkHealthStatus(requestId);
        return new ResponseEntity<>(response, response.getResponseCode());
    }
}
