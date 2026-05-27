package com.utiitsl.DMSAuthService.constants;//package com.SprinSecurityJwtAuthService.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public enum Permission {

    APPLICATION_ADMIN_READ("supremeAdmin:read"),
    APPLICATION_ADMIN_CREATE("supremeAdmin:create"),
    APPLICATION_ADMIN_UPDATE("supremeAdmin:update"),
    APPLICATION_ADMIN_DELETE("supremeAdmin:delete"),

    SUPREMEADMIN_READ("supremeAdmin:read"),
    SUPREMEADMIN_CREATE("supremeAdmin:create"),
    SUPREMEADMIN_UPDATE("supremeAdmin:update"),
    SUPREMEADMIN_DELETE("supremeAdmin:delete"),

    SUPERADMIN_READ("superAdmin:read"),
    SUPERADMIN_CREATE("superAdmin:create"),
    SUPERADMIN_UPDATE("superAdmin:update"),
    SUPERADMIN_DELETE("superAdmin:delete"),


    ADMIN_READ("admin:read"),
    ADMIN_CREATE("admin:create"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_DELETE("admin:delete"),

    DATAHANDLER_READ("datahandler:read"),

    APPROVER_READ("approver:read"),
    APPROVER_UPDATE("approver:update"),
    DATAHANDLER_CREATE("admin:create"),
    DATAHANDLER_UPDATE("admin:update"),
    DATAHANDLER_DELETE("admin:delete"),

    USER_READ("user:read"),
    USER_CREATE("user:create"),
    USER_UPDATE("user:update"),
    USER_DELETE("user:delete"),

    ;

    @Getter
    private final String permission;
}
