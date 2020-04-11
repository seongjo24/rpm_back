package com.rpm.web.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/employee")
@CrossOrigin(origins = "http://kangsrpmdatabase.s3-website.ap-northeast-2.amazonaws.com", allowedHeaders = "*")
public class EmployeeController {
    @Autowired EmployeeRepository employeeRepository;

    @GetMapping("/employeeList/{centerCode}")
    public Map<String, List<Employee>> employeeList(@PathVariable String centerCode){
        Map<String,List<Employee>> map =new HashMap<>();
        map.put("result",employeeRepository.findByCenterCode(centerCode));
        return map;
    }
}
