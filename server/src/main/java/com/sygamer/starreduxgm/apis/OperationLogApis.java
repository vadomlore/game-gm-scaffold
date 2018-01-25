package com.sygamer.starreduxgm.apis;


import com.alibaba.fastjson.JSONArray;
import com.sygamer.starreduxgm.business.model.GmResult;
import com.sygamer.starreduxgm.business.model.OperationLog;
import com.sygamer.starreduxgm.business.service.OperationLogService;
import com.sygamer.starreduxgm.common.Csv2jsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * Created by LF on 2017/12/4 0004.
 */
@RestController
public class OperationLogApis {
    @Autowired
    private OperationLogService operationLogService;
    @Autowired
    private ResourceLoader resourceLoader;

    @PostMapping(value="/api/operationLog/record",produces={ MediaType.APPLICATION_JSON_VALUE })
    public @ResponseBody
    GmResult recordOperation(@RequestBody OperationLog operationLog) throws IOException,ServletException{
        return operationLogService.record(operationLog);
    }

    @RequestMapping(value="/api/operationLog/emailList", method= RequestMethod.GET, produces={ MediaType.APPLICATION_JSON_VALUE })
    public @ResponseBody
    GmResult queryOperationList(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Map<String, String[]> map = request.getParameterMap();
        return operationLogService.findEmailList(map);
    }

    @RequestMapping(value="/api/operationLog/activityRecords", method= RequestMethod.GET, produces={ MediaType.APPLICATION_JSON_VALUE })
    public @ResponseBody
    GmResult queryActivityList(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Map<String, String[]> map = request.getParameterMap();
        return operationLogService.findEmailList(map);
    }

    @RequestMapping(value="/api/email/getProperty", method= RequestMethod.GET, produces={ MediaType.APPLICATION_JSON_VALUE })
    public @ResponseBody
    GmResult getProperty(HttpServletRequest request, HttpServletResponse response){
        Csv2jsonUtil csv2jsonUtil = null;

        int[] arr = {0,2,26};
        try {
            InputStream resource = resourceLoader.getResource("classpath:ItemProperties.csv").getInputStream();
            csv2jsonUtil = new Csv2jsonUtil(resource);
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONArray array = csv2jsonUtil.csv2json(arr);
        GmResult result = new GmResult();
        result.setMsg("获取组件");
        result.setStatus(0);
        result.setData(array);
        return result;
    }

    public static void main(String[] args) {
        String path = OperationLogApis.class.getResource("/").getPath();
        System.out.println(path);
    }

}
