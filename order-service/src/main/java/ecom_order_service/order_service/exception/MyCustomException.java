package ecom_order_service.order_service.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;

public class MyCustomException extends RuntimeException {

    private final HttpStatusCode statusCode;
    private final HttpHeaders headers;

    public MyCustomException(HttpStatusCode statusCode, HttpHeaders headers) {
        super("Inventory Service returned error: " + statusCode);
        this.statusCode = statusCode;
        this.headers = headers;
    }

    public HttpStatusCode getStatusCode() {
        return statusCode;
    }

    public HttpHeaders getHeaders() {
        return headers;
    }
}