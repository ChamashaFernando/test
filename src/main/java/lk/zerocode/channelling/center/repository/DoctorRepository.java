package lk.zerocode.channelling.center.repository;

import lk.zerocode.channelling.center.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    boolean existsByNameAndSpecialty(String name, String specialty);
}
