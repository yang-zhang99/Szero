package com.szero.system.controller.v1;


import com.szero.core.base.BaseController;
import com.szero.system.controller.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Objects;

/**
 * 系统登陆认证
 */
@Controller
@RequestMapping("/v1/open-login")
public class SysOpenLoginController extends BaseController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

//    @Autowired
//    private JedisPool jedisPool;

    @PostMapping()
    public ResponseEntity<String> login(@RequestBody() User user,
                                        HttpServletResponse response,
                                        HttpServletRequest request) {
        // 校验
        validObject(user);

//        try (Jedis jedis = jedisPool.getResource()) {
//            jedis.select(1);
//            System.out.println(jedis.get("test"));
//        } catch (Exception e) {
//
//        }


//
        User userList =
                jdbcTemplate.execute(
                        "select * from iam_user where login_name = ? and HASH_PASSWORD = ?", (PreparedStatement ps) -> {
                            ps.setObject(1, user.getLoginName());
                            ps.setString(2, user.getPassword());

                            try (ResultSet rs = ps.executeQuery()) {
                                if (rs.next()) {
                                    User u = new User();
                                    u.setLoginName(rs.getString("login_name"));
                                    u.setPassword(rs.getString("HASH_PASSWORD"));
                                    return u;
                                }
                                return null;
                            }
                        });
        if (Objects.isNull(userList)) {
            return ResponseEntity.ok("登陆失败");
        } else {
            return ResponseEntity.ok("登陆成功");
        }
    }
}
