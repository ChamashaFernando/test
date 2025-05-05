package lk.zerocode.channelling.center.service.impl;

import jakarta.validation.Valid;
import lk.zerocode.channelling.center.controller.request.AdminLoginRequest;
import lk.zerocode.channelling.center.exception.InvalidRequestException;
import lk.zerocode.channelling.center.model.Admin;
import lk.zerocode.channelling.center.repository.AdminRepository;
import lk.zerocode.channelling.center.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {

//    private AdminRepository adminRepository;
//
//    private PasswordEncoder passwordEncoder;
//
//
//    public Admin loginAdmin(@Valid AdminLoginRequest request)throws InvalidRequestException {
//        Admin admin = adminRepository.findByUsername(request.getUsername());
//        if (admin == null || !passwordEncoder.matches(request.getPassword(), admin.getPassword())) {
//            throw new InvalidRequestException("Invalid username or password");
//        }
//        return admin;
//    }

}
