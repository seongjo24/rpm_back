package com.rpm.web.employee;

import lombok.*;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Component
@Entity
@NoArgsConstructor
@Setter(AccessLevel.PUBLIC)
@Getter
@ToString
@Lazy
@Table(name = "employee")
public class Employee implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull @Column(name = "EM_ID") private Long emId;
    @NotNull @Column(name = "EM_CODE",length = 6) private String emCode;
    @Column(name = "MONTH_REVENUE") private int monthRevenue;
    @Column(name = "CENTER_NAME", length = 20) private String centerName;
    @Column(name = "EM_NAME", length = 20) private String emName;
    @Column(name = "CENTER_CODE", length = 3) private String centerCode;
    @Column(name = "EM_POSITION", length = 3) private String emPosition;
    @Column(name = "CARCD", length = 12) private String carcd;

}
