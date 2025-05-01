package lk.zerocode.channelling.center.controller;

import jakarta.validation.Valid;
import lk.zerocode.channelling.center.controller.request.BookingRequest;
import lk.zerocode.channelling.center.controller.response.BookingResponse;
import lk.zerocode.channelling.center.exception.BookingNotCreatedException;
import lk.zerocode.channelling.center.exception.BookingNotFoundException;
import lk.zerocode.channelling.center.exception.DoctorNotFoundException;
import lk.zerocode.channelling.center.exception.PatientNotFoundException;
import lk.zerocode.channelling.center.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/bookings")
    public ResponseEntity<BookingResponse> createBooking(@RequestBody @Valid BookingRequest request) throws PatientNotFoundException, DoctorNotFoundException, BookingNotCreatedException, BookingNotFoundException {
        return ResponseEntity.ok(bookingService.createBooking(request));
    }

    @GetMapping("/bookings")
    public ResponseEntity<List<BookingResponse>> getBookings() {
        return ResponseEntity.ok(bookingService.getBookings());
    }

    @DeleteMapping("/bookings/{Patient_id}")
    public ResponseEntity<Void> cancelBooking(@PathVariable ("Patient_id") Long id) throws BookingNotFoundException{
        bookingService.cancelBooking(id);
        return ResponseEntity.ok().build();

//    @PostMapping("/bookings/{id}")
//    public ResponseEntity<Void> cancelBooking(@PathVariable Long id) throws BookingNotFoundException{
//        bookingService.cancelBooking(id);
//        return ResponseEntity.ok().build();
    }
}
