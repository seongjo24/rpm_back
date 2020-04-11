package com.rpm.web.social;

import com.fasterxml.jackson.annotation.*;
import com.rpm.web.user.User;
import lombok.*;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Component @Lazy @Entity
@Getter @Setter
@ToString(exclude = {"userSeq","thumbs"})
@NoArgsConstructor
@Table(name="SOCIALBOARD")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "boardSeq")
public class Social implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "BOARDSEQ") @NotNull private Long boardSeq;
    @Column(name = "BOARDDATE", length=25) @NotNull private String boardDate;
    @Column(name = "CARCODE", length=50) @NotNull private String carCode;
    @Column(name = "CARNAME", length=100) @NotNull private String carName;
    @Column(name = "BOARDCONTENT", length = 20000) @NotNull private String boardContent;
    @Column(name = "BOARDIMG", length=200) @NotNull private String boardImg;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USERSEQ")
    @NotNull
    private User userSeq;

    @OneToMany(mappedBy = "boardSeq", cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Thumb> thumbs = new ArrayList<>();

    @Builder
    private Social(String boardDate, String carCode, String carName, String boardContent, String boardImg) {
        Assert.hasText(boardDate, "boardDate must not be empty");
        Assert.hasText(carCode, "carCode must not be empty");
        Assert.hasText(boardContent, "boardContent must not be empty");
        Assert.hasText(boardImg, "boardImg must not be empty");
        this.boardDate = boardDate;
        this.carCode = carCode;
        this.carName = carName;
        this.boardContent = boardContent;
        this.boardImg = boardImg;
    }
}
