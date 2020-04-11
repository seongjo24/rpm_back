package com.rpm.web.recommend;

import com.rpm.web.user.User;
import lombok.*;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Component
@Lazy
@Entity
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="recommend")
public class Recommend implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "RECO_SEQ") @NotNull private Long recoSeq;
    @Column(name = "USERID" ,length = 25) @NotNull private String userId;
    @Column(name = "NAME", length = 30) @NotNull private String name;
    @Column(name = "CENTER_CODE" ,length = 3) @NotNull private String centerCode;
    @Column(name = "CENTER_REGION", length = 10) private String centerRegion;
    @Column(name = "CENTER_NAME", length = 20) private String centerName;
    @Column(name = "MIN_BEGIN_YEAR") private int minBeginYear;
    @Column(name = "MAX_BEGIN_YEAR") private int maxBeginYear;
    @Column(name = "MIN_PRICE") private int minPrice;
    @Column(name = "MAX_PRICE") private int maxPrice;
    @Column(name = "MIN_MILEAGE") private int minMilage;
    @Column(name = "MAX_MILEAGE") private int maxMilage;
    @Column(name = "TRANSMISSIONCD_NAME", length = 10) private String transmissioncdName;
    @Column(name = "FUEL_TYPED_NAME", length = 9) private String fuleTypedName;
    @Column(name = "MAKENM", length = 20) private String makeNm;
    @Column(name = "MODEL_GRP_NM", length = 100) private String modelGrpNm;
    @Column(name = "MODELNM", length = 45) private String modelNm;
    @Column(name = "REC_COMMENT_CD", length = 3) private String recCommentCd;


}
