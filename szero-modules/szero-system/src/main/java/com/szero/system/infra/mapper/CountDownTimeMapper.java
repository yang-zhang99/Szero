package com.szero.system.infra.mapper;

import com.szero.system.domain.entity.CountDownTime;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
public interface CountDownTimeMapper {

    List<CountDownTime> listCountDownTime();
}
