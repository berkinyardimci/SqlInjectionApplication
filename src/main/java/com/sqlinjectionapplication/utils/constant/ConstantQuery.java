package com.sqlinjectionapplication.utils.constant;

public class ConstantQuery {
    public static final String FIND_USER_BY_USERNAME_PASSWORD_SECURE =
            "select id, username, password " +
                    "from users u " +
                    "where u.username = ? " +
                    "and u.password = ?";

    public static final String FIND_USER_BY_USERNAME_PASSWORD =
            "select id, username, password " +
                    "from users u " +
                    "where u.username = '%s' " +
                    "and u.password = '%s'";
}
