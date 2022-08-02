package com.ttdo.oauth.api.controller.v1;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RefreshScope
@Controller
public class OauthController {

    private static final String LOGIN_DEFAULT = "login";


    /**
     * 默认登录页面
     */
    @GetMapping(value = "/login")
    @ResponseBody
    public ResponseEntity<String> login(HttpServletRequest request, Model model, HttpSession session,
                                        @RequestParam(required = false) String device,
                                        @RequestParam(required = false) String type) throws JsonProcessingException {

        return ResponseEntity.ok("123");
    }
}