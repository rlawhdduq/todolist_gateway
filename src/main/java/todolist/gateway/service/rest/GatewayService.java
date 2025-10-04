package todolist.gateway.service.rest;

import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;

public interface GatewayService {

    <T> T get(Long primaryKey, String api, String extUrl, Class<T> responseType);
    <T> T getObject(Map<String, Object> data, String api, String extUrl, Class<T> responseType);
    <T> T post(Map<String, Object> data, String api, String extUrl, Class<T> responseType);
    <T> T put(Map<String, Object> data, String api, String extUrl, Class<T> responseType);
    <T> T delete(Long primaryKey, String api, String extUrl, Class<T> responseType);
    <T> T deleteObject(Map<String, Object> data, String api, String extUrl, Class<T> responseType);

    // 복잡한 제너릭 타입용 오버로딩
    <T> T get(Long primaryKey, String api, String extUrl, ParameterizedTypeReference<T> responseType);
    <T> T getObject(Map<String, Object> data, String api, String extUrl, ParameterizedTypeReference<T> responseType);
    <T> T post(Map<String, Object> data, String api, String extUrl, ParameterizedTypeReference<T> responseType);
    <T> T put(Map<String, Object> data, String api, String extUrl, ParameterizedTypeReference<T> responseType);
    <T> T delete(Long primaryKey, String api, String extUrl, ParameterizedTypeReference<T> responseType);
    <T> T deleteObject(Map<String, Object> data, String api, String extUrl, ParameterizedTypeReference<T> responseType);
}
