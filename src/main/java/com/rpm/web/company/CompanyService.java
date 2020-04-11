package com.rpm.web.company;

import com.rpm.web.contents.Cars;
import com.rpm.web.recommend.Recommend;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CompanyService {
    public List<Cars> getBestCarList(Recommend recommend);
}
