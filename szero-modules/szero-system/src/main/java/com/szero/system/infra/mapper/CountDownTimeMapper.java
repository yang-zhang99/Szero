package com.szero.system.infra.mapper;

import com.szero.system.domain.entity.CountDownTime;
import org.springframework.stereotype.Component;

import java.util.List;

public interface CountDownTimeMapper {

    List<CountDownTime> listCountDownTime();
}
