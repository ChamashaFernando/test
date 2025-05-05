package lk.zerocode.channelling.center.controller;

import jakarta.validation.Valid;
import lk.zerocode.channelling.center.controller.request.DoctorClinicHoursRequest;
import lk.zerocode.channelling.center.controller.request.DoctorStatusRequest;
import lk.zerocode.channelling.center.controller.request.DoctoreRequest;
import lk.zerocode.channelling.center.controller.response.DoctorClinicHoursResponse;
import lk.zerocode.channelling.center.controller.response.DoctorResponse;
import lk.zerocode.channelling.center.controller.response.SimpleAvailabilityResponse;
import lk.zerocode.channelling.center.exception.BusinessLogicException;
import lk.zerocode.channelling.center.exception.DoctorNotCreatedException;
import lk.zerocode.channelling.center.exception.DoctorNotFoundException;
import lk.zerocode.channelling.center.exception.InvalidRequestException;
import lk.zerocode.channelling.center.service.DoctorService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
        try {
            doctorService.deleteDoctor(id);
        } catch (lk.zerocode.channelling.center.exception.BusinessLogicException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/doctor/{id}/is-available")
    public ResponseEntity<SimpleAvailabilityResponse> checkDoctorSimpleAvailability(@PathVariable Long id, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date)throws InvalidRequestException, DoctorNotFoundException {
        return ResponseEntity.ok(doctorService.checkDoctorSimpleAvailability(id, date));
    }

    @GetMapping("/doctor/{id}/clinic-hours")
    public ResponseEntity<DoctorClinicHoursResponse> getDoctorClinicHours(@PathVariable Long id, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date)throws InvalidRequestException, DoctorNotFoundException {
        return ResponseEntity.ok(doctorService.getDoctorClinicHours(id, date));
    }

    @PutMapping("/doctor/{id}/clinic-hours")
    public ResponseEntity<DoctorClinicHoursResponse> updateDoctorOwnClinicHours(@PathVariable Long id, @RequestBody @Valid DoctorClinicHoursRequest request) throws InvalidRequestException, DoctorNotFoundException{
        return ResponseEntity.ok(doctorService.updateDoctorOwnClinicHours(id, request));

    }

    @DeleteMapping("/doctor/{id}/account")
    public ResponseEntity<Void> deleteDoctorAccount(@PathVariable Long id) throws DoctorNotFoundException, BusinessLogicException {
        doctorService.deleteDoctorAccount(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/admin/doctor/{id}/status")
    public ResponseEntity<DoctorResponse> updateDoctorStatus(@PathVariable Long id, @RequestBody @Valid DoctorStatusRequest request) throws DoctorNotFoundException{
        return ResponseEntity.ok(doctorService.updateDoctorStatus(id, request));
    }

    @PutMapping("/admin/doctor/{id}/clinic-hours")
    public ResponseEntity<DoctorClinicHoursResponse> updateDoctorClinicHours(@PathVariable Long id, @RequestBody @Valid DoctorClinicHoursRequest request)throws InvalidRequestException,DoctorNotFoundException {
        return ResponseEntity.ok(doctorService.updateDoctorClinicHours(id, request));
    }







}
