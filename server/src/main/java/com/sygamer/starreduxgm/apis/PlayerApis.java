package com.sygamer.starreduxgm.apis;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Response;
import com.sun.org.apache.bcel.internal.classfile.Code;
import com.sygamer.starreduxgm.buffer.Msg;
import com.sygamer.starreduxgm.buffer.MsgDecoder;
import com.sygamer.starreduxgm.buffer.MsgEncoder;
import com.sygamer.starreduxgm.business.model.GameServerInfo;
import com.sygamer.starreduxgm.business.service.GameServerService;
import com.sygamer.starreduxgm.gameaccount.CodeMap;
import com.sygamer.starreduxgm.util.ByteArrayToObjectParser;
import javassist.tools.web.BadHttpRequest;
import org.apache.http.client.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.client.OkHttpClientHttpRequestFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@RestController
public class PlayerApis {

    public final static Map routerFunctionMap = new HashMap();
    static {
        routerFunctionMap.put("/api/player/list*", "querySimplePlayerData");
        routerFunctionMap.put("/api/player/addExp*", "addExp");
        routerFunctionMap.put("/api/guide/skip*", "skipGuide");
        routerFunctionMap.put("/api/player/modifyCurrency*", "modifyPlayerCurrency");
        routerFunctionMap.put("/api/player/fetchArmy*", "fetchArmy");
        routerFunctionMap.put("/api/player/deleteArmy*", "deleteArmy");
        routerFunctionMap.put("/api/player/modifyArmy*", "modifyArmy");
        routerFunctionMap.put("/api/player/addRaceLevel*", "postAddRaceLevel");
        routerFunctionMap.put("/api/player/addResource*", "addPlayersResource");
        routerFunctionMap.put("/api/player/listPlayerResource*", "listPlayerResource");
        routerFunctionMap.put("/api/mail/sendPlayerMail*", "sendPlayerMail");
        routerFunctionMap.put("/api/player/modifyPlayerItem*", "modifyPlayerItem");
        routerFunctionMap.put("/api/player/querySimplePlayer*", "querySimplePlayerData");
        routerFunctionMap.put("/api/player/getPidByUid*", "getPidByUid");
    }

    @Autowired
    private GameServerService gameServerService;

    @Autowired
    private RestTemplate restTemplate;

    private Map<String, String> getHeadersInfo(HttpServletRequest request) {
        Map<String, String> map = new HashMap<String, String>();
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        return map;
    }


    @RequestMapping("/hello/{name}")
    String hello(@PathVariable String name) {
        return "Hello, " + name + "!";
    }

