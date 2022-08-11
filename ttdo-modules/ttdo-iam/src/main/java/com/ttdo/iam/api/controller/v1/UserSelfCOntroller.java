package com.ttdo.iam.api.controller.v1;


import com.ttdo.iam.infra.common.utils.UserUtils;
import com.yang.core.oauth.CustomUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("userSelfController.v1")
@RequestMapping("/hzero/v1")
@SuppressWarnings("rawtypes")
public class UserSelfCOntroller {


    @GetMapping(value = "/users/self")
    public ResponseEntity<String> selectSelf() {
        CustomUserDetails self = UserUtils.getUserDetails();

        return ResponseEntity.ok(self.toString());
//        return Results.success(userRepository.selectSelf());
    }

}
