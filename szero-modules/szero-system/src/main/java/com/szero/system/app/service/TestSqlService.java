package com.szero.system.app.service;

import com.szero.system.api.dto.TestSqlUser;


public interface TestSqlService {


    TestSqlUser jdbcTemplate(TestSqlUser user);
}
