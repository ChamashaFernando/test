package lk.zerocode.channelling.center.service.impl;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lk.zerocode.channelling.center.controller.request.PatientRequest;
import lk.zerocode.channelling.center.controller.response.BookingResponse;
import lk.zerocode.channelling.center.controller.response.PatientResponse;
import lk.zerocode.channelling.center.exception.PatientNotCreatedException;
import lk.zerocode.channelling.center.exception.PatientNotFoundException;
import lk.zerocode.channelling.center.model.Patient;
import lk.zerocode.channelling.center.repository.PatientRepository;
import lk.zerocode.channelling.center.service.PatientService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PatientServiceImpl implements PatientService {

    private PatientRepository patientRepository;

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



