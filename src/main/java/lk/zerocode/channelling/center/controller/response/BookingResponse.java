package lk.zerocode.channelling.center.controller.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookingResponse {
    private Long id;
    private String patientName;
    private String doctorName;
    private double fee;
    private LocalDateTime appointmentTime;
    private String status;
}
