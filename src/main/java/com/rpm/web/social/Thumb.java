package com.rpm.web.social;

import com.rpm.web.user.User;
import lombok.*;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Component @Lazy @Entity
@ToString
@Getter @Setter(AccessLevel.PUBLIC)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Table(name="THUMB")
public class Thumb implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "THUMBSEQ") @NotNull private Long thumbSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOARDSEQ")
    @NotNull
    private Social boardSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USERSEQ")
    @NotNull
    private User userSeq;

}
