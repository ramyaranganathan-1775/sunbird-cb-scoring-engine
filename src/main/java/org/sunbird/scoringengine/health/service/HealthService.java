package org.sunbird.scoringengine.health.service;


import org.sunbird.scoringengine.models.Response;

public interface HealthService {

    Response checkHealthStatus(String requestId) throws Exception;

}
