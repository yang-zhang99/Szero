package com.szero.system.app.service.impl;

import com.szero.system.api.controller.dto.TestSqlUser;
import com.szero.system.app.service.TestSqlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;


@Service
public class TestSqlServiceImpl implements TestSqlService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public TestSqlUser jdbcTemplate(TestSqlUser user) {
        TestSqlUser userList =
                jdbcTemplate.execute(
                        "select * from iam_user where login_name = ? and HASH_PASSWORD = ?", (PreparedStatement ps) -> {
                            ps.setObject(1, user.getLoginName());
                            ps.setString(2, user.getPassword());

                            try (ResultSet rs = ps.executeQuery()) {
                                if (rs.next()) {
                                    TestSqlUser u = new TestSqlUser();
                                    u.setLoginName(rs.getString("login_name"));
                                    u.setPassword(rs.getString("HASH_PASSWORD"));
                                    u.setEmail(rs.getString("email"));
                                    return u;
                                }
                                return null;
                            }
                        });
        return userList;
    }
}
