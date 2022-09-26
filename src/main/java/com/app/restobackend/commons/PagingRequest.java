package com.app.restobackend.commons;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import java.util.Map;

@Data
public class PagingRequest {
    private int page = 0;
    private int length = 10;
    private Map<String, FilterObject> dtSearch;

    @Data
    public static class FilterObject {
        private String value;
        private FilterObjectMode matchMode;
        private FilterObjectType type;

        public void setMatchMode(String v) {
            this.matchMode = FilterObjectMode.valueOf(v.toUpperCase());
        }

        public void setType(String v) {
            this.type = FilterObjectType.valueOf(v.toUpperCase());
        }
    }

    public enum FilterObjectMode {
        CONTAINS("contains"),
        EQUALS("equals");

        private String mode;

        FilterObjectMode(String mode) {
            this.mode = mode;
        }
    }

    public enum FilterObjectType {
        STRING("string"),
        NUMERIC("numeric");

        private String type;

        FilterObjectType(String type) {
            this.type = type;
        }

        private String getType() {
            return this.type;
        }
    }
}
