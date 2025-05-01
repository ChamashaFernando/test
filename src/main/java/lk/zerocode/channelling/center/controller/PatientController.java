package lk.zerocode.channelling.center.controller;

import jakarta.validation.Valid;
import lk.zerocode.channelling.center.controller.request.PatientRequest;
import lk.zerocode.channelling.center.controller.response.PatientResponse;
import lk.zerocode.channelling.center.exception.PatientNotCreatedException;
import lk.zerocode.channelling.center.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PatientController {

    @Autowired
    private PatientService patientService;

    @PostMapping("/patients")
    public ResponseEntity<PatientResponse> createPatient(@RequestBody @Valid PatientRequest request)throws PatientNotCreatedException {
        return ResponseEntity.ok(patientService.createPatient(request));
    }

}
