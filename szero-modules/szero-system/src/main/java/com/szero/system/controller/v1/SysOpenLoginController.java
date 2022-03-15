package com.szero.system.controller.v1;


import com.szero.core.base.BaseController;
import com.szero.system.controller.dto.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统登陆认证
 */
@Controller
@RequestMapping("/v1/open-login")
public class SysOpenLoginController extends BaseController {


    @PostMapping()
    public ResponseEntity<String> login(@RequestBody() User user,
                                        HttpServletResponse response,
                                        HttpServletRequest request) {
        validObject(user);
        return ResponseEntity.ok("我去");
    }

}
