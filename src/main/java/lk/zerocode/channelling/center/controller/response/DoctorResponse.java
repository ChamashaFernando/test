package lk.zerocode.channelling.center.controller.response;

import lombok.Data;

@Data
public class DoctorResponse {
    private Long id;
    private String name;
    private String specialty;
    private Double fee;
    private Integer activeBookings;
    private Boolean isActive;
}
