package lk.zerocode.channelling.center.service;

import jakarta.validation.Valid;
import lk.zerocode.channelling.center.controller.request.PatientRequest;
import lk.zerocode.channelling.center.controller.response.BookingIdResponse;
import lk.zerocode.channelling.center.controller.response.BookingNumberResponse;
import lk.zerocode.channelling.center.controller.response.BookingResponse;
import lk.zerocode.channelling.center.controller.response.PatientResponse;
import lk.zerocode.channelling.center.exception.BookingNotFoundException;
import lk.zerocode.channelling.center.exception.BusinessLogicException;
import lk.zerocode.channelling.center.exception.PatientNotCreatedException;
import lk.zerocode.channelling.center.exception.PatientNotFoundException;

import java.util.List;

public interface PatientService {
    PatientResponse createPatient(@Valid PatientRequest request) throws PatientNotCreatedException;
    void cancelBookingByPatientId(Long bookingId, Long patientId) throws BookingNotFoundException, BusinessLogicException;
    List<BookingResponse> getPatientAppointments(Long patientId)throws PatientNotFoundException;
    List<BookingIdResponse> getPatientBookingIds(Long patientId) throws PatientNotFoundException;
    BookingNumberResponse getPatientLatestBookingNumber(Long patientId) throws PatientNotFoundException, BusinessLogicException;



    }


