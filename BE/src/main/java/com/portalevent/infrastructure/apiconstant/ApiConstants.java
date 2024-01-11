package com.portalevent.infrastructure.apiconstant;

/**
 * @author thangncph26123
 */
public class ApiConstants {

    // API Indentity

    public static final String API_GET_USER_BY_LIST_ID = "/api/UserSigns/GetDetailUsers";

    public static final String API_GET_USER_BY_ID = "/api/UserSigns/FindById";

    public static final String API_GET_ALL_USER_BY_ROLE_AND_MODULE = "/api/UserSigns/GetUserFromRoleModuleAsync";

    public static final String API_GET_ROLES_USER_BY_ID_USER_AND_MODULE_CODE = "/api/UserSigns/GetRoleFromUserModule";

    // API Consumer

    public static final String API_READ_FILE_LOG = "/api/rabbit-consumer/read-log/page";

    public static final String API_DOWLOAD_FILE_LOG = "/api/rabbit-consumer/download-log";

    //API Honey
    public static final String API_GET_HONEY_CATEGORY = "/api/add-point-student/list-category";

    public static final String API_SEND_CONVERSION_HONEY = "/api/add-point-student/portal-events";
}

