package com.szero.oauth.security.custom;


import com.szero.oauth.api.dto.LoginUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.PreparedStatement;
import java.sql.ResultSet;


/**
 * @ AuthenticationManagerBuilder
 */
public class CustomAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        // 密码认证
    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
//        org.springframework.security.core.userdetails.User user = ;

        LoginUserDTO userData =
                jdbcTemplate.execute(
                        "select * from iam_user where login_name = ? ", (PreparedStatement ps) -> {
                            ps.setObject(1, username);
                            try (ResultSet rs = ps.executeQuery()) {
                                if (rs.next()) {
                                    LoginUserDTO u = new LoginUserDTO();
                                    u.setUsername(rs.getString("login_name"));
                                    u.setPassword(rs.getString("HASH_PASSWORD"));
                                    u.setType(rs.getString("type"));
                                    return u;
                                }
                                return null;
                            }
                        });
        return null;
//        return new org.springframework.security.core.userdetails.User(userData.getPassword(), userData.getPassword(), userData.getType());
    }
}
