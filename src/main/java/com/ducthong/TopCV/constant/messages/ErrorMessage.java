package com.ducthong.TopCV.constant.messages;

public class ErrorMessage {
    public static class Auth {
        public static final String LOGIN_FAIL = "auth.error.login";
        public static final String NOT_EXISTED_USERNAME = "auth.error.login.not-existed-username";
        public static final String SESSION_EXPRIED = "auth.error.token.session-expried";
        public static final String MALFORMED = "auth.error.token.malformed";
        public static final String ILLEGAL = "auth.error.token.illegal";
        public static final String UNSUPPORTED = "auth.error.token.unsupported";
        public static final String SIGNATURE = "auth.error.token.signature";
    }

    public static class Account {
        public static final String EMPTY_LIST = "account.error.empty-list";
        public static final String NOT_FOUND = "account.error.not-found";
        public static final String USERNAME_EXISTED = "account.error.username-existed";
        public static final String EMAIL_EXISTED = "account.error.email-existed";
        public static final String GET_DETAIL = "account.error.get-one-fail";
        public static final String UPDATE = "account.error.update-fail";
        public static final String DELETE_TEMP = "account.error.delete-temp";
        public static final String DELETE_PERM = "account.error.delete-perm";
    }

    public static class Image {}

    public static class Industry {
        public static final String EMPTY_LIST = "industry.error.empty-list";
        public static final String NOT_FOUND = "industry.error.not-found";
        public static final String EXISTED = "industry.error.existed";
        public static final String ADD_ONE = "industry.error.add-one";
    }
}
