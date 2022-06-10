package com.yang.system.app.service;

import com.yang.system.api.dto.TestSqlUser;


public interface TestSqlService {


    TestSqlUser jdbcTemplate(TestSqlUser user);
}
