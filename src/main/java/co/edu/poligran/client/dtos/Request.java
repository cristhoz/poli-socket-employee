package co.edu.poligran.client.dtos;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Request<T> {
    private String requestType;
    private T data;
}
