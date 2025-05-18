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
            public static final String BASE = V1.PREFIX + "/candidate";
            public static final String GET_DETAIL = BASE + "/detail";
            public static final String UPDATE = BASE + "/update";
            public static final String REGISTER_CANDIDATE = BASE + "/register";
            public static final String ROLE = PREFIX + "/candidate";

            public static final class Account {
                public static final String BASE = Candidate.ROLE + "/account";
                public static final String GET_DETAIL = BASE + "/{id}";
                public static final String UPDATE = BASE + "/update/{id}";
            }
        }

        public static final class Employer {
            public static final String ROLE = PREFIX + "/employer";
            public static final String BASE = PREFIX + "/employer";
            public static final String MY_ACCOUNT = BASE + "/my-account";
            public static final String REGISTER_EMPLOYER = BASE + "/register";
            public static final String UPDATE_EMPLOYER = BASE + "/update";
            public static final String REGISTER_COMPANY = BASE + "/register-company/{companyId}";

            public static final class Account {
                public static final String BASE = Employer.ROLE + "/account";
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
            public static final String CHANGE_PASSWORD = BASE + "/change-password";
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
            public static final String SEARCH_JOB = BASE + "/search-job";
            public static final String GET_LIST_BY_COMPANY = BASE + "/byCompany";
            public static final String FIND_LIST_BY_COMPANY = BASE + "/findJobByCompany/{companyId}";
            public static final String GET_LIST_JOB_SPEC = BASE + "/spec";
            public static final String GET_DETAIL = BASE + "/{jobId}";
            public static final String GET_DETAIL_JOB_PAGE = BASE + "/job-page/{jobId}";
            public static final String ADD_ONE = BASE;
            public static final String UPDATE_ONE = BASE + "/update/{jobId}";
            public static final String HIDDEN_ONE = BASE + "/hidden/{jobId}";
            public static final String GET_LIST_JOB_ADDRESS = BASE + "/job-address/{jobId}";
            public static final String UPDATE_JOB_ADDRESS = BASE + "/job-address/update/{jobId}";
            public static final String DELETE_JOB_ADDRESS = BASE + "/job-address/delete/{jobId}";
        }

        public static final class Cv {
            public static final String BASE = V1.PREFIX + "/cv";
            public static final String GET_ONE_CV = BASE + "/get-one/{cvId}";
            public static final String GET_LIST_BY_ACCOUNT_ID = BASE + "/my-cv";
            public static final String ADD_ONE = BASE + "/add";
            public static final String UPDATE_CV = BASE + "/update";
            public static final String DELETE = BASE + "/delete/{cvId}";
        }

        public static final class Company {
            public static final String BASE = V1.PREFIX + "/company";
            public static final String GET_LIST = BASE;
            public static final String GET_LIST_FOR_EMPLOYER = BASE + "/employer";
            public static final String GET_BRIEF_COMPANY = BASE + "/brief-company/{companyId}";
            public static final String GET_DETAIL = BASE + "/detail-company/{companyId}";
            public static final String ADD_COMPANY = BASE + "/add";
            public static final String GET_MY_COMPANY = BASE + "/my-company";
        }

        public static final class Application {
            public static final String BASE = V1.PREFIX + "/application";
            public static final String APPLY = BASE + "/apply";
            public static final String GET_APPLIED_CANDIDATE_BY_JOB = BASE + "/applied-candidate-by-job/{jobId}";
            public static final String STATISTIC_BY_COMPANY = BASE + "/statistic-cv-by-company";
            public static final String HISTORY_APPLICAITON = BASE + "/application-history";
            public static final String SEARCH_CANDIDATE_BY_JOB = BASE + "/search-candidate/{jobId}";
            public static final String UPDATE_STATUS = BASE + "/update-status";
        }

        public static final class CvProfile {
            public static final String BASE = V1.PREFIX + "/cv-profile";
            public static final String GET_ONE = BASE + "/{cvProfileId}";
            public static final String UPDATE_EDUCATION = BASE + "/education/update";
            public static final String DELETE_EDUCATION = BASE + "/education/delete/{educationId}";
        }

        public static final class Statistic {
            public static final String BASE = V1.PREFIX + "/statistic";
            public static final String STATISTIC_GENERAL_JOB_BY_INDUSTRY = BASE + "/statistcGeneralJobByIndustry";
            public static final String STATISTIC_GENERAL_JOB_BY_DAY = BASE + "/statisticGeneralJobByDay";
            public static final String STATISTIC_COMPANY_JOB_BY_INDUSTRY = BASE + "/statisticCompanyJobByIndustry";
            public static final String STATISTIC_APPLICATION_STATUS_BY_COMPANY =
                    BASE + "/statisticApplicationStatusByCompany";
            public static final String STATISTIC_APPLY_CANDIDATE_BY_DAY = BASE + "/statisticApplyCandidateByDay";
            public static final String STATISTIC_RECRUITMENT_EFFECTIVE = BASE + "/statisticRecruitmentEffective";
        }

        public static final class Address {
            public static final String BASE = V1.PREFIX + "/address";
            public static final String GET_LIST_PROVINCE = BASE + "/provinces";
            public static final String GET_LIST_DISTRICT_BY_PROVINCE = BASE + "/districts/{provinceId}";
            public static final String GET_LIST_WARD_BY_DISTRICT = BASE + "/wards/{districtId}";
        }

        public static final class CvInfor {
            public static final String BASE = V1.PREFIX + "/cv-info";
            public static final String GET_LIST = BASE + "/get-list";
            public static final String GET_DETAIL = BASE + "/get-detail/{inforId}";
        }

        public static final class HealthCheck {
            public static final String BASE = V1.PREFIX + "/actuator/health";
        }
    }
}
