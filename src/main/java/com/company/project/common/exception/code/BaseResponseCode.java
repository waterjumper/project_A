package com.company.project.common.exception.code;

/**
 * Error Code
 */
public enum BaseResponseCode implements ResponseCodeInterface {
    /**
     * Error codes
     */
    SUCCESS(0, "Operation successful"),
    SYSTEM_BUSY(500001, "System is busy, please try again later"),
    OPERATION_ERRO(500002, "Operation failed"),

    TOKEN_ERROR(401001, "Login credentials have expired, please log in again"),
    DATA_ERROR(401003, "Incoming data is abnormal"),
    NOT_ACCOUNT(401004, "User does not exist, please register first"),
    USER_LOCK(401005, "This user has been locked, please contact the operation staff"),
    PASSWORD_ERROR(401006, "Username or password is incorrect"),
    METHODARGUMENTNOTVALIDEXCEPTION(401007, "Method parameter validation exception"),
    UNAUTHORIZED_ERROR(401008, "Authentication validation failed"),
    ROLE_PERMISSION_RELATION(401009, "This menu permission has a sub-level association and cannot be deleted"),
    OLD_PASSWORD_ERROR(401010, "Old password is incorrect"),
    NOT_PERMISSION_DELETED_DEPT(401011,
            "There are still users associated with this organizational structure, deletion is not allowed"),
    OPERATION_MENU_PERMISSION_CATALOG_ERROR(401012,
            "After operation, the menu type is directory, the parent menu must be the default top-level menu or a directory"),
    OPERATION_MENU_PERMISSION_MENU_ERROR(401013,
            "After operation, the menu type is menu, the parent menu must be of directory type"),
    OPERATION_MENU_PERMISSION_BTN_ERROR(401013,
            "After operation, the menu type is button, the parent menu must be of menu type"),
    OPERATION_MENU_PERMISSION_URL_NOT_NULL(401015, "The URL of the menu permission cannot be empty"),
    OPERATION_MENU_PERMISSION_URL_PERMS_NULL(401016, "The identifier of the menu permission cannot be empty"),

    USER_EXIXT(401017, "Username already exists!"),
    ILLEGAL_REQUEST(401018, "Illegal request!"),

    DISHED_NOT_EXIXT(401030, "Dish does not exist!"),

    ;

    /**
     * Error code
     */
    private final int code;
    /**
     * Error message
     */
    private final String msg;

    BaseResponseCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
