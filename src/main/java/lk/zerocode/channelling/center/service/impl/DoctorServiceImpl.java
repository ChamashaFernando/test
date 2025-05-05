package lk.zerocode.channelling.center.service.impl;

import jakarta.transaction.Transactional;
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
import lk.zerocode.channelling.center.model.Booking;
import lk.zerocode.channelling.center.model.Doctor;
import lk.zerocode.channelling.center.model.DoctorSchedule;
import lk.zerocode.channelling.center.repository.BookingRepository;
import lk.zerocode.channelling.center.repository.DoctorRepository;
import lk.zerocode.channelling.center.repository.DoctorScheduleRepository;
import lk.zerocode.channelling.center.service.DoctorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private DoctorRepository doctorRepository;

    private BookingRepository bookingRepository;

    private DoctorScheduleRepository doctorScheduleRepository;

    @Override
    @Transactional
    public DoctorResponse addDoctor(DoctoreRequest request) throws DoctorNotCreatedException {
        if (doctorRepository.existsByNameAndSpecialty(request.getName(), request.getSpecialty())) {
            throw new DoctorNotCreatedException("Doctor with name " + request.getName() + " and specialty " + request.getSpecialty() + " already exists");
        }

        Doctor doctor = new Doctor();
        doctor.setName(request.getName().trim());
        doctor.setSpecialty(request.getSpecialty().trim());
        doctor.setFee(request.getFee());
        doctor.setIsActive(true);
        Doctor savedDoctor = doctorRepository.save(doctor);

        DoctorResponse response = new DoctorResponse();
        response.setId(savedDoctor.getId());
        response.setName(doctor.getName());
        response.setSpecialty(doctor.getSpecialty());
        response.setFee(doctor.getFee());
        response.setActiveBookings(0);
        response.setIsActive(doctor.getIsActive());
        return response;
    }

    //**awl;ak thiyanav is active eke
    @Override
    public List<DoctorResponse> getDoctors() {
        return doctorRepository.findAll().stream().map(doctor -> {
            DoctorResponse response = new DoctorResponse();
            response.setId(doctor.getId());
            response.setName(doctor.getName());
            response.setSpecialty(doctor.getSpecialty());
            response.setFee(doctor.getFee());
            response.setActiveBookings(
                    bookingRepository.findByDoctorIdAndStatus(doctor.getId(), "CONFIRMED").size()
            );
            response.setIsActive(doctor.getIsActive());
            return response;
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public DoctorResponse updateDoctor(Long id, @Valid DoctoreRequest request) throws DoctorNotFoundException {

        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor not found with ID: " + id));
        doctor.setName(request.getName().trim());
        doctor.setSpecialty(request.getSpecialty().trim());
        doctor.setFee(request.getFee());
        doctorRepository.save(doctor);

        DoctorResponse response = new DoctorResponse();
        response.setId(doctor.getId());
        response.setName(doctor.getName());
        response.setSpecialty(doctor.getSpecialty());
        response.setFee(doctor.getFee());
        response.setActiveBookings(
                bookingRepository.findByDoctorIdAndStatus(doctor.getId(), "CONFIRMED").size()
        );
        response.setIsActive(doctor.getIsActive());
        return response;
    }

    @Override
    @Transactional
    public void deleteDoctor(Long id) throws DoctorNotFoundException, BusinessLogicException {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor not found with ID: " + id));

        List<Booking> activeBookings = bookingRepository.findByDoctorIdAndStatus(id, "CONFIRMED");
        if (!activeBookings.isEmpty()) {
            throw new BusinessLogicException("Cannot delete doctor with " + activeBookings.size() + " active bookings");
        }
        doctorRepository.delete(doctor);
    }

    @Override
    @Transactional
    public void deleteDoctorAccount(Long id) throws DoctorNotFoundException, BusinessLogicException {

        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor not found with ID: " + id));

        List<Booking> activeBookings = bookingRepository.findByDoctorIdAndStatus(id, "CONFIRMED");
        if (!activeBookings.isEmpty()) {
            throw new BusinessLogicException("Cannot delete doctor account with " + activeBookings.size() + " active bookings");
        }

        doctorRepository.delete(doctor);

    }

    @Override
    @Transactional
    public DoctorResponse updateDoctorStatus(Long id, @Valid DoctorStatusRequest request) throws DoctorNotFoundException{


        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor not found with ID: " + id));

        doctor.setIsActive(request.getIsActive());
        doctorRepository.save(doctor);

        DoctorResponse response = new DoctorResponse();
        response.setId(doctor.getId());
        response.setName(doctor.getName());
        response.setSpecialty(doctor.getSpecialty());
        response.setFee(doctor.getFee());
        response.setActiveBookings(
                bookingRepository.findByDoctorIdAndStatus(doctor.getId(), "CONFIRMED").size()
        );
        response.setIsActive(doctor.getIsActive());

        return response;
    }

    @Override
    public SimpleAvailabilityResponse checkDoctorSimpleAvailability(Long doctorId, LocalDate date) throws InvalidRequestException, DoctorNotFoundException{
        if (doctorId == null || doctorId <= 0) {
            throw new InvalidRequestException("Invalid doctor ID: " + doctorId);
        }
        if (date == null || date.isBefore(LocalDate.now())) {
            throw new InvalidRequestException("Invalid or past date: " + date);
        }

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor not found with ID: " + doctorId));

        SimpleAvailabilityResponse response = new SimpleAvailabilityResponse();
        response.setAvailable(doctor.getIsActive());
        return response;
    }

//    ***********meka enna ona patient class ektda***********

    @Override
    public DoctorClinicHoursResponse getDoctorClinicHours(Long doctorId, LocalDate date) throws InvalidRequestException, DoctorNotFoundException{
        if (doctorId == null || doctorId <= 0) {
            throw new InvalidRequestException("Invalid doctor ID: " + doctorId);
        }
        if (date == null || date.isBefore(LocalDate.now())) {
            throw new InvalidRequestException("Invalid or past date: " + date);
        }

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor not found with ID: " + doctorId));

        DoctorSchedule schedule = doctorScheduleRepository.findByDoctorId(doctorId)
                .orElse(new DoctorSchedule() {{
                    setStartTime(java.time.LocalTime.of(9, 0));
                    setEndTime(java.time.LocalTime.of(17, 0));
                }});

        DoctorClinicHoursResponse response = new DoctorClinicHoursResponse();
        response.setDoctorId(doctorId);
        response.setStartTime(schedule.getStartTime());
        response.setEndTime(schedule.getEndTime());

        return response;
    }

    @Override
    @Transactional
    public DoctorClinicHoursResponse updateDoctorClinicHours(Long doctorId, @Valid DoctorClinicHoursRequest request) throws InvalidRequestException, DoctorNotFoundException{
        if (doctorId == null || doctorId <= 0) {
            throw new InvalidRequestException("Invalid doctor ID: " + doctorId);
        }
        if (request.getStartTime().isAfter(request.getEndTime()) || request.getStartTime().equals(request.getEndTime())) {
            throw new InvalidRequestException("Start time must be before end time");
        }

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor not found with ID: " + doctorId));

        DoctorSchedule schedule = doctorScheduleRepository.findByDoctorId(doctorId)
                .orElse(new DoctorSchedule());
        schedule.setDoctor(doctor);
        schedule.setStartTime(request.getStartTime());
        schedule.setEndTime(request.getEndTime());
        DoctorSchedule savedSchedule = doctorScheduleRepository.save(schedule);

        DoctorClinicHoursResponse response = new DoctorClinicHoursResponse();
        response.setDoctorId(doctorId);
        response.setStartTime(savedSchedule.getStartTime());
        response.setEndTime(savedSchedule.getEndTime());

        return response;
    }

//******meken wenne mokakda balanna eka theren na mokada uda eketh ekemai wenne**
    @Override
    @Transactional
    public DoctorClinicHoursResponse updateDoctorOwnClinicHours(Long doctorId, @Valid DoctorClinicHoursRequest request) throws InvalidRequestException, DoctorNotFoundException {
        if (doctorId == null || doctorId <= 0) {
            throw new InvalidRequestException("Invalid doctor ID: " + doctorId);
        }
        if (request.getStartTime().isAfter(request.getEndTime()) || request.getStartTime().equals(request.getEndTime())) {
            throw new InvalidRequestException("Start time must be before end time");
        }

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor not found with ID: " + doctorId));

        DoctorSchedule schedule = doctorScheduleRepository.findByDoctorId(doctorId)
                .orElse(new DoctorSchedule());
        schedule.setDoctor(doctor);
        schedule.setStartTime(request.getStartTime());
        schedule.setEndTime(request.getEndTime());
        DoctorSchedule savedSchedule = doctorScheduleRepository.save(schedule);

        DoctorClinicHoursResponse response = new DoctorClinicHoursResponse();
        response.setDoctorId(doctorId);
        response.setStartTime(savedSchedule.getStartTime());
        response.setEndTime(savedSchedule.getEndTime());

        return response;
    }


}