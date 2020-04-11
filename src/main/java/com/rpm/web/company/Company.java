package com.rpm.web.company;


import com.rpm.web.employee.Employee;
import lombok.*;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Component
@Entity
@NoArgsConstructor
@Setter(AccessLevel.PUBLIC)
@Getter
@ToString
@Lazy
@Table(name = "company")
public class Company implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull @Column(name = "COMPANY_ID") private Long companyId;
    @NotNull@Column(name = "CENTER_CODE", length = 3) private String centerCode;
    @NotNull@Column(name = "CENTER_NAME", length = 20) private String centerName;
    @NotNull@Column(name = "CENTER_REGION", length = 10) private String centerRegion;
    @Column(name = "MONTH_REVENUE") private int monthRevenue;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMPANY_ID")
    private List<Employee> employees;
}
