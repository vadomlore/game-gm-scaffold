package com.sygamer.starreduxgm.business.service;

import com.sygamer.starreduxgm.business.model.GameServerInfo;
import com.sygamer.starreduxgm.business.repository.GameServerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DatabaseGameServerServiceImpl implements GameServerService{

    private final GameServerRepository gameServerRepository;

    @Autowired
    public DatabaseGameServerServiceImpl(GameServerRepository gameServerRepository){
        this.gameServerRepository = gameServerRepository;
    }


    @Override
    public Optional<GameServerInfo> findGameServerById(int id) {
        return this.gameServerRepository.findGameServerById(id);
    }

    @Override
    public Optional<GameServerInfo> findGameServerByKey(String key) {
        return this.gameServerRepository.findGameServerByKey(key);
    }

    @Override
    public List<GameServerInfo> findAll() {
        return this.gameServerRepository.findAll();
    }

    @Override
    public GameServerInfo create(GameServerInfo gameServer) {
        return this.gameServerRepository.create(gameServer);
    }

    @Override
    public void delete(String key) {
        this.gameServerRepository.delete(key);
    }
}
