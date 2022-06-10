package com.yang.system.api.controller.v1;


import com.yang.system.api.dto.TestSqlUser;
import com.yang.system.app.service.TestSqlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/test/sql")
public class TestSqlController {

    @Autowired
    private TestSqlService testsqlService;

    @PostMapping("/mysql-test")
    public ResponseEntity<TestSqlUser> login(@RequestBody() TestSqlUser user,
                                             HttpServletResponse response) {
        return ResponseEntity.ok(testsqlService.jdbcTemplate(user));
    }
}
