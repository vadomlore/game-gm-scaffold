package com.sygamer.starreduxgm.user.repository;

import com.sygamer.starreduxgm.endpoint.UserAccount;
import com.sygamer.starreduxgm.endpoint.UserViewBean;
import com.sygamer.starreduxgm.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {
    @Autowired
    @Qualifier("gmJdbcTemplate")
    JdbcTemplate jdbcTemplate;

    @Transactional(readOnly=true)
    public List<User> findAll() {
        return jdbcTemplate.query("select * from user",
                new UserRowMapper());
    }

    public List<UserViewBean> findAllGrantedUsers(String filter) {
        String queryString = "select `user`.id, `user`.`name`, `role`.role_name from `user`, `userrole`, `role` where `user`.id=`userrole`.userid and `userrole`.roleid = role.id";
        if(filter != null && !filter.equals("")){
            queryString += " and `user`.name like '%" + filter + "%'";
        }
        return jdbcTemplate.query(queryString,
                new UserBeanRowMapper());
    }

    @Transactional(readOnly = true)
    public Optional<User> findUserById(int id) {
        try {
            return Optional.of(jdbcTemplate.queryForObject(
                    "select * from user where id=?", new Object[]{id}, new UserRowMapper()));
        }catch (EmptyResultDataAccessException e){
            return Optional.ofNullable(null);
        }
    }


    @Transactional(readOnly = true)
    public Optional<User> findUserByName(String name){
        try{
            return Optional.of(jdbcTemplate.queryForObject(
                    "select * from user where name=?", new Object[]{name}, new UserRowMapper()));
        }catch (EmptyResultDataAccessException e){
            return Optional.ofNullable(null);
        }
    }

    public User create(final User user)
    {
        final String sql = "insert into user(name,password,avatar,introduction,last_login_time) values(?,md5(?),?,?,?)";
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, user.getName());
                ps.setString(2, user.getPassword());
                ps.setString(3, user.getAvatar());
                ps.setString(4, user.getIntroduction());
                ps.setTimestamp(5, user.getLastLoginTime());
                return ps;
            }
        }, holder);
        user.setId(holder.getKey().intValue());
        return user;
    }

    public User updateLoginTime(final User user)
    {
        final String sql = "update user set last_login_time=? where id=?";
        jdbcTemplate.update(sql,
                new PreparedStatementSetter(){
                    @Override
                    public void setValues(java.sql.PreparedStatement preparedStatement) throws SQLException {
                        preparedStatement.setTimestamp(1, user.getLastLoginTime());
                        preparedStatement.setInt(2, user.getId());
                    }
                });
        return user;
    }

    public void changePassword(final String userName, final String password)
    {
        final String sql = "update user set password=md5(?) where name=?";
        jdbcTemplate.update(sql,
                new PreparedStatementSetter(){
                    @Override
                    public void setValues(java.sql.PreparedStatement preparedStatement) throws SQLException {
                        preparedStatement.setString(1, password);
                        preparedStatement.setString(2, userName);
                    }
                });
    }

    public boolean verifyPassword(final String userName, String password)
    {
        final String sql = "select count(*) from user where `user`.`name`=? and user.`password`=MD5(?)";
        Integer cnt = jdbcTemplate.queryForObject(sql, Integer.class, userName, password);
        return cnt > 0;
    }

    @Transactional
    public void deleteUser(int id) {
        final String sql = "delete from  user where `user`.`id`=?";
        jdbcTemplate.update(sql, id);
    }

    private class UserBeanRowMapper implements RowMapper<UserViewBean>{
        @Override
        public UserViewBean mapRow(ResultSet rs, int i) throws SQLException {
            UserViewBean user = new UserViewBean();
            user.setId(rs.getInt("id"));
            user.setName(rs.getString("name"));
            user.setRole(rs.getString("role_name"));
            return user;
        }
    }
    private class UserRowMapper implements RowMapper<User>{
        @Override
        public User mapRow(ResultSet rs, int i) throws SQLException {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));
            user.setAvatar(rs.getString("avatar"));
            user.setIntroduction(rs.getString("introduction"));
            user.setLastLoginTime(rs.getTimestamp("last_login_time"));
            return user;
        }
    }
}
