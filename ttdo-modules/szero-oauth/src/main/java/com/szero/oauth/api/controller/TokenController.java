package com.yang.oauth.api.controller;


import com.yang.core.base.BaseController;
import com.yang.oauth.api.dto.LoginUserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/oauth/v1/")
public class TokenController extends BaseController {





    @PostMapping(value = "/token/authorize")
    public ResponseEntity<?> login(@RequestBody LoginUserDTO userDTO) {


        return ResponseEntity.ok(null);

    }

}
