package com.rpm.web.company;

import com.rpm.web.contents.Cars;
import com.rpm.web.contents.CarsRepository;
import com.rpm.web.recommend.Recommend;
import com.rpm.web.recommend.RecommendRepository;
import com.rpm.web.recommendedCar.RecommendedCarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/company")
@CrossOrigin(origins = "http://kangsrpmdatabase.s3-website.ap-northeast-2.amazonaws.com", allowedHeaders = "*")
public class CompanyController {
    @Autowired
    CarsRepository carsRepository;
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    RecommendRepository recommendRepository;
    @Autowired
    CompanyServiceImpl companyServiceImpl;
    @Autowired
    RecommendedCarRepository recommendedCarRepository;


    @GetMapping("/carList/{centerCode}")
    public Map<String, List<Cars>> carList(@PathVariable String centerCode){
        Map<String,List<Cars>> map = new HashMap();

        map.put("result",carsRepository.findByCenterCode(centerCode));
        return map;
    }
    @PostMapping("/bestCarList")
    public List<Cars> bestCarList(@RequestBody Recommend recommend){

        return companyServiceImpl.getBestCarList(recommend);
    }
    @PostMapping("/carRemove")
    public void carRemove(@RequestBody List<Cars> cars){
        cars.forEach(el->{
            if(recommendedCarRepository.findFirstByCars(el)!=null){
                recommendedCarRepository.findByCars(el).forEach(p->{
                    recommendedCarRepository.deleteById(p.getRecoCarSeq());
                });
            }
            carsRepository.deleteById(el.getCid());

        });
    }



}
