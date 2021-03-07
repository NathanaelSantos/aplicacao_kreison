package model;

import java.util.Base64;

public class StringUtil {

    public String encodeBase64String(String userInfo) {
        String encode = "<fh!@"+userInfo+"&bFYX>";
        String test = Base64.getEncoder().encodeToString(encode.getBytes());
        return Base64.getEncoder().encodeToString(test.getBytes());
    }

    public String decodeBase64String(String encodedString) {
        return new String(Base64.getDecoder().decode(encodedString));
    }
}
