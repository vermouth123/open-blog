package com.zht.open.blog.service.search;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

/**
 * 包括建索引（倒排，正排，直接映射）、分词、关键词提取等。
 */
@Service
public class SearchService {

    private static Logger logger = LoggerFactory.getLogger(SearchService.class);

    //
    public void generateIndex(String content, String id) {
        File file = new File("index/index");
        if(file.exists() && file.isFile()){
        }
    }
}
