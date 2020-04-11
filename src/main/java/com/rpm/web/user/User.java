package com.rpm.web.user;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.rpm.web.social.Social;
import com.rpm.web.social.Thumb;
import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component @Lazy @Entity
@Getter @Setter
@ToString
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Table(name="USER")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "userSeq")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "USERSEQ") @NotNull private Long userSeq;
    @Column(name = "USERID", unique = true, length = 25) @NotNull private String userid;
    @Column(name = "PASSWD") @NotNull private String passwd;
    @Column(name = "NAME", length = 30) @NotNull private String name;
    @Column(name = "EMAIL", length=100) @NotNull private String email;
    @Column(name = "AUTH") @NotNull private int auth;
    @Column(name = "GENDER", length=10)  private String gender;
    @Column(name = "BIRTHMONTH", length=10)  private String birthMonth;
    @Column(name = "REGION", length=10)  private String region;




    @OneToMany(mappedBy = "userSeq", cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Thumb> thumbs = new ArrayList<>();

    @OneToMany(mappedBy = "userSeq", cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Social> socials = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }


    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public String getUsername() {
        return this.userid;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public String getPassword() {
        return this.passwd;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isEnabled() {
        return true;
    }

}
