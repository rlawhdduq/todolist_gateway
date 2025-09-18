package todolist.gateway.service;

import java.util.Map;

public interface GatewayServiceVerTwo {
    String get(Long boardId);
    String post(Map<String, Object> data);
    String put(Map<String, Object> data);
    String delete(Long boardId);
}
