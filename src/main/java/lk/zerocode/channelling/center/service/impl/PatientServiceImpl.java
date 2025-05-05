package lk.zerocode.channelling.center.service.impl;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lk.zerocode.channelling.center.controller.request.PatientRequest;
import lk.zerocode.channelling.center.controller.response.BookingIdResponse;
import lk.zerocode.channelling.center.controller.response.BookingNumberResponse;
import lk.zerocode.channelling.center.controller.response.BookingResponse;
import lk.zerocode.channelling.center.controller.response.PatientResponse;
import lk.zerocode.channelling.center.exception.*;
import lk.zerocode.channelling.center.model.Booking;
import lk.zerocode.channelling.center.model.Patient;
import lk.zerocode.channelling.center.repository.BookingRepository;
import lk.zerocode.channelling.center.repository.PatientRepository;
import lk.zerocode.channelling.center.service.PatientService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PatientServiceImpl implements PatientService {

    private PatientRepository patientRepository;

    private BookingRepository bookingRepository;

    @Override
    @Transactional
    public PatientResponse createPatient(@Valid PatientRequest request) throws PatientNotCreatedException {
        if (patientRepository.existsByEmail(request.getEmail())) {
            throw new PatientNotCreatedException("Patient with email " + request.getEmail() + " already exists");
        }

        Patient patient = new Patient();
        patient.setName(request.getName().trim());
        patient.setEmail(request.getEmail().trim());
        patient.setContact(request.getContact().trim());
        Patient savedPatient = patientRepository.save(patient);

        PatientResponse response = new PatientResponse();
        response.setId(savedPatient.getId());
        response.setName(patient.getName());
        response.setEmail(patient.getEmail());
        response.setContact(patient.getContact());
        return response;
    }

    @Override
    @Transactional
    public void cancelBookingByPatientId(Long bookingId, Long patientId) throws BookingNotFoundException, BusinessLogicException{

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found with ID: " + bookingId));
        if (!booking.getPatient().getId().equals(patientId)) {
            throw new BusinessLogicException("Booking does not belong to patient ID: " + patientId);
        }
        if ("CANCELLED".equals(booking.getStatus())) {
            throw new BusinessLogicException("Booking is already cancelled");
        }
        booking.setStatus("CANCELLED");
        bookingRepository.save(booking);
    }

    @Override
    public List<BookingResponse> getPatientAppointments(Long patientId)throws PatientNotFoundException{

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found with ID: " + patientId));

        return patient.getBookings().stream()
                .filter(booking -> "CONFIRMED".equals(booking.getStatus()))
                .map(booking -> {
                    BookingResponse response = new BookingResponse();
                    response.setId(booking.getId());
                    response.setPatientName(booking.getPatient().getName());
                    response.setDoctorName(booking.getDoctor().getName());
                    response.setFee(booking.getDoctor().getFee());
                    response.setAppointmentTime(booking.getAppointmentTime());
                    response.setStatus(booking.getStatus());
                    return response;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingIdResponse> getPatientBookingIds(Long patientId) throws PatientNotFoundException{
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found with ID: " + patientId));

        return patient.getBookings().stream()
                .filter(booking -> "CONFIRMED".equals(booking.getStatus()))
                .map(booking -> {
                    BookingIdResponse response = new BookingIdResponse();
                    response.setId(booking.getId());
                    return response;
                })
                .collect(Collectors.toList());
    }

//*****Meka mokakda kiyala balanna ahala****
    @Override
    public BookingNumberResponse getPatientLatestBookingNumber(Long patientId) throws PatientNotFoundException, BusinessLogicException{

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found with ID: " + patientId));

        Booking latestBooking = patient.getBookings().stream()
                .filter(booking -> "CONFIRMED".equals(booking.getStatus()))
                .max(Comparator.comparing(Booking::getAppointmentTime))
                .orElseThrow(() -> new BusinessLogicException("No confirmed bookings found for patient ID: " + patientId));

        BookingNumberResponse response = new BookingNumberResponse();
        response.setBookingId(latestBooking.getId());
        response.setFee(latestBooking.getDoctor().getFee());

        return response;
    }

}

//    @Override
//    public List<PatientResponse> getPatientByBookingId(Long bookingId) {
//        return patientRepository.findByBooking_Id(bookingId);
//
////        Patient patient = patientRepository.findById(patientId)
////                .orElseThrow(() -> new PatientNotFoundException("Patient not found with ID: " + patientId));
////
////        return patient.getBookings().stream()
////                .filter(booking -> "CONFIRMED".equals(booking.getStatus()))
////                .map(booking -> {
////                    BookingIdResponse response = new BookingIdResponse();
////                    response.setId(booking.getId());
////                    return response;
////                })
////                .collect(Collectors.toList());
//    }



