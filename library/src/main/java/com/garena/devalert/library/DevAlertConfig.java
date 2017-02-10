package com.garena.devalert.library;

import java.util.ArrayList;
import java.util.List;

/**
 * @author amulya
 * @since 03 Feb 2017, 1:17 PM.
 */

public class DevAlertConfig {

    private boolean showErrors;
    private boolean showWarnings;
    private List<String> ignoredTags;
    private String sharedPrefName;

    private DevAlertConfig(Builder builder) {
        showErrors = builder.showErrors;
        showWarnings = builder.showWarnings;
        ignoredTags = builder.ignoredTags;
        sharedPrefName = builder.sharedPrefName;
    }

    public boolean isShowErrors() {
        return showErrors;
    }

    public boolean isShowWarnings() {
        return showWarnings;
    }

    public List<String> getIgnoredTags() {
        return ignoredTags;
    }

    public String getSharedPrefName() {
        return sharedPrefName;
    }

    public static final class Builder {
        private boolean showErrors;
        private boolean showWarnings;
        private List<String> ignoredTags;
        private String sharedPrefName;

        public Builder() {
            showErrors = true;
            showWarnings = true;
            ignoredTags = new ArrayList<>();
            sharedPrefName = "com_garena_dev_alert_lib";
        }

        public Builder showErrors(boolean show) {
            this.showErrors = show;
            return this;
        }

        public Builder showWarnings(boolean show) {
            this.showWarnings = show;
            return this;
        }

        public Builder ignoredTags(List<String> ignoredTags) {
            this.ignoredTags = ignoredTags;
            return this;
        }

        public Builder sharedPrefName(String name) {
            this.sharedPrefName = name;
            return this;
        }

        public DevAlertConfig build() {
            return new DevAlertConfig(this);
        }
    }
}
