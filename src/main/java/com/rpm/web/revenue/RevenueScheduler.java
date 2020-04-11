package com.rpm.web.revenue;

import com.rpm.web.employee.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class RevenueScheduler {
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    RevenueRepository revenueRepository;

    @Scheduled(cron="0 0 0 1 * *")
    public void test(){
        SimpleDateFormat sdf =new SimpleDateFormat("yyyyMM");
        Date now = new Date();
        String date = sdf.format(now);
        System.out.println("start");
        employeeRepository.findAll().forEach(el->{
            Revenue revenue=new Revenue();
            revenue.setMonth(date);
            revenue.setEmCode(el.getEmCode());
            revenue.setEmName(employeeRepository.findByEmCode(el.getEmCode()).getEmName());
            revenue.setCenterCode(employeeRepository.findByEmCode(el.getEmCode()).getCenterCode());
            revenue.setEmRevenue((int) (Math.random() * 100 + 1) * 1000);
            revenueRepository.save(revenue);
        });
        System.out.println("end");
    }
}
