package com.yang.system.api.controller.v1;

import com.yang.system.domain.entity.CountDownTime;
import com.yang.system.infra.mapper.CountDownTimeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("/count-down-timer")
public class CountDownTimeController {

    @Autowired
    private CountDownTimeMapper countDownTimeMapper;

    @GetMapping()
    public ResponseEntity<List<CountDownTime>> select() {
        return ResponseEntity.ok(countDownTimeMapper.listCountDownTime());
    }
}