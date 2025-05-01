package lk.zerocode.channelling.center.repository;

import lk.zerocode.channelling.center.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByDoctorIdAndStatus(Long doctorId, String status);

    List<Booking> findByDoctorIdAndAppointmentTimeBetween(Long doctorId, LocalDateTime start, LocalDateTime end);
}
