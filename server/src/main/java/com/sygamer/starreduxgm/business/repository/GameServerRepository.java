package com.sygamer.starreduxgm.business.repository;

import com.sygamer.starreduxgm.business.model.GameServerInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.util.List;
import java.util.Optional;

@Repository
public class GameServerRepository {

    @Autowired
    @Qualifier("gmJdbcTemplate")
    JdbcTemplate jdbcTemplate;

    @Transactional(readOnly=true)
    public List<GameServerInfo> findAll() {
        return jdbcTemplate.query("select * from game_server",
                new GameServerMapper());
    }

    @Transactional(readOnly = true)
    public Optional<GameServerInfo> findGameServerById(int id){
        try{
            return Optional.of(jdbcTemplate.queryForObject(
                    "select * from game_server where id=?", new Object[]{id}, new GameServerMapper()));
        }catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    @Transactional(readOnly = true)
    public Optional<GameServerInfo> findGameServerByKey(String key){
        try{
            return Optional.of(jdbcTemplate.queryForObject(
                    "select * from game_server where serverKey=?", new Object[]{key}, new GameServerMapper()));
        }catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    public GameServerInfo create(final GameServerInfo gameServer)
    {
        final String sql = "insert into game_server(serverId, serverKey, game,serverIp, accountServerUrl, description) values(?,?,?,?,?,?)";
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, gameServer.getServerId());
                ps.setString(2, gameServer.getServerKey());
                ps.setString(3, gameServer.getGame());
                ps.setString(4, gameServer.getServerIp());
                ps.setString(5, gameServer.getAccountServerUrl());
                ps.setString(6, gameServer.getDescription());
                return ps;
            }
        }, holder);
        gameServer.setId(holder.getKey().intValue());
        return gameServer;
    }

    public void delete(final String key)
    {
        final String sql = "delete from game_server where serverKey=?";
        jdbcTemplate.update(sql, new Object[]{key}, new int[]{Types.VARCHAR} );
    }

    private class GameServerMapper implements RowMapper<GameServerInfo> {
        @Override
        public GameServerInfo mapRow(ResultSet rs, int i) throws SQLException {
            GameServerInfo gameServer = new GameServerInfo();
            gameServer.setId(rs.getInt("id"));
            gameServer.setServerId(rs.getString("serverId"));
            gameServer.setGame(rs.getString("game"));
            gameServer.setServerKey(rs.getString("serverKey"));
            gameServer.setServerIp(rs.getString("serverIp"));
            gameServer.setAccountServerUrl(rs.getString("accountServerUrl"));
            gameServer.setDescription(rs.getString("description"));
            return gameServer;
        }
    }
}
