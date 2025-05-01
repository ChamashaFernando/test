package lk.zerocode.channelling.center.controller.response;

import lombok.Data;
import java.time.LocalTime;

@Data
public class DoctorClinicHoursResponse {
    private Long doctorId;
    private LocalTime startTime;
    private LocalTime endTime;
}