package lk.zerocode.channelling.center.controller.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalTime;

@Data
public class DoctorClinicHoursRequest {
    @NotNull
    private LocalTime startTime;
    @NotNull
    private LocalTime endTime;
}