package com.iarasantos.studentservice.constants;

public class AppConstant {
    private AppConstant() {}
    public static final String EMAIL_REGEXPR = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
}
