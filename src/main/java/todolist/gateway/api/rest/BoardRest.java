package todolist.gateway.api.rest;

import org.springframework.web.bind.annotation.RestController;

import todolist.gateway.dto.BoardDto;
import todolist.gateway.service.rest.GatewayService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/api/v1/board")
public class BoardRest {

    private static final Logger log = LoggerFactory.getLogger(BoardRest.class);
    @Autowired
    private GatewayService gateway;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @RequestMapping(value="/noti", method=RequestMethod.POST)
    public ResponseEntity<String> notiBoard(@RequestBody BoardDto boardDto) {
        Map<String, String> scope = new HashMap<String, String>(){{
            put("A", "all");
            put("F", "friends:");
            put("C", "community:");
        }};
        String topic = scope.get(boardDto.getScope_of_disclosure());
        if( !boardDto.getScope_of_disclosure().equals("A") )
        {
            topic += boardDto.getUser_id();
        }
        messagingTemplate.convertAndSend("board/"+topic, boardDto);
        return ResponseEntity.ok("["+LocalDateTime.now()+"]Notification sent to WebSocket clients");
    }

    // Board
    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<List<Map<String, Object>>> getBoard(@RequestParam Map<String, Object> data) 
    {
        log.info("boardGetCall");
        List<Map<String, Object>> res = gateway.getObject(data, "board", "", List.class);
        return ResponseEntity.ok()
                            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                            .body(res);
    }

    @RequestMapping(path="/detail/{boardId}", method=RequestMethod.GET)
    public ResponseEntity<String> getDetailBoard(@PathVariable Long boardId) 
    {
        log.info("boardDetailGetCall");
        String res = gateway.get(boardId, "board", "/detail", String.class);
        return ResponseEntity.ok()
                            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                            .body(res);
    }

    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<Long> insertBoard(@RequestBody Map<String, Object> data) 
    {
        log.info("boardPostCall");
        Long res = gateway.post(data, "board", "", Long.class);
        return ResponseEntity.ok()
                            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                            .body(res);
    }

    @RequestMapping(method=RequestMethod.PUT)
    public ResponseEntity<Long> updateBoard(@RequestBody Map<String, Object> data) 
    {
        log.info("boardPutCall");
        Long res = gateway.put(data, "board", "", Long.class);
        return ResponseEntity.ok()
                            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                            .body(res);
    }

    @RequestMapping(path="/{boardId}", method=RequestMethod.DELETE)
    public ResponseEntity<String> deleteBoard(@PathVariable Long boardId) 
    {
        log.info("boardDeleteCall");
        String res = gateway.delete(boardId, "board", "", String.class);
        return ResponseEntity.ok()
                            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                            .body(res);
    }

    @RequestMapping(path="/detail/{boardId}", method=RequestMethod.DELETE)
    public ResponseEntity<String> deleteDetailBoard(@PathVariable Long boardId) 
    {
        log.info("boardDetailDeleteCall");
        String res = gateway.delete(boardId, "board", "/detail", String.class);
        return ResponseEntity.ok()
                            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                            .body(res);
    }
    
    // Reply
    @RequestMapping(path="/reply/{boardId}", method=RequestMethod.GET)
    public ResponseEntity<List<Map<String, Object>>> getReply(@PathVariable Long boardId) 
    {
        log.info("replyGetCall");
        List<Map<String, Object>> res = gateway.get(boardId, "board", "/reply", List.class);
        return ResponseEntity.ok()
                            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                            .body(res);
    }
    @RequestMapping(path="/reply", method=RequestMethod.POST)
    public ResponseEntity<String> insertReply(@RequestBody Map<String, Object> data) 
    {
        log.info("replyPostCall");
        String res = gateway.post(data, "board", "/reply", String.class);
        return ResponseEntity.ok()
                            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                            .body(res);
    }
    @RequestMapping(path="/reply", method=RequestMethod.PUT)
    public ResponseEntity<String> updateReply(@RequestBody Map<String, Object> data) 
    {
        log.info("replyPutCall");
        String res = gateway.put(data, "board", "/reply", String.class);
        return ResponseEntity.ok()
                            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                            .body(res);
    }
    @RequestMapping(path="/reply/{boardId}", method=RequestMethod.DELETE)
    public ResponseEntity<String> deleteReply(@PathVariable Long boardId) 
    {
        log.info("replyDeleteCall");
        String res = gateway.delete(boardId, "board", "/reply", String.class);
        return ResponseEntity.ok()
                            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                            .body(res);
    }
    @RequestMapping(path="/reply/detail/{boardId}", method=RequestMethod.DELETE)
    public ResponseEntity<String> detailDeleteReply(@PathVariable Long boardId) 
    {
        log.info("replyDetailDeleteCall");
        String res = gateway.delete(boardId, "board", "/reply/detail", String.class);
        return ResponseEntity.ok()
                            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                            .body(res);
    }

    // Todolist
    @RequestMapping(path="/todo/{boardId}", method=RequestMethod.GET)
    public ResponseEntity<List<Map<String, Object>>> getTodo(@PathVariable Long boardId) 
    {
        log.info("boardGetCall");
        List<Map<String, Object>> res = gateway.get(boardId, "board", "/todo", List.class);
        return ResponseEntity.ok()
                            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                            .body(res);
    }
    @RequestMapping(path="/todo", method=RequestMethod.POST)
    public ResponseEntity<String> insertTodo(@RequestBody Map<String, Object> data) 
    {
        log.info("todoPostCall");
        String res = gateway.post(data, "board", "/todo", String.class);
        return ResponseEntity.ok()
                            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                            .body(res);
    }
    @RequestMapping(path="/todo", method=RequestMethod.PUT)
    public ResponseEntity<String> updateTodo(@RequestBody Map<String, Object> data) 
    {
        log.info("todoPutCall");
        String res = gateway.put(data, "board", "/todo", String.class);
        return ResponseEntity.ok()
                            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                            .body(res);
    }
    @RequestMapping(path="/todo/{boardId}", method=RequestMethod.DELETE)
    public ResponseEntity<String> deleteTodo(@PathVariable Long boardId) 
    {
        log.info("todoDeleteCall");
        String res = gateway.delete(boardId, "board", "/todo", String.class);
        return ResponseEntity.ok()
                            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                            .body(res);
    }
    @RequestMapping(path="/todo/detail/{boardId}", method=RequestMethod.DELETE)
    public ResponseEntity<String> detailDeleteTodo(@PathVariable Long boardId) 
    {
        log.info("todoDetailDeleteCall");
        String res = gateway.delete(boardId, "board", "/todo/detail", String.class);
        return ResponseEntity.ok()
                            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                            .body(res);
    }
}
