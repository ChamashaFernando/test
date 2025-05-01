package lk.zerocode.channelling.center.service.impl;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lk.zerocode.channelling.center.controller.request.DoctoreRequest;
import lk.zerocode.channelling.center.controller.response.DoctorResponse;
import lk.zerocode.channelling.center.exception.DoctorNotCreatedException;
import lk.zerocode.channelling.center.exception.DoctorNotFoundException;
import lk.zerocode.channelling.center.model.Doctor;
import lk.zerocode.channelling.center.repository.BookingRepository;
import lk.zerocode.channelling.center.repository.DoctorRepository;
import lk.zerocode.channelling.center.service.DoctorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private DoctorRepository doctorRepository;

    private BookingRepository bookingRepository;

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
        return response;
    }
    @Override
    @Transactional
    public void deleteDoctor(Long id) throws DoctorNotFoundException {

        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor not found with ID: " + id));

        doctorRepository.delete(doctor);
    }

}
