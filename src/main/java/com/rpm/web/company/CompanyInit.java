package com.rpm.web.company;

import com.rpm.web.contents.CarsRepository;
import com.rpm.web.employee.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import java.text.SimpleDateFormat;
import java.util.List;

@Order(value=1)
@Component
public class CompanyInit implements ApplicationRunner {
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    EmployeeRepository employeeRepository;



    @Override
    public void run(ApplicationArguments args) throws Exception {

        System.out.println( new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss").format (System.currentTimeMillis())+ "  INFO 18844 --- [           CompanyInit ]         : CompanyInit Start ");



        long count = companyRepository.count();
        if (count == 0) {
            List<String> code = companyRepository.findCenterCode();
            code.forEach(el -> {

                Company company = new Company();
                company.setCenterCode(el);
                company.setEmployees(employeeRepository.findByCenterCode(el));
                company.setCenterName(companyRepository.findByCenterCode(el));
                company.setCenterRegion(companyRepository.findCenterRigionByCenterCode(el));
                companyRepository.save(company);

            });
        }


        System.out.println( new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss").format (System.currentTimeMillis()) + "  INFO 18844 --- [           CompanyInit ]         : CompanyInit End ");

    }

}