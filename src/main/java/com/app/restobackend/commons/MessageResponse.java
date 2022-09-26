package com.app.restobackend.commons;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageResponse<T> {

    private String message;
    private int code;
    private T data;
    private boolean success;

    /**
     * Success response
     *
     * @param data
     * @param message
     */
    public MessageResponse(T data, String message) {
        this.data = data;
        this.message = message;
        this.code = 200;
        this.success = true;
    }

    /**
     * Success response
     *
     * @param data
     */
    public MessageResponse(T data) {
        this.data = data;
        this.code = 200;
        this.success = true;
    }

    /**
     * Error response
     *
     * @param message
     */
    public MessageResponse(String message) {
        this.success = false;
    }
}
