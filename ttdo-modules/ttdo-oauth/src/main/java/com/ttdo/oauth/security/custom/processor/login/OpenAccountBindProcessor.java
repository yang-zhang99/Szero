//package com.ttdo.oauth.security.custom.processor.login;
//
//
//import com.ttdo.oauth.domain.entity.User;
//import com.ttdo.oauth.security.service.LoginRecordService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.social.connect.Connection;
//import org.springframework.social.connect.ConnectionData;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
///**
// * 账号绑定
// *
// * @author bojiangzhou 2019/11/05
// */
//@Component
//public class OpenAccountBindProcessor implements LoginSuccessProcessor {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(OpenAccountBindProcessor.class);
//
//    @Autowired
//    private LoginRecordService loginRecordService;
//    @Autowired
//    private SocialUserProviderRepository userProviderRepository;
//
//    @Override
//    public Object process(HttpServletRequest request, HttpServletResponse response) {
//        Connection<?> connection = ProviderBindHelper.getConnection(request);
//        if (connection == null) {
//            return null;
//        }
//        User user = loginRecordService.getLocalLoginUser();
//        ConnectionData data = connection.createData();
//        SocialUserData socialUserData = new SocialUserData(data);
//        LOGGER.info("bind open user, username={}, socialUser={}", user.getLoginName(), socialUserData);
//        userProviderRepository.createUserBind(user.getLoginName(), data.getProviderId(), data.getProviderUserId(), socialUserData);
//        ProviderBindHelper.removeConnection(request);
//        return null;
//    }
//
//    @Override
//    public int getOrder() {
//        return 10;
//    }
//}
