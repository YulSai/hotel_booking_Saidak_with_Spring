package com.company.hotel_booking.web.controllers.view;

import com.company.hotel_booking.service.api.ReservationService;
import com.company.hotel_booking.service.api.UserService;
import com.company.hotel_booking.service.dto.ReservationDto;
import com.company.hotel_booking.service.dto.UserDto;
import com.company.hotel_booking.utils.DtoTest;
import com.company.hotel_booking.utils.constants.PagesConstants;
import com.company.hotel_booking.utils.exceptions.reservations.ReservationNotFoundException;
import com.company.hotel_booking.utils.exceptions.reservations.ReservationServiceException;
import com.company.hotel_booking.web.controllers.utils.PagingUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(controllers = ReservationController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@ExtendWith(MockitoExtension.class)
class ReservationControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ReservationService reservationService;
    @MockBean
    private UserService userService;
    @MockBean
    private PagingUtil pagingUtil;
    @MockBean
    private MessageSource messageSource;

    private ReservationDto reservationDto;

    @BeforeEach
    public void setup() {
        reservationDto = DtoTest.getExpectedReservationWithId();
    }


    @Test
    void whenRequestAllReservations_thenReturnCorrectView() throws Exception {
        List<ReservationDto> reservations = Collections.singletonList(reservationDto);
        Page<ReservationDto> pageReservationDto = new PageImpl<>(new ArrayList<>(reservations));
        when(reservationService.findAllPages(any())).thenReturn(pageReservationDto);

        this.mockMvc.perform(get("/reservations/all"))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        model().attribute("reservations", reservations),
                        view().name(PagesConstants.PAGE_RESERVATIONS),
                        forwardedUrl("/WEB-INF/jsp/forms/jstl/reservations/reservations.jsp"));

        verify(reservationService, times(1)).findAllPages(any());
        verifyNoMoreInteractions(reservationService);
    }

    @Test
    void whenRequestAllReservationsEmpty_thenReturnCorrectView() throws Exception {
        Page<ReservationDto> pageReservationDto = new PageImpl<>(new ArrayList<>());
        when(reservationService.findAllPages(any())).thenReturn(pageReservationDto);
        this.mockMvc.perform(get("/reservations/all"))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        model().attribute("message", messageSource.getMessage("msg.empty", null,
                                LocaleContextHolder.getLocale())),
                        view().name(PagesConstants.PAGE_RESERVATIONS),
                        forwardedUrl("/WEB-INF/jsp/forms/jstl/reservations/reservations.jsp"));

        verify(reservationService, times(1)).findAllPages(any());
        verifyNoMoreInteractions(reservationService);
    }

    @Test
    void whenRequestOneReservationById_thenReturnCorrectViewWithModelAttribute() throws Exception {
        when(reservationService.findById(reservationDto.getId())).thenReturn(reservationDto);
        this.mockMvc.perform(
                        get("/reservations/{id}", reservationDto.getId()))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        model().attribute("reservation", reservationDto),
                        view().name(PagesConstants.PAGE_RESERVATION),
                        forwardedUrl("/WEB-INF/jsp/forms/jstl/reservations/reservation.jsp"));

        verify(reservationService, times(1)).findById(reservationDto.getId());
        verifyNoMoreInteractions(reservationService);
    }

    @Test
    void whenRequestNonExistingReservation_thenReturnCorrectViewAndExceptionMessage() throws Exception {
        when(reservationService.findById(reservationDto.getId())).thenThrow(new ReservationNotFoundException(""));
        this.mockMvc.perform(get("/reservations/{id}", reservationDto.getId()))
                .andDo(print())
                .andExpectAll(
                        status().isNotFound(),
                        model().attributeExists("message"),
                        view().name(PagesConstants.PAGE_ERROR),
                        forwardedUrl("/WEB-INF/jsp/error.jsp"));
        verify(reservationService, times(1)).findById(reservationDto.getId());
        verifyNoMoreInteractions(reservationService);
    }

    @Test
    @WithMockUser
    void whenSendReservationCreationCorrect_thenReceiveCorrectDataAndDoRedirect() throws Exception {
        UserDto userDto = DtoTest.getExpectedUserWithId();
        when(userService.findByUsername("user")).thenReturn(userDto);

        String check_in = "2022-11-28";
        String check_out = "2022-11-30";
        Map<Long, Long> booking = DtoTest.getBooking();
        when(reservationService.processReservationCreation(booking, userDto, LocalDate.parse(check_in),
                LocalDate.parse(check_out))).thenReturn(reservationDto);

        this.mockMvc.perform(get("/reservations/create")
                        .sessionAttr("booking", booking)
                        .sessionAttr("check_in", check_in)
                        .sessionAttr("check_out", check_out)
                )
                .andDo(print())
                .andExpectAll(
                        status().is3xxRedirection(),
                        header().string("Location", "/reservations/" + reservationDto.getId()),
                        request().sessionAttribute("message", messageSource.getMessage("msg.reservation.created",
                                null, LocaleContextHolder.getLocale())),
                        request().sessionAttributeDoesNotExist("booking")
                );

        verify(reservationService, times(1)).processReservationCreation(booking, userDto,
                LocalDate.parse(check_in), LocalDate.parse(check_out));
        verifyNoMoreInteractions(reservationService);
    }

    @Test
    void whenRequestReservationUpdateForm_thenReturnCorrectView() throws Exception {
        when(reservationService.findById(reservationDto.getId())).thenReturn(reservationDto);
        this.mockMvc.perform(get("/reservations/update/{id}", reservationDto.getId()))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        model().attribute("reservation", reservationDto),
                        view().name(PagesConstants.PAGE_UPDATE_RESERVATION),
                        forwardedUrl("/WEB-INF/jsp/forms/jstl/reservations/updateReservationForm.jsp"));

        verify(reservationService, times(1)).findById(reservationDto.getId());
        verifyNoMoreInteractions(reservationService);
    }

    @Test
    void whenSendReservationUpdateFormCorrectData_thenReceiveCorrectDataAndDoRedirectWithMessage() throws Exception {
        when(reservationService.findById(reservationDto.getId())).thenReturn(reservationDto);
        when(reservationService.update(refEq(reservationDto))).thenReturn(reservationDto);
        this.mockMvc.perform(post("/reservations/update/{id}", reservationDto.getId())
                        .param("status", String.valueOf(reservationDto.getStatus()))
                )
                .andDo(print())
                .andExpectAll(
                        status().is3xxRedirection(),
                        header().string("Location", "/reservations/" + reservationDto.getId()),
                        request().sessionAttribute("message", messageSource.getMessage("msg.reservation.updated",
                                null, LocaleContextHolder.getLocale()))
                );

        verify(reservationService, times(1)).findById(reservationDto.getId());
        verify(reservationService, times(1)).update(refEq(reservationDto));
        verifyNoMoreInteractions(reservationService);
    }

    @Test
    void whenSendReservationUpdateFormCorrectDataButNull_thenReturnErrorPage() throws Exception {
        when(reservationService.findById(reservationDto.getId())).thenReturn(reservationDto);
        when(reservationService.update(refEq(reservationDto))).thenThrow(new ReservationServiceException(""));
        this.mockMvc.perform(post("/reservations/update/{id}", reservationDto.getId())
                        .param("status", String.valueOf(reservationDto.getStatus()))
                )
                .andDo(print())
                .andExpectAll(
                        status().isBadRequest(),
                        view().name(PagesConstants.PAGE_ERROR),
                        forwardedUrl("/WEB-INF/jsp/error.jsp")
                );

        verify(reservationService, times(1)).findById(reservationDto.getId());
        verify(reservationService, times(1)).update(refEq(reservationDto));
        verifyNoMoreInteractions(reservationService);
    }

    @Test
    void whenSendReservationUpdateFormCorrectDataAndRuntimeExceptionOccurs_thenReturnErrorPage() throws Exception {
        when(reservationService.findById(reservationDto.getId())).thenReturn(reservationDto);
        when(reservationService.update(refEq(reservationDto))).thenThrow(new RuntimeException(""));
        this.mockMvc.perform(post("/reservations/update/{id}", reservationDto.getId())
                        .param("status", String.valueOf(reservationDto.getStatus()))
                )
                .andDo(print())
                .andExpectAll(
                        status().isInternalServerError(),
                        view().name(PagesConstants.PAGE_ERROR),
                        forwardedUrl("/WEB-INF/jsp/error.jsp")
                );

        verify(reservationService, times(1)).findById(reservationDto.getId());
        verify(reservationService, times(1)).update(refEq(reservationDto));
        verifyNoMoreInteractions(reservationService);
    }

    @Test
    void whenRequestReservationDelete_thenReturnErrorPageAndMessage() throws Exception {
        this.mockMvc.perform(get("/reservations/delete/{id}", reservationDto.getId()))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        model().attribute("message", messageSource.getMessage("msg.delete.not.available",
                                null, LocaleContextHolder.getLocale())),
                        view().name(PagesConstants.PAGE_ERROR),
                        forwardedUrl("/WEB-INF/jsp/error.jsp"));

        verifyNoInteractions(reservationService);
    }

    @Test
    void whenSendReservationCancelFormCorrectData_thenReceiveCorrectDataAndDoRedirectWithMessage() throws Exception {
        when(reservationService.findById(reservationDto.getId())).thenReturn(reservationDto);
        when(reservationService.update(refEq(reservationDto))).thenReturn(reservationDto);
        this.mockMvc.perform(get("/reservations/cancel_reservation/{id}", reservationDto.getId())
                        .param("status", String.valueOf(reservationDto.getStatus()))
                )
                .andDo(print())
                .andExpectAll(
                        status().is3xxRedirection(),
                        header().string("Location", "/reservations/" + reservationDto.getId()),
                        request().sessionAttribute("message", messageSource.getMessage("msg.reservations.cancel",
                                null, LocaleContextHolder.getLocale()))
                );

        verify(reservationService, times(1)).findById(reservationDto.getId());
        verify(reservationService, times(1)).update(refEq(reservationDto));
        verifyNoMoreInteractions(reservationService);
    }

    @Test
    void whenSendReservationCancelFormCorrectDataButNull_thenReturnErrorPage() throws Exception {
        when(reservationService.findById(reservationDto.getId())).thenReturn(reservationDto);
        when(reservationService.update(refEq(reservationDto))).thenThrow(new ReservationServiceException(""));
        this.mockMvc.perform(get("/reservations/cancel_reservation/{id}", reservationDto.getId())
                        .param("status", String.valueOf(reservationDto.getStatus()))
                )
                .andDo(print())
                .andExpectAll(
                        status().isBadRequest(),
                        view().name(PagesConstants.PAGE_ERROR),
                        forwardedUrl("/WEB-INF/jsp/error.jsp")
                );

        verify(reservationService, times(1)).findById(reservationDto.getId());
        verify(reservationService, times(1)).update(refEq(reservationDto));
        verifyNoMoreInteractions(reservationService);
    }

    @Test
    void whenSendReservationCancelFormCorrectDataAndRuntimeExceptionOccurs_thenReturnErrorPage() throws Exception {
        when(reservationService.findById(reservationDto.getId())).thenReturn(reservationDto);
        when(reservationService.update(refEq(reservationDto))).thenThrow(new RuntimeException(""));
        this.mockMvc.perform(get("/reservations/cancel_reservation/{id}", reservationDto.getId())
                        .param("status", String.valueOf(reservationDto.getStatus()))
                )
                .andDo(print())
                .andExpectAll(
                        status().isInternalServerError(),
                        view().name(PagesConstants.PAGE_ERROR),
                        forwardedUrl("/WEB-INF/jsp/error.jsp")
                );

        verify(reservationService, times(1)).findById(reservationDto.getId());
        verify(reservationService, times(1)).update(refEq(reservationDto));
        verifyNoMoreInteractions(reservationService);
    }

    @Test
    void whenSendAddBooking_thenReceiveCorrectDataAndDoRedirect() throws Exception {
        String check_in = "2022-11-28";
        String check_out = "2022-11-30";
        Map<Long, Long> booking = DtoTest.getBooking();
        this.mockMvc.perform(post("/reservations/add_booking")
                        .param("roomId", "1")
                        .param("check_in", check_in)
                        .param("check_out", check_out)
                        .sessionAttr("booking", booking)
                )
                .andDo(print())
                .andExpectAll(
                        status().is3xxRedirection(),
                        header().string("Location", "/rooms/rooms_available"),
                        request().sessionAttribute("booking", booking),
                        request().sessionAttribute("check_in", LocalDate.parse(check_in)),
                        request().sessionAttribute("check_out", LocalDate.parse(check_out))
                );

        verifyNoInteractions(reservationService);
    }

    @Test
    void whenRequestBooking_thenReceiveCorrectDataAndView() throws Exception {
        String check_in = "2022-11-28";
        String check_out = "2022-11-30";
        Map<Long, Long> booking = DtoTest.getBooking();
        when(reservationService.processBooking(booking, null, LocalDate.parse(check_in), LocalDate.parse(check_out)))
                .thenReturn(reservationDto);
        this.mockMvc.perform(get("/reservations/booking")
                        .sessionAttr("check_in", check_in)
                        .sessionAttr("check_out", check_out)
                        .sessionAttr("booking", booking)
                )
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        model().attribute("booking", reservationDto),
                        view().name(PagesConstants.PAGE_BOOKING),
                        forwardedUrl("/WEB-INF/jsp/forms/jstl/reservations/booking.jsp")
                );

        verify(reservationService, times(1)).processBooking(booking, null,
                LocalDate.parse(check_in), LocalDate.parse(check_out));
        verifyNoMoreInteractions(reservationService);
    }

    @Test
    void whenRequestCleanBooking_thenReceiveCorrectDataAndView() throws Exception {
        this.mockMvc.perform(get("/reservations/clean_booking"))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        request().sessionAttributeDoesNotExist("booking"),
                        view().name(PagesConstants.PAGE_BOOKING),
                        forwardedUrl("/WEB-INF/jsp/forms/jstl/reservations/booking.jsp")
                );

        verifyNoInteractions(reservationService);
    }

    @Test
    void whenRequestDeleteBooking_thenReceiveCorrectDataAndDoRedirect() throws Exception {
        String check_in = "2022-11-28";
        String check_out = "2022-11-30";
        Map<Long, Long> booking = DtoTest.getBooking();
        this.mockMvc.perform(get("/reservations/delete_booking/{id}", 1L)
                        .sessionAttr("check_in", check_in)
                        .sessionAttr("check_out", check_out)
                        .sessionAttr("booking", booking)
                )
                .andDo(print())
                .andExpectAll(
                        status().is3xxRedirection(),
                        header().string("Location", "/reservations/booking"),
                        request().sessionAttribute("booking", booking),
                        request().sessionAttribute("check_in", LocalDate.parse(check_in)),
                        request().sessionAttribute("check_out", LocalDate.parse(check_out))
                );

        verifyNoInteractions(reservationService);
    }

    @Test
    @WithMockUser
    void whenRequestAllReservationsByUser_thenReturnCorrectView() throws Exception {
        UserDto userDto = DtoTest.getExpectedUserFormCreateWithId();
        when(userService.findByUsername("user")).thenReturn(userDto);

        List<ReservationDto> reservations = Collections.singletonList(reservationDto);
        Page<ReservationDto> pageReservationDto = new PageImpl<>(new ArrayList<>(reservations));
        when(reservationService.findAllPagesByUsers(any(), eq(userDto.getId()))).thenReturn(pageReservationDto);

        this.mockMvc.perform(get("/reservations/user_reservations/{id}", userDto.getId()))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        model().attribute("reservations", reservations),
                        view().name(PagesConstants.PAGE_RESERVATIONS),
                        forwardedUrl("/WEB-INF/jsp/forms/jstl/reservations/reservations.jsp")
                );

        verify(reservationService, times(1)).findAllPagesByUsers(any(), eq(userDto.getId()));
        verifyNoMoreInteractions(reservationService);
        verify(userService, times(1)).findByUsername("user");
        verifyNoMoreInteractions(userService);
    }

    @Test
    @WithMockUser
    void whenRequestAllReservationsByUserEmpty_thenReturnCorrectView() throws Exception {
        UserDto userDto = DtoTest.getExpectedUserFormCreateWithId();
        when(userService.findByUsername("user")).thenReturn(userDto);

        Page<ReservationDto> pageReservationDto = new PageImpl<>(new ArrayList<>());
        when(reservationService.findAllPagesByUsers(any(), eq(userDto.getId()))).thenReturn(pageReservationDto);
        this.mockMvc.perform(get("/reservations/user_reservations/{id}", userDto.getId()))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        model().attribute("message", messageSource.getMessage("msg.reservations.no", null,
                                LocaleContextHolder.getLocale())),
                        view().name(PagesConstants.PAGE_RESERVATIONS),
                        forwardedUrl("/WEB-INF/jsp/forms/jstl/reservations/reservations.jsp"));

        verify(reservationService, times(1)).findAllPagesByUsers(any(), eq(userDto.getId()));
        verifyNoMoreInteractions(reservationService);
        verify(userService, times(1)).findByUsername("user");
        verifyNoMoreInteractions(userService);
    }
}