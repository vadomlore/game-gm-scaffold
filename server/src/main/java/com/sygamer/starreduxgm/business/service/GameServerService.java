package com.sygamer.starreduxgm.business.service;

import com.sygamer.starreduxgm.business.model.GameServerInfo;

import java.util.List;
import java.util.Optional;

public interface GameServerService {

    public GameServerInfo create(final GameServerInfo gameServer);

    public Optional<GameServerInfo> findGameServerById(int id);

    public Optional<GameServerInfo> findGameServerByKey(String name);

    public List<GameServerInfo> findAll();

    public void delete(final String key);
}
