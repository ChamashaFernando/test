package lk.zerocode.channelling.center.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class DoctoreRequest {

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @NotBlank(message = "Specialty cannot be empty")
    private String specialty;

    @NotNull(message = "Fee cannot be null")
    @Positive(message = "Fee must be positive")
    private double fee;
}
