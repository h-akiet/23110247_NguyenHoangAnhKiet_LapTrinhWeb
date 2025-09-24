package vn.iotstar.Model;

import org.springframework.data.domain.Page;

public class Response<T> {
    private boolean status;
    private String message;
    private T body;

    // Constructors
    public Response() {}

    public Response(boolean status, String message, T body) {
        this.status = status;
        this.message = message;
        this.body = body;
    }

    // Getters và Setters
    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }
}

