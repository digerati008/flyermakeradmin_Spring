package com.project.flyermakeradmin.response;

import lombok.Data;

@Data
public class DeleteResponse {
    private String message;

    public DeleteResponse(String message) {
        this.message = message;
    }
}
