package com.garena.devalert.library.enabled;

/**
 * @author amulya
 * @since 03 Feb 2017, 1:24 PM.
 */

class DevAlertData {

    private final int type;
    private final String tag;
    private final String message;
    private final Throwable throwable;

    DevAlertData(int type, String tag, String message, Throwable throwable) {
        this.type = type;
        this.tag = tag;
        this.message = message;
        this.throwable = throwable;
    }

    int getType() {
        return type;
    }

    String getTag() {
        return tag;
    }

    String getMessage() {
        return message;
    }

    Throwable getThrowable() {
        return throwable;
    }
}
