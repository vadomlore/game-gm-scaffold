package com.sygamer.starreduxgm.user.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PermissionRepository {
    @Autowired
    @Qualifier("gmJdbcTemplate")
    JdbcTemplate jdbcTemplate;

    @Transactional(readOnly = true)
    public List<String> getUserPermissionsByName(final String userName) {
        final String sql = "select role_name from role where role.id in( select gmadmin.userrole.roleid from userrole where `userrole`.userid in(select user.id from user where name=?))";
        List<String> list = jdbcTemplate.query(sql, new PermissionRowMapper(), userName);
        return list;
    }

    /**
     * 获取用户的Role
     *
     * @param id
     * @return
     */
    public List<String> getRolessByUserId(final int id) {
        final String sql = "select role_name from role where role.id in( select gmadmin.userrole.roleid from userrole where `userrole`.userid = ?)";
        List<String> list = jdbcTemplate.query(sql, new PermissionRowMapper(), id);
        if (list == null) return null;
        if (list.isEmpty()) return null;
        return list;
    }

    /**
     * 增加用户角色
     *
     * @param userId
     * @param roleName
     */
    public void addRoleForUser(final int userId, String roleName) {
        int roleId = jdbcTemplate.queryForObject("select id from role where role_name=?", new Object[]{roleName}, Integer.class);
        try {
            final String sql = "insert into userrole(userid,roleid) values(?,?)";
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    ps.setInt(1, userId);
                    ps.setInt(2, roleId);
                    return ps;
                }
            });
        } catch (DuplicateKeyException e) {
            e.printStackTrace();
            return;
        }
    }

    /**
     * 增加用户角色
     *
     * @param userId
     */
    public void addRoleForUser(final int userId, int roleId) {
        try {
            final String sql = "insert into userrole(userid,roleid) values(?,?)";
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    ps.setInt(1, userId);
                    ps.setInt(2, roleId);
                    return ps;
                }
            });
        } catch (DuplicateKeyException e) {
            e.printStackTrace();
            return;
        }
    }

    /**
     * 获取该用户可访问的所有页面,可以访问的路由以:为前缀标注
     *
     * @param userName
     * @return
     */
    public List<String> getAuthenticatedRouters(final String userName) {
        final String sql = "select distinct role_permission.permission_router_index from role_permission where role_permission.roleid in " +
                "(select distinct role.id from role, user, userrole where `user`.name = '?' and `user`.id=userrole.userid) and role_permission.permission_router_index like ':%'";
        List<String> list = jdbcTemplate.query(sql, new PermissionRowMapper(), userName);
        return list;
    }

    /**
     * 获取可以访问该资源的Role集合
     *
     * @return
     */
    public List<String> getResourceGrantRoles(final String resourceUrl) {
        final String sql = "select role.role_name from role, role_permission where role_permission.permission_router_index=? and role_permission.roleid = role.id";
        List<String> list = jdbcTemplate.query(sql, new PermissionRowMapper(), resourceUrl);
        return (list == null || list.isEmpty()) ? null:list;
    }

    private class PermissionRowMapper implements RowMapper<String> {
        public String mapRow(ResultSet rs, int rowNum) throws SQLException {
            return rs.getString("role_name");
        }
    }

    /**
     * 获取所有角色的可访问资源列表
     *
     * @return
     */
    public Map<String, List<String>> getAllRoleRouters() {
        final String sql = "select role.role_name, role_permission.permission_router_index from role_permission inner join role on role.id = role_permission.roleid where permission_router_index like ':%'; ";

        HashMap<String, List<String>> rolesRouters = new HashMap<>();
        List<RoleRouters> routers = jdbcTemplate.query(sql, new RowMapper<RoleRouters>() {
            @Override
            public RoleRouters mapRow(ResultSet resultSet, int i) throws SQLException {
                return new RoleRouters(resultSet.getString("role_name"), resultSet.getString("permission_router_index"));
            }
        });
        for (RoleRouters router : routers) {
            if(rolesRouters.get(router.getRoleName()) == null){
                rolesRouters.put(router.getRoleName(), new ArrayList<>());
            }
            rolesRouters.get(router.getRoleName()).add(router.getRouter());
        }
        return rolesRouters;
    }

    class RoleRouters {

        public RoleRouters(String roleName, String router) {
            this.roleName = roleName;
            this.router = router;
        }

        private String roleName;

        private String router;

        public String getRoleName() {
            return roleName;
        }

        public void setRoleName(String roleName) {
            this.roleName = roleName;
        }

        public String getRouter() {
            return router;
        }

        public void setRouter(String router) {
            this.router = router;
        }
    }
}
