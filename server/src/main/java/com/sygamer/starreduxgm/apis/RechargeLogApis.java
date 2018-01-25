package com.sygamer.starreduxgm.apis;

import com.sygamer.starreduxgm.business.model.GmResult;
import com.sygamer.starreduxgm.business.service.RechargeLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by LF on 2017/12/11 0011.
 */
@RestController
public class RechargeLogApis {
    @Autowired
    private RechargeLogService rechargeLogService;

    @RequestMapping(value="/api/rechargeLog/rechargeList", method= RequestMethod.GET, produces={ MediaType.APPLICATION_JSON_VALUE })
    public @ResponseBody
    GmResult queryRechargeList(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Map<String, String[]> map = request.getParameterMap();
        return rechargeLogService.findRechargeList(map);
    }
}
