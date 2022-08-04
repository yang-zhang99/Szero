package com.ttdo.oauth.domain.service.impl;


import com.ttdo.oauth.domain.entity.Language;
import com.ttdo.oauth.domain.service.LanguageService;
import com.ttdo.oauth.infra.mapper.LanguageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author qingsheng.chen@hand-china.com
 */
@Service
public class LanguageServiceImpl implements LanguageService {
    private static List<Language> languages;

    private LanguageMapper languageMapper;

    @Autowired
    public LanguageServiceImpl(LanguageMapper languageMapper) {
        this.languageMapper = languageMapper;
    }

    @Override
    public List<Language> listLanguage() {
        if (languages == null) {
            languages = languageMapper.selectList(null);
        }
        return languages;
    }
}