    //目前虽然传递了分页查询的参数,但是没有分页查询功能,只支持查询单个玩家,返回的输入任然是玩家列表信息
    @RequestMapping(value="/api/player/listPlayerResource*", method = RequestMethod.GET, produces={ MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public ResponseEntity<byte[]> listPlayerResource(@RequestBody(required = false) String body, HttpMethod method,
                                                     HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "serverKey", required = true) String serverKey) throws IOException, ServletException, BadHttpRequest {
        String requestUrl = "/api/player/listPlayerResource*";
        return getResponseEntity(body, method, request, response, serverKey, requestUrl);
    }

    @RequestMapping(value="/api/player/modifyPlayerItem*", method = RequestMethod.POST, produces={ MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public ResponseEntity<byte[]> modifyPlayerItem(@RequestBody(required = false) String body, HttpMethod method,
                                                     HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "serverKey", required = true) String serverKey) throws IOException, ServletException, BadHttpRequest {
        String requestUrl = "/api/player/modifyPlayerItem*";
        return getResponseEntity(body, method, request, response, serverKey, requestUrl);
    }

    //目前虽然传递了分页查询的参数,但是没有分页查询功能,只支持查询单个玩家,返回的输入任然是玩家列表信息
    @RequestMapping(value="/api/player/list*", method = RequestMethod.GET, produces={ MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public ResponseEntity<byte[]> getPlayers(@RequestBody(required = false) String body, HttpMethod method,
                                             HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "serverKey", required = true) String serverKey) throws IOException, ServletException, BadHttpRequest {
        String requestUrl = "/api/player/list*";
        return getResponseEntity(body, method, request, response, serverKey, requestUrl);
    }
    //目前虽然传递了分页查询的参数,但是没有分页查询功能,只支持查询单个玩家,返回的输入任然是玩家列表信息
    @RequestMapping(value="/api/player/querySimplePlayer*", method = RequestMethod.GET, produces={ MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public ResponseEntity<byte[]> querySimplePlayer(@RequestBody(required = false) String body, HttpMethod method,
                                             HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "serverKey", required = true) String serverKey) throws IOException, ServletException, BadHttpRequest {
        String requestUrl = "/api/player/querySimplePlayer*";
        return getResponseEntity(body, method, request, response, serverKey, requestUrl);
    }

    @RequestMapping(value="/api/player/addExp*", method = RequestMethod.GET, produces={ MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public ResponseEntity<byte[]> addExp(@RequestBody(required = false) String body, HttpMethod method,
                                             HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "serverKey", required = true) String serverKey) throws IOException, ServletException, BadHttpRequest {
        String requestUrl = "/api/player/addExp*";
        return getResponseEntity(body, method, request, response, serverKey, requestUrl);
    }

    @RequestMapping(value="/api/guide/skip*", method = RequestMethod.GET, produces={ MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public ResponseEntity<byte[]> skipGuide(@RequestBody(required = false) String body, HttpMethod method,
                                         HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "serverKey", required = true) String serverKey) throws IOException, ServletException, BadHttpRequest {
        String requestUrl = "/api/guide/skip*";
        return getResponseEntity(body, method, request, response, serverKey, requestUrl);
    }

    @RequestMapping(value="/api/player/modifyCurrency*", method = RequestMethod.GET, produces={ MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public ResponseEntity<byte[]> modifyPlayerCurrency(@RequestBody(required = false) String body, HttpMethod method,
                                            HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "serverKey", required = true) String serverKey) throws IOException, ServletException, BadHttpRequest {
        String requestUrl = "/api/player/modifyCurrency*";
        return getResponseEntity(body, method, request, response, serverKey, requestUrl);
    }

    @RequestMapping(value="/api/player/fetchArmy*", method = RequestMethod.GET, produces={ MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public ResponseEntity<byte[]> fetchArmy(@RequestBody(required = false) String body, HttpMethod method,
                                                       HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "serverKey", required = true) String serverKey) throws IOException, ServletException, BadHttpRequest {
        String requestUrl = "/api/player/fetchArmy*";
        return getResponseEntity(body, method, request, response, serverKey, requestUrl);
    }

    @RequestMapping(value="/api/player/deleteArmy*", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<byte[]> deleteArmy(@RequestBody(required = false) String body, HttpMethod method,
                                            HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "serverKey", required = true) String serverKey) throws IOException, ServletException, BadHttpRequest {
        String requestUrl = "/api/player/deleteArmy*";
        ResponseEntity<byte[]> responseData =  getResponseEntity(body, method, request, response, serverKey, requestUrl);
        return responseData;
    }

    @RequestMapping(value="/api/player/modifyArmy*", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<byte[]> modifyArmy(@RequestBody(required = false) String body, HttpMethod method,
                                            HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "serverKey", required = true) String serverKey) throws IOException, ServletException, BadHttpRequest {
        String requestUrl = "/api/player/modifyArmy*";
        ResponseEntity<byte[]> responseData =  getResponseEntity(body, method, request, response, serverKey, requestUrl);
        return responseData;
    }

    @RequestMapping(value="/api/player/addRaceLevel*", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<byte[]> addRaceLevel(@RequestBody(required = false) String body, HttpMethod method,
                                             HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "serverKey", required = true) String serverKey) throws IOException, ServletException, BadHttpRequest {
        String requestUrl = "/api/player/addRaceLevel*";
        ResponseEntity<byte[]> responseData =  getResponseEntity(body, method, request, response, serverKey, requestUrl);
        return responseData;
    }

    @RequestMapping(value="/api/player/addResource*", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<byte[]> addResource(@RequestBody(required = false) String body, HttpMethod method,
                                               HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "serverKey", required = true) String serverKey) throws IOException, ServletException, BadHttpRequest {
        String requestUrl = "/api/player/addResource*";
        ResponseEntity<byte[]> responseData =  getResponseEntity(body, method, request, response, serverKey, requestUrl);
        return responseData;
    }

    @RequestMapping(value="/api/mail/sendPlayerMail*", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<byte[]> sendPlayerMail(@RequestBody(required = false) String body, HttpMethod method,
                                              HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "serverKey", required = true) String serverKey) throws IOException, ServletException, BadHttpRequest {
        String requestUrl = "/api/mail/sendPlayerMail*";
        ResponseEntity<byte[]> responseData =  getResponseEntity(body, method, request, response, serverKey, requestUrl);
        return responseData;
    }

    @RequestMapping(value="/api/v1*")
    @ResponseBody
    public ResponseEntity<byte[]> requestProxy(@RequestBody(required = false) String body, HttpMethod method,
                                               HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "serverKey", required = true) String serverKey, @RequestParam(value = "functionName", required = true) String functionName) throws IOException, ServletException, BadHttpRequest {
        ResponseEntity<byte[]> responseData =  getResponseEntityWithFunctionName(body, method, request, response, serverKey, functionName);
        return responseData;
    }

    @RequestMapping(value="/api/v1/*")
    @ResponseBody
    public ResponseEntity<byte[]> requestProxy2(@RequestBody(required = false) String body, HttpMethod method,
                                               HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "serverKey", required = true) String serverKey, @RequestParam(value = "functionName", required = true) String functionName) throws IOException, ServletException, BadHttpRequest {
        ResponseEntity<byte[]> responseData =  getResponseEntityWithFunctionName(body, method, request, response, serverKey, functionName);
        return responseData;
    }

    private ResponseEntity<byte[]> getResponseEntityWithFunctionName(@RequestBody(required = false) String body, HttpMethod method, HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "serverKey", required = true) String serverKey, String functionName) throws BadHttpRequest {
        Optional<GameServerInfo> gameServer = gameServerService.findGameServerByKey(serverKey);
        if(!gameServer.isPresent()){
            throw new BadHttpRequest(new IllegalArgumentException("Invalid server key"));
        }
        String combinationUrl = generateQueryUrl(functionName, gameServer);
        combinationUrl += request.getQueryString();
        HttpServletRequest req = request;
        //获取header信息
        HttpHeaders requestHeaders = new HttpHeaders();
        Enumeration<String> headerNames = req.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = req.getHeader(key);
            requestHeaders.add(key, value);
        }
        String originHeader = request.getHeader("Origin");
        HttpEntity entities = new HttpEntity(body, requestHeaders);
        response.setHeader("Access-Control-Allow-Origin", originHeader);
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
        ResponseEntity<byte[]> responseEntity = restTemplate.exchange(combinationUrl, method, entities, byte[].class);
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.setContentLength(responseEntity.getHeaders().getContentLength());
        return new ResponseEntity<byte[]>(responseEntity.getBody(), requestHeaders, responseEntity.getStatusCode());
    }

    private String generateQueryUrl(String functionName, Optional<GameServerInfo> gameServer) {
        String serverIp = gameServer.get().getServerIp();
        String combinationUrl = "";
        if(!serverIp.startsWith("http://")){
            combinationUrl += "http://";
        }
        combinationUrl += serverIp;
        combinationUrl += "/gm?";
        combinationUrl += "mType=" + functionName + "&";
        return combinationUrl;
    }

    private ResponseEntity<byte[]> getResponseEntity(@RequestBody(required = false) String body, HttpMethod method, HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "serverKey", required = true) String serverKey, String requestUrl) throws BadHttpRequest {
        Optional<GameServerInfo> gameServer = gameServerService.findGameServerByKey(serverKey);
        if(!gameServer.isPresent()){
            throw new BadHttpRequest(new IllegalArgumentException("Invalid server key"));
        }
        String serverIp = gameServer.get().getServerIp();
        String combinationUrl = "";
        if(!serverIp.startsWith("http://")){
            combinationUrl += "http://";
        }
        combinationUrl += serverIp;
        combinationUrl += "/gm?";
        combinationUrl += "mType=" + routerFunctionMap.get(requestUrl) + "&";
        combinationUrl += request.getQueryString();
        HttpServletRequest req = request;
        //获取header信息
        HttpHeaders requestHeaders = new HttpHeaders();
        Enumeration<String> headerNames = req.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = req.getHeader(key);
            requestHeaders.add(key, value);
        }
        String originHeader = request.getHeader("Origin");
        HttpEntity entities = new HttpEntity(body, requestHeaders);
        response.setHeader("Access-Control-Allow-Origin", originHeader);
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
        ResponseEntity<byte[]> responseEntity = restTemplate.exchange(combinationUrl, method, entities, byte[].class);
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.setContentLength(responseEntity.getHeaders().getContentLength());
        return new ResponseEntity<byte[]>(responseEntity.getBody(), requestHeaders, responseEntity.getStatusCode());
    }

    @RequestMapping(value="/api/player/getPidByUid*", method = RequestMethod.GET)
    @ResponseBody
    public Map getPidByUid(@RequestBody(required = false) String body, HttpMethod method,
                                                 HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "serverKey", required = true) String serverKey, @RequestParam String uid, @RequestParam String channelId) throws IOException, ServletException, BadHttpRequest {
        String requestUrl = "/api/player/getPidByUid*";

        Optional<GameServerInfo> gameServer = gameServerService.findGameServerByKey(serverKey);
        if(!gameServer.isPresent()){
            throw new BadHttpRequest(new IllegalArgumentException("Invalid server key"));
        }
        Msg msg = new Msg<>();
        msg.setCmd(CodeMap.Cmd.CMD_ACCOUNT_INFO);
        Map params = new HashMap();
        params.put(CodeMap.Param.ACCOUNT_REL_CODE, Integer.parseInt(channelId));
        params.put(CodeMap.Param.ACCOUNT_REL_UID, uid);
        msg.setParam(params);
        byte[] content = MsgEncoder.encode(msg);
        OkHttpClient client = new OkHttpClient();
        com.squareup.okhttp.MediaType mediaType = com.squareup.okhttp.MediaType.parse("text/plain");
        com.squareup.okhttp.RequestBody bd = com.squareup.okhttp.RequestBody.create(mediaType, new String(content));
        com.squareup.okhttp.Request request1 = new com.squareup.okhttp.Request.Builder()
                .url(gameServer.get().getAccountServerUrl())
                .post(bd)
                .addHeader("content-type", "text/plain")
                .addHeader("cache-control", "no-cache")
                .build();
        Response rs = client.newCall(request1).execute();
        Msg resp = ByteArrayToObjectParser.parse(rs);
        Map pararmResp = (Map)resp.getParam();
        if(((Integer)pararmResp.get(CodeMap.Param.RS_CODE)).equals(CodeMap.RsCode.ERRO_ACCOUNT_OP_NO_EXIST_ACCOUNT ))
        {
            throw new BadHttpRequest(new RuntimeException("Invalid server key"));
        }
        int srvId = (int)pararmResp.get(CodeMap.Param.ACCOUNT_SRV_ID);
        String uidx = (String)pararmResp.get(CodeMap.Param.ACCOUNT_UID);
        //get pid from game server
//        HashMap kvs = new HashMap();
//        kvs.put("srvId", srvId);
//        kvs.put("uidx", uidx);
//        return kvs;

        String queryUrl = generateQueryUrl("getPidByUid", gameServer);
        queryUrl += "uid=" + uidx;
        queryUrl += "&srvId=" + srvId;
        com.squareup.okhttp.Request request2 = new com.squareup.okhttp.Request.Builder()
                .url(queryUrl)
                .addHeader("content-type", "text/plain")
                .addHeader("cache-control", "no-cache")
                .build();
        Response rs2 = client.newCall(request2).execute();
        Map result = ByteArrayToObjectParser.parse(rs2, Map.class);
        return result;
    }
}
