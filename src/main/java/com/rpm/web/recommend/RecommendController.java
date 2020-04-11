package com.rpm.web.recommend;

import com.rpm.web.company.CompanyRepository;
import com.rpm.web.contents.CarsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recommend")
@CrossOrigin(origins = "http://kangsrpmdatabase.s3-website.ap-northeast-2.amazonaws.com", allowedHeaders = "*")
public class RecommendController {
    @Autowired
    CarsRepository carsRepository;
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    RecommendRepository recommendRepository;

    @GetMapping("/centerName/{region}")
    public List<String> centerRigion(@PathVariable String region){
        if(region.equals("경기")){
            region="경기/인천";
        }
        List<String> centerName =  companyRepository.findByCenterRegion(region);
        return centerName;
    }

    @GetMapping("/centerRigion")
    public List<String> centerName(){
        List<String> region =  companyRepository.findAllRigion();
        return region;

    }
    @PostMapping("/inputRecommend")
    public void inputRecommend(@RequestBody Recommend recommend){
        recommend.setCenterCode(companyRepository.findByCenterName(recommend.getCenterName()));
        recommendRepository.save(recommend);


    }
    @GetMapping("/customerList/{userid}")
    public List<Recommend> customerList(@PathVariable String userid){
        List<Recommend> list=recommendRepository.findByCenterCode(userid);
        list.sort((a,b) -> b.getRecoSeq().compareTo(a.getRecoSeq()));
        return  list;

    }
    @PostMapping("/recommendRemove")
    public void recommendRemove(@RequestBody List<Recommend> recommends){
        recommends.forEach(el->{
            recommendRepository.deleteById(el.getRecoSeq());
        });
    }
}
