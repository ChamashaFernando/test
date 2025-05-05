package lk.zerocode.channelling.center.service;

import jakarta.validation.Valid;
import lk.zerocode.channelling.center.controller.request.BookingRequest;
import lk.zerocode.channelling.center.controller.response.BookingResponse;
import lk.zerocode.channelling.center.exception.*;

import java.util.List;

public interface BookingService {
    BookingResponse createBooking(@Valid BookingRequest request) throws PatientNotFoundException, DoctorNotFoundException, BookingNotCreatedException, BookingNotFoundException;
    List<BookingResponse> getBookings();
    void cancelBooking(Long id) throws BookingNotFoundException, BusinessLogicException;

}
