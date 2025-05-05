package lk.zerocode.channelling.center.service.impl;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lk.zerocode.channelling.center.controller.request.BookingRequest;
import lk.zerocode.channelling.center.controller.response.BookingResponse;
import lk.zerocode.channelling.center.exception.*;
import lk.zerocode.channelling.center.model.Booking;
import lk.zerocode.channelling.center.model.Doctor;
import lk.zerocode.channelling.center.model.Patient;
import lk.zerocode.channelling.center.repository.BookingRepository;
import lk.zerocode.channelling.center.repository.DoctorRepository;
import lk.zerocode.channelling.center.repository.PatientRepository;
import lk.zerocode.channelling.center.service.BookingService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookingServiceImpl implements BookingService {

    private BookingRepository bookingRepository;

    private PatientRepository patientRepository;

    private DoctorRepository doctorRepository;


    @Override
    @Transactional
    public BookingResponse createBooking(@Valid BookingRequest request) throws PatientNotFoundException,DoctorNotFoundException,BookingNotCreatedException,BookingNotFoundException{
        if (request.getPatientId() == null || request.getPatientId() <= 0) {
            throw new PatientNotFoundException("Invalid patient ID: " + request.getPatientId());
        }
        if (request.getDoctorId() == null || request.getDoctorId() <= 0) {
            throw new DoctorNotFoundException("Invalid doctor ID: " + request.getDoctorId());
        }
        if (request.getAppointmentTime() == null || request.getAppointmentTime().isBefore(LocalDateTime.now())) {
            throw new BookingNotCreatedException("Invalid or past appointment time");
        }

        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new PatientNotFoundException("Patient not found with ID: " + request.getPatientId()));
        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new DoctorNotFoundException("Doctor not found with ID: " + request.getDoctorId()));

        // Check slot availability
        List<Booking> existingBookings = bookingRepository.findByDoctorIdAndAppointmentTimeBetween(
                request.getDoctorId(),
                request.getAppointmentTime().minusMinutes(15),
                request.getAppointmentTime().plusMinutes(15)
        );
        if (!existingBookings.isEmpty()) {
            throw new BookingNotFoundException("Slot not available for doctor at the requested time");
        }

        // Create booking
        Booking booking = new Booking();
        booking.setPatient(patient);
        booking.setDoctor(doctor);
        booking.setAppointmentTime(request.getAppointmentTime());
        booking.setStatus("CONFIRMED");
        bookingRepository.save(booking);

        // Prepare response
        BookingResponse response = new BookingResponse();
        response.setId(booking.getId());
        response.setPatientName(patient.getName());
        response.setDoctorName(doctor.getName());
        response.setFee(doctor.getFee());
        response.setAppointmentTime(booking.getAppointmentTime());
        response.setStatus(booking.getStatus());

        return response;
    }
//meke getbookings kiyalai getpatientappointments kiyalai dekk thiynw
    @Override
    public List<BookingResponse> getBookings() {
        return bookingRepository.findAll().stream().map(booking -> {
            BookingResponse response = new BookingResponse();
            response.setId(booking.getId());
            response.setPatientName(booking.getPatient().getName());
            response.setDoctorName(booking.getDoctor().getName());
            response.setFee(booking.getDoctor().getFee());
            response.setAppointmentTime(booking.getAppointmentTime());
            response.setStatus(booking.getStatus());
            return response;
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void cancelBooking(Long id) throws BookingNotFoundException, BusinessLogicException {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found with ID: " + id));
        if ("CANCELLED".equals(booking.getStatus())) {
            throw new BusinessLogicException("Booking is already cancelled");
        }
        booking.setStatus("CANCELLED");
        bookingRepository.save(booking);
    }


//    @Override
//    @Transactional
//    public void cancelBooking(Long id)throws BookingNotFoundException {
//
//        Booking booking = bookingRepository.findById(id)
//                .orElseThrow(() -> new BookingNotFoundException("Booking not found with ID: " + id));
//        if ("CANCELLED".equals(booking.getStatus())) {
//            throw new BookingNotFoundException("Booking is already cancelled");
//        }
//        booking.setStatus("CANCELLED");
//        bookingRepository.save(booking);
//    }



}
