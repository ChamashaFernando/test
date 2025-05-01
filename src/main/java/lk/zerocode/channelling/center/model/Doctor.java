package lk.zerocode.channelling.center.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "doctors")
@Data
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String specialty;

    @Column(nullable = false)
    private Double fee;

    @Column(nullable = false)
    private Boolean isActive;

    @OneToMany(mappedBy = "doctor")
    private List<Booking> bookings;
}
