package todolist.gateway.api.rest;

import org.springframework.web.bind.annotation.RestController;

import todolist.gateway.dto.BoardDto;
import todolist.gateway.service.rest.GatewayService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/api/v1/board")
public class board {

    private static final Logger log = LoggerFactory.getLogger(board.class);
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
    @RequestMapping(path="/{boardId}", method=RequestMethod.GET)
    public String getBoard(@PathVariable Long boardId) 
    {
        log.info("boardGetCall");
        String res = gateway.get(boardId, "board", "");
        return res;
    }

    @RequestMapping(path="/detail/{boardId}", method=RequestMethod.GET)
    public String getDetailBoard(@PathVariable Long boardId) 
    {
        log.info("boardDetailGetCall");
        String res = gateway.get(boardId, "board", "/detail");
        return res;
    }

    @RequestMapping(method=RequestMethod.POST)
    public String insertBoard(@RequestBody Map<String, Object> data) 
    {
        log.info("boardPostCall");
        String res = gateway.post(data, "board", "");
        return res;
    }

    @RequestMapping(method=RequestMethod.PUT)
    public String updateBoard(@RequestParam Map<String, Object> data) 
    {
        log.info("boardPutCall");
        String res = gateway.put(data, "board", "");
        return res;
    }

    @RequestMapping(path="/{boardId}", method=RequestMethod.DELETE)
    public String deleteBoard(@PathVariable Long boardId) 
    {
        log.info("boardDeleteCall");
        String res = gateway.delete(boardId, "board", "");
        return res;
    }

    @RequestMapping(path="/detail/{boardId}", method=RequestMethod.DELETE)
    public String deleteDetailBoard(@PathVariable Long boardId) 
    {
        log.info("boardDetailDeleteCall");
        String res = gateway.delete(boardId, "board", "/detail");
        return res;
    }
    
    // Reply
    @RequestMapping(path="/reply/{boardId}", method=RequestMethod.GET)
    public String getReply(@PathVariable Long boardId) 
    {
        log.info("replyGetCall");
        String res = gateway.get(boardId, "board", "/reply");
        return res;
    }
    @RequestMapping(path="/reply", method=RequestMethod.POST)
    public String insertReply(@RequestBody Map<String, Object> data) 
    {
        log.info("replyPostCall");
        String res = gateway.post(data, "board", "/reply");
        return res;
    }
    @RequestMapping(path="/reply", method=RequestMethod.PUT)
    public String updateReply(@RequestParam Map<String, Object> data) 
    {
        log.info("replyPutCall");
        String res = gateway.put(data, "board", "/reply");
        return res;
    }
    @RequestMapping(path="/reply/{boardId}", method=RequestMethod.DELETE)
    public String deleteReply(@PathVariable Long boardId) 
    {
        log.info("replyDeleteCall");
        String res = gateway.delete(boardId, "board", "/reply");
        return res;
    }
    @RequestMapping(path="/reply/detail/{boardId}", method=RequestMethod.DELETE)
    public String detailDeleteReply(@PathVariable Long boardId) 
    {
        log.info("replyDetailDeleteCall");
        String res = gateway.delete(boardId, "board", "/reply/detail");
        return res;
    }

    // Todolist
    @RequestMapping(path="/todo/{boardId}", method=RequestMethod.GET)
    public String getTodo(@PathVariable Long boardId) 
    {
        log.info("boardGetCall");
        String res = gateway.get(boardId, "board", "/todo");
        return res;
    }
    @RequestMapping(path="/todo", method=RequestMethod.POST)
    public String insertTodo(@RequestBody Map<String, Object> data) 
    {
        log.info("todoPostCall");
        String res = gateway.post(data, "board", "/todo");
        return res;
    }
    @RequestMapping(path="/todo", method=RequestMethod.PUT)
    public String updateTodo(@RequestParam Map<String, Object> data) 
    {
        log.info("todoPutCall");
        String res = gateway.put(data, "board", "/todo");
        return res;
    }
    @RequestMapping(path="/todo/{boardId}", method=RequestMethod.DELETE)
    public String deleteTodo(@PathVariable Long boardId) 
    {
        log.info("todoDeleteCall");
        String res = gateway.delete(boardId, "board", "/todo");
        return res;
    }
    @RequestMapping(path="/todo/detail/{boardId}", method=RequestMethod.DELETE)
    public String detailDeleteTodo(@PathVariable Long boardId) 
    {
        log.info("todoDetailDeleteCall");
        String res = gateway.delete(boardId, "board", "/todo/detail");
        return res;
    }
}
