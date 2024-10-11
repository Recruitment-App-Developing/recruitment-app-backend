package com.ducthong.TopCV.constant;

public class Endpoint {
    public static final class V1 {
        public static final String PREFIX = "api/v1";

        public static final class Industry {
            public static final String BASE = V1.PREFIX + "/industry";
            public static final String GET_LIST_ACTIVE = BASE + "/active";
            public static final String GET_LIST_ALL = BASE + "/all";
            public static final String GET_DETAIL_ACTIVE = BASE + "/active/{id}";
            public static final String GET_DETAIL_ALL = BASE + "/all/{id}";
            public static final String ADD_ONE = BASE + "/add";
            public static final String UPDATE_ONE = BASE + "/update";
            public static final String DELETE_TEMP = BASE + "/delete-temporarily/{id}";
            public static final String RETRIEVE = BASE + "/retrieve/{id}";
            public static final String DELETE_PERM = BASE + "/delete-permanently/{id}";
        }

        public static final class Candidate {
            public static final String ROLE = PREFIX + "/candidate";

            public static final class Account {
                public static final String BASE = Candidate.ROLE + "/account";
                public static final String GET_DETAIL = BASE + "/{id}";
                public static final String UPDATE = BASE + "/update/{id}";
            }
        }

        public static final class Employer {
            public static final String ROLE = PREFIX + "/employer";

            public static final class Account {
                public static final String BASE = Employer.ROLE + "/account";
                public static final String GET_DETAIL = BASE + "/{id}";
                public static final String ADD_ONE = BASE;
                public static final String UPDATE = BASE + "/update/{id}";
            }
        }

        public static final class Admin {
            public static final String ROLE = PREFIX + "/admin";

            public static final class Auth {
                public static final String BASE = Admin.ROLE + "/auth";
                public static final String LOGIN = BASE + "/login";
            }

            public static final class Account {
                public static final String BASE = Admin.ROLE + "/account";
                public static final String GET_LIST_ACTIVE = BASE + "/active";
                public static final String GET_LIST_DELETED = BASE + "/deleted";
                public static final String GET_DETAIL = BASE + "/{id}";
                public static final String DELETE_TEMP = BASE + "/delete-temporarily/{id}";
                public static final String RETRIEVE = BASE + "/retrieve/{id}";
                public static final String DELETE_PERM = BASE + "/delete-permanently/{id}";
            }
        }

        public static final class Authentication {
            public static final String BASE = V1.PREFIX + "/auth";
            public static final String LOGIN = BASE + "/login";
            public static final String INTROSPECT = BASE + "/introspect";
        }

        public static final class Account {
            public static final String BASE = V1.PREFIX + "/accounts";
            public static final String GET_ONE = BASE + "/{id}";
            public static final String ADD_ONE = BASE;
            public static final String UPDATE_CANDIDATE = BASE + "/{id}";
        }

        public static final class Job {
            public static final String BASE = V1.PREFIX + "/job";
            public static final String GET_LIST_JOB = BASE;
            public static final String GET_LIST_BY_COMPANY = BASE + "/byCompany";
            public static final String GET_LIST_JOB_SPEC = BASE + "/spec";
            public static final String GET_DETAIL = BASE + "/{jobId}";
            public static final String ADD_ONE = BASE;
        }

        public static final class Cv {
            public static final String BASE = V1.PREFIX + "/cv";
            public static final String GET_LIST_BY_ACCOUNT_ID = BASE + "/my-cv";
            public static final String ADD_ONE = BASE + "/add";
            public static final String UPDATE_CV = BASE + "/update";
            public static final String DELETE = BASE + "/delete/{cvId}";
        }

        public static final class Company {
            public static final String BASE = V1.PREFIX + "/company";
            public static final String GET_LIST = BASE;
            public static final String GET_BRIEF_COMPANY = BASE + "/brief-company/{companyId}";
            public static final String GET_DETAIL = BASE + "/detail-company/{companyId}";
        }

        public static final class Application {
            public static final String BASE = V1.PREFIX + "/application";
            public static final String APPLY = BASE + "/apply";
            public static final String STATISTIC_BY_COMPANY = BASE + "/statistic-cv-by-company";
        }

        public static final class HealthCheck {
            public static final String BASE = V1.PREFIX + "/actuator/health";
        }
    }
}
