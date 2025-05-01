package lk.zerocode.channelling.center.repository;

import lk.zerocode.channelling.center.model.DoctorSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DoctorScheduleRepository extends JpaRepository<DoctorSchedule, Long> {
    Optional<DoctorSchedule> findByDoctorId(Long doctorId);
}
