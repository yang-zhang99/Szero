package com.me.oauth.api.controller.v1;

import com.me.oauth.api.controller.dto.Result;
import com.me.oauth.domain.entity.User;
import com.me.oauth.domain.repository.UserRepository;
import com.me.oauth.domain.service.PasswordService;
import com.me.oauth.domain.service.UserCaptchaService;
import com.me.oauth.infra.constant.Constants;
import com.me.oauth.infra.encrypt.EncryptClient;
import com.me.oauth.security.constant.SecurityAttributes;
import com.me.core.base.BaseConstants;
import com.me.core.base.BaseController;
import com.me.core.exception.MessageException;
import com.me.core.message.MessageAccessor;
import com.me.core.user.UserType;
import com.me.core.util.Regexs;
import com.me.core.util.Results;
import com.me.redis.captcha.CaptchaImageHelper;
import com.me.redis.captcha.CaptchaResult;
import org.apache.commons.lang3.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 修改密码
 *
 * @author bojiangzhou 2019/03/04
 */
@Controller("passwordController.v1")
@RequestMapping("/password")
//@ApiIgnore
public class PasswordController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PasswordController.class);

    @Autowired
    private CaptchaImageHelper captchaImageHelper;
    @Autowired
    private UserCaptchaService userCaptchaService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordService passwordService;
    @Autowired
    private EncryptClient encryptClient;

    private static final String ERROR_ACCOUNT = "error.account";
    private static final String ERROR_CAPTCHA = "error.captcha";

    private static final String RESET_ACCOUNT = "RESET_PASSWORD_ACCOUNT";
    private static final String RESET_ACCOUNT_CROWN = "RESET_PASSWORD_CROWN";

    private static final String USER_ORGANIZATION_ID = "organizationId";
    private static final String USER_NAME = "loginName";

    /**
     * 获取图片验证
     */
    @GetMapping("/captcha.jpg")
    public void createCaptcha(HttpServletResponse response) {
        captchaImageHelper.generateAndWriteCaptchaImage(response, Constants.APP_CODE);
    }

    /**
     * 下一步
     */
    @GetMapping("/next")
    @ResponseBody
    public ResponseEntity<Result> validateAccountAndNext(
            HttpServletRequest request,
            HttpSession session,
            @RequestParam(defaultValue = BaseConstants.DEFAULT_CROWN_CODE) String internationalTelCode,
            String account,
            String captcha) {
        session.removeAttribute(RESET_ACCOUNT);
        session.removeAttribute(RESET_ACCOUNT_CROWN);

        if (StringUtils.isBlank(account)) {
            return Results.success(new Result(false, ERROR_ACCOUNT, MessageAccessor.getMessage("hoth.warn.phoneOrEmailNotnull").desc()));
        }
        if (StringUtils.isBlank(captcha)) {
            return Results.success(new Result(false, ERROR_CAPTCHA, MessageAccessor.getMessage("hoth.warn.captchaNotnull").desc()));
        }
        CaptchaResult captchaResult = captchaImageHelper.checkCaptcha(request, captcha, Constants.APP_CODE);
        if (!captchaResult.isSuccess()) {
            return Results.success(new Result(false, ERROR_CAPTCHA, captchaResult.getMessage()));
        }

        User user = userRepository.selectUserByPhoneOrEmail(account, UserType.ofDefault());
        if (user == null) {
            return Results.success(new Result(false, ERROR_ACCOUNT, MessageAccessor.getMessage("hoth.warn.phoneOrEmailNotFound").desc()));
        }
        if (user.getLdap()) {
            return Results.success(new Result(false, ERROR_ACCOUNT, MessageAccessor.getMessage("hoth.warn.ldapCannotChangePassword").desc()));
        }
        // 用户名和组织Id 用于获取密码校验规则
        Map<String, Object> map = new HashMap<>();
        map.put(USER_ORGANIZATION_ID, user.getOrganizationId());
        map.put(USER_NAME, account);
        session.setAttribute(RESET_ACCOUNT, account);
        session.setAttribute(RESET_ACCOUNT_CROWN, internationalTelCode);
        Result result = new Result(true);
        result.setData(map);
        return Results.success(result);
    }

    /**
     * 给用户手机/邮箱发送验证码
     */
    @GetMapping("/send-captcha")
    @ResponseBody
    public ResponseEntity<Result> sendUserAccountCaptcha(
            HttpSession session,
            @RequestParam(required = false) String userType,
            @RequestParam(required = false) String businessScope) throws IOException {
        String account = (String) session.getAttribute(RESET_ACCOUNT);
        String internationalTelCode = (String) session.getAttribute(RESET_ACCOUNT_CROWN);
        if (StringUtils.isBlank(account)) {
            return Results.success(new Result(false, MessageAccessor.getMessage("hoth.warn.phoneOrEmailNotnull").desc()));
        }

        CaptchaResult captchaResult = null;
        if (Regexs.isEmail(account)) {
            captchaResult = userCaptchaService.sendEmailCaptcha(account, UserType.ofDefault(userType), businessScope);
        } else if (Regexs.isMobile(account)) {
            captchaResult = userCaptchaService.sendPhoneCaptcha(internationalTelCode, account, UserType.ofDefault(userType), businessScope);
        } else {
            return Results.success(new Result(false, MessageAccessor.getMessage("hoth.warn.phoneOrEmailNotInvalid").desc()));
        }

        Result result = new Result(captchaResult.isSuccess(), captchaResult.getCode(), captchaResult.getMessage());
        result.setData(captchaResult.getCaptchaKey());

        return Results.success(result);
    }

    /**
     * 修改密码
     */
    @PostMapping("/modify")
    @ResponseBody
    public ResponseEntity<Result> resetPassword(
            HttpSession session, String password, String captchaKey, String captcha,
            @RequestParam(required = false) String userType,
            @RequestParam(required = false) String businessScope) {
        String account = (String) session.getAttribute(RESET_ACCOUNT);
        if (StringUtils.isBlank(account)) {
            return Results.success(new Result(false, MessageAccessor.getMessage("hoth.warn.phoneOrEmailNotnull").desc()));
        }

        password = encryptClient.decrypt(password);
        try {
            passwordService.updatePasswordByAccount(account, UserType.ofDefault(userType), businessScope, password, captchaKey, captcha);
        } catch (Exception e) {
            LOGGER.warn("update password error. e={}", e.getMessage());
            if (e instanceof MessageException) {
                return Results.success(new Result(false, ((MessageException) e).getCode(), e.getMessage()));
            } else {
                return Results.success(new Result(false, e.getMessage(), MessageAccessor.getMessage(e.getMessage()).desc()));
            }
        }

        return Results.success(new Result(true));
    }

    /**
     * 强制修改密码
     */
    @PostMapping("/force-modify")
    @ResponseBody
    public ResponseEntity<Result> forceModifyPassword(HttpSession session, String password,
                                                      @RequestParam(required = false) String userType) {
        Long userId = (Long) session.getAttribute(SecurityAttributes.SECURITY_LOGIN_USER_ID);

        if (null == userId) {
            // session 内容为空  无法强制修改密码参数
            return Results.success(new Result(false, MessageAccessor.getMessage("hoth.warn.sessionUserNull").desc()));
        }
        password = encryptClient.decrypt(password);
        try {
            passwordService.updatePasswordByUser(userId, UserType.ofDefault(userType), password);
        } catch (Exception e) {
            LOGGER.error("modify password error.", e);
            return Results.success(new Result(false, e.getMessage(), MessageAccessor.getMessage(e.getMessage()).desc()));
        }
        return Results.success(new Result(true));
    }

    /**
     * 检测密码强度
     */
    @PostMapping("/check-strength")
    public ResponseEntity<?> checkPasswordStrength(@RequestParam("password") String password) {
        password = encryptClient.decrypt(password);
        Map<String, String> strength = new HashMap<>(1);
//        strength.put("level", CheckStrength.getPasswordLevel(password).name());
        return Results.success(strength);
    }

}
