package lk.zerocode.channelling.center.service;

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

import java.time.LocalDate;
import java.util.List;

public interface DoctorService {

    DoctorResponse addDoctor(DoctoreRequest request) throws DoctorNotCreatedException;
    List<DoctorResponse> getDoctors();
    DoctorResponse updateDoctor(Long id, @Valid DoctoreRequest request) throws DoctorNotFoundException;
    void deleteDoctor(Long id)throws DoctorNotFoundException, BusinessLogicException;
    void deleteDoctorAccount(Long id) throws DoctorNotFoundException,BusinessLogicException;
    DoctorResponse updateDoctorStatus(Long id, @Valid DoctorStatusRequest request) throws DoctorNotFoundException;
    SimpleAvailabilityResponse checkDoctorSimpleAvailability(Long doctorId, LocalDate date) throws InvalidRequestException, DoctorNotFoundException;
    DoctorClinicHoursResponse getDoctorClinicHours(Long doctorId, LocalDate date) throws InvalidRequestException, DoctorNotFoundException;
    DoctorClinicHoursResponse updateDoctorClinicHours(Long doctorId, @Valid DoctorClinicHoursRequest request) throws InvalidRequestException, DoctorNotFoundException;
    DoctorClinicHoursResponse updateDoctorOwnClinicHours(Long doctorId, @Valid DoctorClinicHoursRequest request) throws InvalidRequestException, DoctorNotFoundException;


}
