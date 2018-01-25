package com.sygamer.starreduxgm.apis;

import com.sygamer.starreduxgm.business.model.GameServerInfo;
import com.sygamer.starreduxgm.business.service.GameServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class GameServerApis {
    @Autowired
    private GameServerService gameServerService;

    @RequestMapping(value="/api/gameserver/all", method= RequestMethod.GET, produces={ MediaType.APPLICATION_JSON_VALUE })
    public @ResponseBody
    List<GameServerInfo> queryAllGameServer(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        return gameServerService.findAll();
    }

    @RequestMapping(value="/api/gameserver/create", method= RequestMethod.POST, produces={ MediaType.APPLICATION_JSON_VALUE })
    public @ResponseBody
    GameServerInfo createGameServer(@RequestBody GameServerInfo gameServer) throws IOException, ServletException {
        return gameServerService.create(gameServer);
    }
}
