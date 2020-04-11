package com.rpm.web.contents;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
@Setter
@Getter
public class SearchDetailCondition {
    private boolean checked;
    private String code,name;
    private int count;
    private String bigCategory;

}
