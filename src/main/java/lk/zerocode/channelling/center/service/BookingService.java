package lk.zerocode.channelling.center.service;

import jakarta.validation.Valid;
import lk.zerocode.channelling.center.controller.request.BookingRequest;
import lk.zerocode.channelling.center.controller.response.BookingResponse;
import lk.zerocode.channelling.center.exception.BookingNotCreatedException;
import lk.zerocode.channelling.center.exception.BookingNotFoundException;
import lk.zerocode.channelling.center.exception.DoctorNotFoundException;
import lk.zerocode.channelling.center.exception.PatientNotFoundException;

import java.util.List;

public interface BookingService {
    BookingResponse createBooking(@Valid BookingRequest request) throws PatientNotFoundException, DoctorNotFoundException, BookingNotCreatedException, BookingNotFoundException;
    public List<BookingResponse> getBookings();
    public void cancelBooking(Long id) throws BookingNotFoundException;
}
