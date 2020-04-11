package com.rpm.web.util;

public enum PathEnum {
    UPLOAD_PATH;

    @Override
    public String toString() {
        String  result= "";
        switch (this) {
            case UPLOAD_PATH:
                //resource\\static의 주소를 작성.
                result= "C:\\Users\\yejee\\IdeaProjects\\RPM\\src\\main\\resources\\static";
                break;
        }
        return result;
    }
}

