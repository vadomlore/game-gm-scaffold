package com.sygamer.starreduxgm.business.repository;

import com.sygamer.starreduxgm.business.model.RechargeLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by LF on 2017/12/11 0011.
 */
@Repository
public class RechargeLogRepository {
    @Autowired
    @Qualifier("paymentJdbcTemplate")
    JdbcTemplate jdbcTemplate;

    public List<RechargeLog> findRechargeList(Map<String, String[]> map) {
        int page = Integer.parseInt(map.get("page")[0]);
        int limit = Integer.parseInt(map.get("limit")[0]);
        final String sql = "select * from order_croods_1001_201712 limit " + (page-1)*limit+","+limit;
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            RechargeLog rechargeLog = new RechargeLog();
            rechargeLog.setPid(rs.getString("pid"));
            rechargeLog.setProductid(rs.getInt("productid"));
            rechargeLog.setProductnum(rs.getInt("productnum"));
            rechargeLog.setExtaward(rs.getString("extaward"));
            rechargeLog.setCrttime(rs.getTimestamp("crttime"));
            rechargeLog.setPrice(rs.getDouble("price"));
            rechargeLog.setState(rs.getInt("state"));
            rechargeLog.setAppid(rs.getString("appid"));
            return rechargeLog;
        });
    }

    public Integer getTotal(String tabalName) {
        return jdbcTemplate.queryForObject("select count(*) from "+tabalName, Integer.class);
    }
}
