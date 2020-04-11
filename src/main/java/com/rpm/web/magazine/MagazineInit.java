package com.rpm.web.magazine;

import com.rpm.web.util.ArticleCrawler;
import com.rpm.web.util.MakeCarDummyList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

@Component
public class MagazineInit implements ApplicationRunner {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ExtractedWordRepository extractedWordRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        System.out.println( new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss").format (System.currentTimeMillis()) + "  INFO 18844 --- [           MagazineInit ]         : MagazineInit Start ");

       MakeCarDummyList http = new MakeCarDummyList();
        Map<String, Map<String, Object>> map = new HashMap<>();
        ArticleCrawler crawler = new ArticleCrawler( articleRepository , extractedWordRepository );
        if (articleRepository.count() == 0 ) crawler.motorgraphComtroller();
        if (extractedWordRepository.count() == 0 ) crawler.extractor();

        System.out.println( new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss").format (System.currentTimeMillis()) + "  INFO 18844 --- [           MagazineInit ]         : MagazineInit End ");

    }


}
