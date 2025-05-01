package lk.zerocode.channelling.center.controller;

import jakarta.validation.Valid;
import lk.zerocode.channelling.center.controller.request.DoctoreRequest;
import lk.zerocode.channelling.center.controller.response.DoctorResponse;
import lk.zerocode.channelling.center.exception.DoctorNotCreatedException;
import lk.zerocode.channelling.center.exception.DoctorNotFoundException;
import lk.zerocode.channelling.center.service.DoctorService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @PostMapping("/doctors")
    public ResponseEntity<DoctorResponse> addDoctor(@RequestBody @Valid DoctoreRequest request)throws DoctorNotCreatedException {
        return ResponseEntity.ok(doctorService.addDoctor(request));
    }
    @GetMapping("/doctors")
    public ResponseEntity<List<DoctorResponse>> getDoctors() {
        return ResponseEntity.ok(doctorService.getDoctors());
    }
    @PutMapping("/admin/doctor/{id}")
    public ResponseEntity<DoctorResponse> updateDoctor(@PathVariable Long id, @RequestBody @Valid DoctoreRequest request) throws DoctorNotFoundException {
        return ResponseEntity.ok(doctorService.updateDoctor(id, request));
    }
    @DeleteMapping("/admin/doctor/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id) throws DoctorNotFoundException {
        doctorService.deleteDoctor(id);
        return ResponseEntity.ok().build();
    }



}
