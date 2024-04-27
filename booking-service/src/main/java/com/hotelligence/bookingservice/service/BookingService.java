package com.hotelligence.bookingservice.service;

import com.hotelligence.bookingservice.dto.BookingLineItemsDto;
import com.hotelligence.bookingservice.dto.BookingRequest;
import com.hotelligence.bookingservice.model.Booking;
import com.hotelligence.bookingservice.model.BookingLineItems;
import com.hotelligence.bookingservice.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;

    public void placeBooking(BookingRequest bookingRequest){
        Booking booking = new Booking();
        booking.setBookingNumber(UUID.randomUUID().toString());

        List<BookingLineItems> bookingLineItems = bookingRequest.getBookingLineItemsDtoList()
                .stream()
                .map(this::mapToDto)
                .toList();

        booking.setBookingLineItemsList(bookingLineItems);

        bookingRepository.save(booking);
    }

    private BookingLineItems mapToDto(BookingLineItemsDto bookingLineItemsDto) {
        BookingLineItems bookingLineItems = new BookingLineItems();
        bookingLineItems.setCheckinDate(bookingLineItemsDto.getCheckinDate());
        bookingLineItems.setCheckoutDate(bookingLineItemsDto.getCheckoutDate());
        bookingLineItems.setNightlyPrice(bookingLineItemsDto.getNightlyPrice());
        bookingLineItems.setNumOfNights(bookingLineItemsDto.getNumOfNights());
        bookingLineItems.setTax(bookingLineItemsDto.getTax());
        bookingLineItems.setExtraFee(bookingLineItemsDto.getExtraFee());
        bookingLineItems.setTotalPrice(bookingLineItemsDto.getTotalPrice());
        bookingLineItems.setBookingStatus(bookingLineItemsDto.getBookingStatus());
        return bookingLineItems;
    }

}
