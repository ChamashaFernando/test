package lk.zerocode.channelling.center.controller;

import jakarta.validation.Valid;
import lk.zerocode.channelling.center.controller.request.PatientIdRequest;
import lk.zerocode.channelling.center.controller.request.PatientRequest;
import lk.zerocode.channelling.center.controller.response.BookingIdResponse;
import lk.zerocode.channelling.center.controller.response.BookingNumberResponse;
import lk.zerocode.channelling.center.controller.response.BookingResponse;
import lk.zerocode.channelling.center.controller.response.PatientResponse;
import lk.zerocode.channelling.center.exception.BookingNotFoundException;
import lk.zerocode.channelling.center.exception.BusinessLogicException;
import lk.zerocode.channelling.center.exception.PatientNotCreatedException;
import lk.zerocode.channelling.center.exception.PatientNotFoundException;
import lk.zerocode.channelling.center.service.PatientService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class PatientController {

    @Autowired
    private PatientService patientService;

    @PostMapping("/patients")
    public ResponseEntity<PatientResponse> createPatient(@RequestBody @Valid PatientRequest request)throws PatientNotCreatedException {
        return ResponseEntity.ok(patientService.createPatient(request));
    }

    @GetMapping("/patient/{patient-id}/appointments")
    public ResponseEntity<List<BookingResponse>> getPatientAppointments(@PathVariable Long id) throws PatientNotFoundException {
        return ResponseEntity.ok(patientService.getPatientAppointments(id));
    }
    @GetMapping("/patient/{patient-id}/booking-ids")
    public ResponseEntity<List<BookingIdResponse>> getPatientBookingIds(@PathVariable Long id) throws PatientNotFoundException{
        return ResponseEntity.ok(patientService.getPatientBookingIds(id));
    }

    @GetMapping("/patient/{patient-id}/booking-number")
    public ResponseEntity<BookingNumberResponse> getPatientLatestBookingNumber(@PathVariable Long id) throws PatientNotFoundException, BusinessLogicException {
        return ResponseEntity.ok(patientService.getPatientLatestBookingNumber(id));
    }

    @DeleteMapping("/patient/booking/{id}/cancel")
    public ResponseEntity<Void> cancelPatientByBookingId(@PathVariable Long id, @RequestBody @Valid PatientIdRequest request) throws BookingNotFoundException,BusinessLogicException {
        patientService.cancelBookingByPatientId(id, request.getPatientId());
        return ResponseEntity.ok().build();
    }




}
