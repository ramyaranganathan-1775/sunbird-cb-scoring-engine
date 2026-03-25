package org.sunbird.scoringengine.health.service;


import org.apache.kafka.clients.admin.AdminClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.sunbird.scoringengine.exception.CustomException;
import org.sunbird.scoringengine.models.Response;
import org.sunbird.scoringengine.util.Constants;
import org.sunbird.scoringengine.util.IndexerService;
import org.sunbird.scoringengine.util.ProjectUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HealthServiceImpl implements HealthService {

    @Autowired
    IndexerService esUtilService;

    private Logger log = LoggerFactory.getLogger(getClass().getName());


    @Override
    public Response checkHealthStatus(String requestId) throws Exception {
        Response response = ProjectUtil.createDefaultResponse(Constants.API_HEALTH_CHECK);
        Map<String, Object> responseObj = new HashMap<>();
        response.getParams().setMsgId(requestId);
        response.getParams().setResMsgId(requestId);
        try {

            List<Map<String, Object>> healthResults = new ArrayList<>();

            elasticsearchHealthStatus(healthResults);

            responseObj.put(Constants.CHECKS, healthResults);
            responseObj.put(Constants.NAME,Constants.ALL_HEALTH_CHECK);
            responseObj.put(Constants.HEALTHY, Constants.TRUE);
            response.put(Constants.RESPONSE, responseObj);

        } catch (Exception e) {
            log.error("Failed to process health check. Exception: ", e);
            response.put(Constants.HEALTHY, false);
            response.getParams().setStatus(Constants.FAILED);
            response.getParams().setErr(e.getMessage());
            response.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    private void elasticsearchHealthStatus(List<Map<String, Object>> response) {

        Map<String, Object> result = ProjectUtil.createDefaultMapResponse(Constants.ELASTIC_SEARCH,null,null);
        boolean isHealthy = true;
        try {
            isHealthy = esUtilService.isElasticsearchHealthy();

            if (!isHealthy) {

                setErrorDetails( result, new CustomException(Constants.ELASTIC_SEARCH +" Down", "Elasticsearch service is unhealthy",
                        HttpStatus.SERVICE_UNAVAILABLE));
            }

        }catch (Exception e) {
            setErrorDetails( result, new CustomException(Constants.ELASTIC_SEARCH +" Down", e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR));
        }
        response.add(result);
    }


    private void setErrorDetails(Map<String, Object> response, CustomException e) {

        response.put(Constants.HEALTHY,Constants.FALSE);
        response.put(Constants.ERR, e.getHttpStatusCode().value());
        response.put(Constants.ERROR_MESSAGE, e.getMessage()!=null ? e.getMessage() : e.getHttpStatusCode().getReasonPhrase());
    }



}

