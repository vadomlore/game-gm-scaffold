package com.sygamer.starreduxgm.business.repository;

import com.sygamer.starreduxgm.business.model.OperationLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by LF on 2017/12/4 0004.
 */
@Repository
public class OperationLogRepository{
    @Autowired
    @Qualifier("gmJdbcTemplate")
    JdbcTemplate jdbcTemplate;

    public Integer record(OperationLog operationLog) {
        final String sql = "insert into operationlog(operation, content,createAt,user,serverName,extension) values(?,?,?,?,?,?)";
        return jdbcTemplate.update(sql,
                operationLog.getOperation(),
                operationLog.getContent(),
                operationLog.getCreateAt(),
                operationLog.getUser(),
                operationLog.getServerName(),
                operationLog.getExtension());
    }

    public List<OperationLog> findEmailList(Map<String, String[]> map) {
        String optype = map.get("optype")[0];
        int page = Integer.parseInt(map.get("page")[0]);
        int limit = Integer.parseInt(map.get("limit")[0]);
        final String sql = "select * from operationlog where operation = ?  limit " + (page-1)*limit+","+limit;
        return jdbcTemplate.query(sql, new Object[]{optype}, new OperationLogMapper());
    }

    public List<OperationLog> findRecordList(Map<String, String[]> map) {
        String optype = map.get("optype")[0];
        int page = Integer.parseInt(map.get("page")[0]);
        int limit = Integer.parseInt(map.get("limit")[0]);
        final String sql = "select * from operationlog where operation = ?  limit " + (page-1)*limit+","+limit;
        return jdbcTemplate.query(sql, new Object[]{optype}, new OperationLogMapper());
    }


    public Integer getTotal(String operationlog) {
        return jdbcTemplate.queryForObject("select count(*) from "+operationlog, Integer.class);
    }

    public Integer getTotalByType(String operationlog, String type) {
        return jdbcTemplate.queryForObject("select count(*) from "+operationlog + " where operation='" + type +"'", Integer.class);
    }

    private class OperationLogMapper implements RowMapper<OperationLog> {
        @Override
        public OperationLog mapRow(ResultSet rs, int rowNum) throws SQLException {
            OperationLog operationLog = new OperationLog();
            operationLog.setId(rs.getInt("id"));
            operationLog.setOperation(rs.getString("operation"));
            operationLog.setContent(rs.getString("content"));
            operationLog.setCreateAt(rs.getTimestamp("createAt"));
            operationLog.setUser(rs.getString("user"));
            operationLog.setServerName(rs.getString("serverName"));
            operationLog.setExtension(rs.getString("extension"));
            return operationLog;
        }
    }
}
