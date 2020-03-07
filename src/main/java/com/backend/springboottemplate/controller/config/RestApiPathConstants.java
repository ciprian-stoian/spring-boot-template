package com.backend.springboottemplate.controller.config;

public interface RestApiPathConstants {
    String API_BASE_PATH = "/v1";
    String BY_UUID_PATH = "/{uuid}";
    String BY_UUID_PARAM = "uuid";

    interface AuthenticationController {
        String AUTHENTICATION_RELATIVE_PATH = "/authentication";
        String SIGN_UP_RELATIVE_PATH = "/sign-up";
        String SIGN_IN_RELATIVE_PATH = "/sign-in";

        String AUTHENTICATION_PATH = API_BASE_PATH + AUTHENTICATION_RELATIVE_PATH;
    }

    interface UserProfileController {
        String USER_PROFILE_RELATIVE_PATH = "/user-profile";

        String USER_PROFILE_PATH = API_BASE_PATH + USER_PROFILE_RELATIVE_PATH;
    }
}
