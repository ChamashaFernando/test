package lk.zerocode.channelling.center.controller.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PatientIdRequest {
    @NotNull
    private Long patientId;
}
