package com.youssef.filesprocessor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.youssef.filesprocessor.searchreplace.SearchReplaceEngine;

@Configuration
public class SearchReplaceEngineConfig
{
    @Bean("searchReplaceEngineBean")
    public SearchReplaceEngine getSearchReplaceEngine()
    {
        return new SearchReplaceEngine();
    }
}
