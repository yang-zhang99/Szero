package com.me.oauth.domain.service;


public interface PasswordErrorTimesService {
    long increaseErrorTimes(Long userId);

    void clearErrorTimes(Long userId);

    long getErrorTimes(Long userId);
}
