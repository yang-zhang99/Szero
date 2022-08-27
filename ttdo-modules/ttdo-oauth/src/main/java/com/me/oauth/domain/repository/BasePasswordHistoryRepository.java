package com.me.oauth.domain.repository;




import java.util.List;

/**
 *
 * @author bojiangzhou 2019/08/06
 */
public interface BasePasswordHistoryRepository  {

    List<String> selectUserHistoryPassword(Long userId);

}
