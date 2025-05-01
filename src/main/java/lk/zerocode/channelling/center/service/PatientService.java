package lk.zerocode.channelling.center.service;

import jakarta.validation.Valid;
import lk.zerocode.channelling.center.controller.request.PatientRequest;
import lk.zerocode.channelling.center.controller.response.BookingResponse;
import lk.zerocode.channelling.center.controller.response.PatientResponse;
import lk.zerocode.channelling.center.exception.PatientNotCreatedException;

import java.util.List;

public interface PatientService {
    PatientResponse createPatient(@Valid PatientRequest request) throws PatientNotCreatedException;


}


