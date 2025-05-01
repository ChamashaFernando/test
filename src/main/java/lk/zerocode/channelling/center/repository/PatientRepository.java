package lk.zerocode.channelling.center.repository;

import lk.zerocode.channelling.center.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PatientRepository extends JpaRepository<Patient, Long> {
    boolean existsByEmail(String email);
}
