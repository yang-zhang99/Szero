//package com.ttdo.oauth.strategy.password;
//
//import com.ttdo.oauth.domain.entity.BaseUser;
//import com.ttdo.oauth.policy.PasswordPolicyMap;
//import com.ttdo.oauth.policy.PasswordPolicyType;
//import com.ttdo.oauth.strategy.PasswordStrategy;
//import com.yang.core.exception.CommonException;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
///**
// * @author wuguokai
// */
//@Component
//public class NotRecentStrategy implements PasswordStrategy {
//    private static final String ERROR_MESSAGE = "hoth.warn.password.policy.notRecent";
//    public static final String TYPE = PasswordPolicyType.NOT_RECENT.getValue();
//
//    private final PasswordEncoder passwordEncoder;
//    private final BasePasswordHistoryRepository basePasswordHistoryRepository;
//
//    public NotRecentStrategy(PasswordEncoder passwordEncoder,
//                             BasePasswordHistoryRepository basePasswordHistoryRepository) {
//        this.passwordEncoder = passwordEncoder;
//        this.basePasswordHistoryRepository = basePasswordHistoryRepository;
//    }
//
//    @Override
//    public Object validate(PasswordPolicyMap policyMap, BaseUser user, String password) {
//        Integer recentPasswordCount = (Integer) policyMap.getPasswordConfig().get(TYPE);
//        if (recentPasswordCount > 0 && user.getId() != null) {
//            List<String> passwordHistoryList = basePasswordHistoryRepository.selectUserHistoryPassword(user.getId());
//            int count = 0;
//            for (String recentPassword : passwordHistoryList) {
//                if (passwordEncoder.matches(password, recentPassword)) {
//                    throw new CommonException(ERROR_MESSAGE, recentPasswordCount);
//                }
//                if (++count >= recentPasswordCount) {
//                    break;
//                }
//            }
//        }
//        return null;
//    }
//
//    @Override
//    public String getType() {
//        return TYPE;
//    }
//
//    @Override
//    public Object parseConfig(Object value) {
//        return null;
//    }
//}
