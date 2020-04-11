package com.rpm.web.revenue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/revenue")
@CrossOrigin(origins = "http://kangsrpmdatabase.s3-website.ap-northeast-2.amazonaws.com", allowedHeaders = "*")
public class RevenueController {
    @Autowired RevenueRepository revenueRepository;

    @GetMapping("/emRevenue/{centerCode}")
    public List<Revenue> emRevenue(@PathVariable String centerCode){
        return revenueRepository.findByCenterCode(centerCode);
    }

}
