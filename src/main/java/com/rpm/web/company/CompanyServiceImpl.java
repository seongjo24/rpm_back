package com.rpm.web.company;

import com.rpm.web.contents.Cars;
import com.rpm.web.contents.CarsRepository;
import com.rpm.web.recommend.Recommend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CompanyServiceImpl implements CompanyService{
    @Autowired
    CarsRepository carsRepository;
    public List<Cars> getBestCarList(Recommend recommend) {
        List<Cars> list =new ArrayList<>();
        //모델명이같고 원하는 가격대 안에있는 차
        for(Cars car :carsRepository.findByModelnmAndCenterCodeOrderByPrice(recommend.getModelNm(),recommend.getCenterCode())){
            if ( list.size()<5&&recommend.getMinPrice() <= car.getPrice() && recommend.getMaxPrice() > car.getPrice()&&!list.contains(car)) {

                list.add(car);
            }
            if(list.size()>=5) return list;
        }
        //모델명이같고 0원 에서 최대 희망 가격대 +10% 안에있는 차
        for(Cars car :carsRepository.findByModelnmAndCenterCodeOrderByPrice(recommend.getModelNm(),recommend.getCenterCode())){
            if(0<car.getPrice()&&recommend.getMaxPrice()+(recommend.getMaxPrice()/10)>car.getPrice()&&!list.contains(car)){
                list.add(car);
            }
            if(list.size()>=5) return list;
        }
        //차종이같고 원하는 가격대 안에있는 차
        for(Cars car : carsRepository.findByCategorycdAndCenterCode(carsRepository.findFirstByModelnm(recommend.getModelNm()).getCategorycd(),recommend.getCenterCode())){
            if(recommend.getMinPrice()<car.getPrice()&&recommend.getMaxPrice()>car.getPrice()&&!list.contains(car)){
                list.add(car);
            }
            if(list.size()>=5) return list;
        }
        //차종이같고 0원 에서 최대 희망 가격대 +10% 안에있는 차
        for(Cars car : carsRepository.findByCategorycdAndCenterCode(carsRepository.findFirstByModelnm(recommend.getModelNm()).getCategorycd(),recommend.getCenterCode())){
            if(0<car.getPrice()&&recommend.getMaxPrice()+(recommend.getMaxPrice()/10)>car.getPrice()&&!list.contains(car)){
                list.add(car);
            }
            if(list.size()>=5) return list;
        }
        //가격대에 상관없이 모델명이 같은 차
        for(Cars car :carsRepository.findByModelnmAndCenterCodeOrderByPrice(recommend.getModelNm(),recommend.getCenterCode())){
            if(!list.contains(car)){
                list.add(car);
            }
            if(list.size()>=5) return list;
        }
        // 모든 보유차량중에 가격대 안에 있는차
        for(Cars car: carsRepository.findByCenterCode(recommend.getCenterCode())){
            if(recommend.getMinPrice()<car.getPrice()&&recommend.getMaxPrice()>car.getPrice()&&!list.contains(car)) {
                list.add(car);
            }
            if(list.size()>=5) return list;
        }



        return list;
    }

}
