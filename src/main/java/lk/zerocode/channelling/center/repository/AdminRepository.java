package lk.zerocode.channelling.center.repository;

import lk.zerocode.channelling.center.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin findByUsername(String username);
}
