package com.me.oauth.infra.repository.impl;


import com.me.oauth.domain.entity.BasePasswordHistory;
import com.me.oauth.domain.repository.BasePasswordHistoryRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author bojiangzhou 2019/08/06
 */
@Component
public class BasePasswordHistoryRepositoryImpl  implements BasePasswordHistoryRepository {

    @Override
    public List<String> selectUserHistoryPassword(Long userId) {
        BasePasswordHistory params = new BasePasswordHistory();
        params.setUserId(userId);
        List<BasePasswordHistory> histories = null;
//                selectOptional(params, new Criteria()
//                .where(BasePasswordHistory.FIELD_USER_ID)
//                .sort(BasePasswordHistory.FIELD_ID, SortType.DESC)
//        );
        return histories.stream().map(BasePasswordHistory::getPassword).collect(Collectors.toList());
    }
}
