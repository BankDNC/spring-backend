package com.bankdnc.springbackend.constans;

public class Constant {

    private Constant() {
        throw new IllegalStateException("Utility class");
    }

    public static final String API_V1 = "/api/v1";
    public static final String ENDPOINT_AUTH = API_V1+"/auth";
    public static final String REGISTER = "/register";
    public static final String LOGIN = "/login";
    public static final String ENDPOINT_ACCOUNT = API_V1+"/account";
    public static final String CREATE_ACCOUNT = "/create";
    public static final String ENDPOINT_TRANSACTION = API_V1+"/transaction";
    public static final String LOAD_ACCOUNT = "/load";
    public static final String HISTORY = "/history";
    public static final String TRANSFER = "/transfer";
    public static final String WITHDRAW = "/withdraw";
}
