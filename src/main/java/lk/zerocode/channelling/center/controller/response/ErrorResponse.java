package lk.zerocode.channelling.center.controller.response;

import lombok.Data;

import java.util.List;

@Data
public class ErrorResponse {
    private String error;
    private List<String> details;
}
