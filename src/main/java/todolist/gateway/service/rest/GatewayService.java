package todolist.gateway.service.rest;

import java.util.Map;

public interface GatewayService {
    String get(Long primaryKey, String api, String extUrl);
    String getObject(Map<String, Object> data, String api, String extUrl);
    String post(Map<String, Object> data, String api, String extUrl);
    String put(Map<String, Object> data, String api, String extUrl);
    String delete(Long primaryKey, String api, String extUrl);
}
