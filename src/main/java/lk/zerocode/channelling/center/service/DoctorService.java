package lk.zerocode.channelling.center.service;

import jakarta.validation.Valid;
import lk.zerocode.channelling.center.controller.request.DoctoreRequest;
import lk.zerocode.channelling.center.controller.response.DoctorResponse;
import lk.zerocode.channelling.center.exception.DoctorNotCreatedException;
import lk.zerocode.channelling.center.exception.DoctorNotFoundException;

import java.util.List;

public interface DoctorService {

    DoctorResponse addDoctor(DoctoreRequest request) throws DoctorNotCreatedException;
    List<DoctorResponse> getDoctors();
    DoctorResponse updateDoctor(Long id, @Valid DoctoreRequest request) throws DoctorNotFoundException;
    void deleteDoctor(Long id)throws DoctorNotFoundException;


}
