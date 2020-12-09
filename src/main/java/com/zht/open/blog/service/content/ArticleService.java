package com.zht.open.blog.service.content;


import com.zht.open.blog.domain.vo.ArticleVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhanghaotian
 * 文章操作
 */
@Service
public class ArticleService {

    private static Logger logger = LoggerFactory.getLogger(ArticleService.class);

    @Value("${path.article}")
    private String articlesPath;

    public void addArticle(String brief,String content, String title) {
        long now = System.currentTimeMillis();
        File file = new File(articlesPath + File.separator + now);
        try {
            file.createNewFile();
            //暂时只有标题和文章正文，后面还会增加一些别的字段，比如，关键词之类的
            String article = title + "@@@" + brief + "@@@" + content;
            FileWriter writer = new FileWriter(file);
            writer.write(article);
            writer.close();
        } catch (IOException e) {
            logger.error("[ARTICLE SERVICE] add article fail , title = {}", title, e);
        }
    }

    public List<ArticleVO> listArticle(){
        File file = new File(articlesPath);
        List<ArticleVO> articleList = new ArrayList<>();
        try {
            if (file.exists() && file.isDirectory()) {
                File[] files;
                if ((files = file.listFiles()) != null) {
                    for (File f : files) {
                        FileReader reader = new FileReader(f);
                        char[] chars = new char[100];
                        reader.read(chars,0,100);
                        StringBuilder brief = new StringBuilder();
                        if(chars != null && chars.length != 0){
                            for(char c:chars){
                                brief.append(c);
                            }
                        }
                        ArticleVO articleVO = new ArticleVO();
                        String[] strings = brief.toString().split("@@@");
                        articleVO.setId(f.getName());
                        articleVO.setTitle(strings[0]);
                        articleVO.setContent(strings[2]);
                    }
                }
            }
        }catch (FileNotFoundException e){
            logger.error("[ARTICLE SERVICE] read article fail");
        } catch (Exception e){
            logger.error("[ARTICLE SERVICE] list article fail");
        }
        return articleList;
    }


}
