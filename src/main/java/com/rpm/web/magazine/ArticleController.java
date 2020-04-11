package com.rpm.web.magazine;

import com.rpm.web.contents.Cars;
import com.rpm.web.contents.CarsRepository;
import com.rpm.web.contents.CarsService;
import com.rpm.web.proxy.Trunk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@CrossOrigin(origins = "http://kangsrpmdatabase.s3-website.ap-northeast-2.amazonaws.com", allowedHeaders = "*")
@RequestMapping("/magazine")
public class ArticleController {
    @Autowired Trunk<Object> trunk;
    @Autowired ArticleService articleService;
    @Autowired ArticleRepository articleRepository;
    @Autowired ExtractedWordRepository extractedWordRepository;
    @Autowired List<ExtractedWord> extractedWordList;
    @Autowired List<Cars> cars;
    @Autowired CarsRepository carsRepository;
    @Autowired CarsService carsService;
    @Autowired List<Article> articleList;

    @GetMapping("/init")
    public Map<String, Object> init () {
        trunk.clear();
        extractedWordList = (List<ExtractedWord>) extractedWordRepository.findAll();
        articleList = (List<Article>) articleRepository.findAll();
        trunk.put(Arrays.asList("articles","extractedWordList"), Arrays.asList(articleList , extractedWordList));
        return trunk.get();
    }

    @GetMapping("/AIEditer/{keyword}")
    public Map<String, Object> aiEditer (@PathVariable String keyword) {
        trunk.clear();
        List<Cars> tempCars = new ArrayList<>();
        List<Article> tmpArticle = new ArrayList<>();
        cars = carsService.findAllByDistinct((List<Cars>) carsRepository.findAll());
        cars.forEach(item ->{
            if(item.getTruckName().contains(keyword.trim()))
                tempCars.add(item);
        });

        trunk.put(Arrays.asList("carsList"), Arrays.asList(tempCars.stream().limit(16)));
        articleList = (List<Article>) articleRepository.findAll();
        articleList.forEach(item -> {
            if(item.getSubject().contains(keyword))
            tmpArticle.add(item);
        });

        trunk.put(Arrays.asList("articles"), Arrays.asList(tmpArticle));

        return trunk.get();
    }

}
