package com.app.restobackend.commons;

import lombok.Data;

import java.util.List;

public class CommonRequest {

    @Data
    public static class BulkDelete {
        private List<Long> ids;
    }

}
