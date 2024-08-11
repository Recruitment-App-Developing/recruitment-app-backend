package com.ducthong.TopCV.constant.messages;

public class SuccessMessage {
    public static class Auth {
        public static final String LOGIN_SUCCESS = "auth.success.login";
        public static final String INTROSPECT = "auth.success.token.introspect";
    }

    public static class Account {
        public static final String GET_LIST = "account.success.get-list";
        public static final String GET_ONE = "acount.success.get-one";
        public static final String ADD = "account.sccuess.add-success";
        public static final String UPDATE = "account.success.update-success";
        public static final String DELETE_TEMP = "account.success.delete-temp";
        public static final String RETRIEVE = "account.success.retrieve";
        public static final String DELETE_PERM = "account.success.delete-perm";
    }

    public static class Industry {
        public static final String GET_LIST = "industry.success.get-list";
        public static final String GET_DETAIL = "industry.success.get-detail";
        public static final String ADD_ONE = "industry.success.add-one";
        public static final String UPDATE_ONE = "industry.success.update-one";
    }
}
