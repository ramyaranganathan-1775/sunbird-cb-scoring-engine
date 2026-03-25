package org.sunbird.scoringengine.util;

import org.joda.time.DateTime;
import org.springframework.http.HttpStatus;
import org.sunbird.scoringengine.models.RespParam;
import org.sunbird.scoringengine.models.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ProjectUtil {

    public static Response createDefaultResponse(String api) {
        Response response = new Response();
        response.setId(api);
        response.setVer(Constants.API_VERSION_1);
        response.setParams(new RespParam(UUID.randomUUID().toString()));
        response.getParams().setStatus(Constants.SUCCESS);
        response.setResponseCode(HttpStatus.OK);
        response.setTs(DateTime.now().toString());
        return response;
    }

    public static Map<String, Object> createDefaultMapResponse(String api, String err, String errMsg) {
        Map<String, Object> response = new HashMap<>();
        response.put(Constants.HEALTHY, Constants.TRUE);
        response.put(Constants.NAME, api);
        response.put(Constants.ERR, err != null ? err : "");
        response.put(Constants.ERROR_MESSAGE, errMsg != null ? errMsg : "");
        return response;
    }
}
