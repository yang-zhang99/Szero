package com.yang.system.api.controller.v1;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author yang99
 */
@Controller
@RequestMapping("/api/login")
public class AuthorizationEndpoint  {


    @PostMapping("/account")
    public ResponseEntity<String> login(HttpServletResponse response,
                                        HttpServletRequest request,
                                        @RequestParam Map<String, String> parameters) {


        return null;

    }


}
