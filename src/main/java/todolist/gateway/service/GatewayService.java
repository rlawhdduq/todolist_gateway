package todolist.gateway.service;

public interface GatewayService {
    String processCall(String data, String callUrl, String callMethod);
    // String get(String data, String callUrl, String callMethod);
    // String post(String data, String callUrl, String callMethod);
    // String put(String data, String callUrl, String callMethod);
    // String delete(String data, String callUrl, String callMethod);
}
