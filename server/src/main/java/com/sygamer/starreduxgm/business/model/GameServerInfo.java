package com.sygamer.starreduxgm.business.model;

public class GameServerInfo {

    private Integer id;

    private String game;

    private String serverId;

    private String serverKey;

    /**
     * game server ip
     */
    private String serverIp;

    private String accountServerUrl;

    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getServerKey() {
        return serverKey;
    }

    public void setServerKey(String serverKey) {
        this.serverKey = serverKey;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public String getAccountServerUrl() {
        return accountServerUrl;
    }

    public void setAccountServerUrl(String accountServerUrl) {
        this.accountServerUrl = accountServerUrl;
    }
}
