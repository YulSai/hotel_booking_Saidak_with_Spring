package com.company.hotel_booking.web.controllers.view;

import com.company.hotel_booking.data.entity.Room;
import com.company.hotel_booking.service.api.RoomService;
import com.company.hotel_booking.service.dto.RoomDto;
import com.company.hotel_booking.utils.DtoTest;
import com.company.hotel_booking.utils.constants.PagesConstants;
import com.company.hotel_booking.utils.exceptions.rooms.RoomAlreadyExistsException;
import com.company.hotel_booking.utils.exceptions.rooms.RoomNotFoundException;
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
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
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

@WebMvcTest(controllers = RoomController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@ExtendWith(MockitoExtension.class)
class RoomControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RoomService roomService;
    @MockBean
    private PagingUtil pagingUtil;
    @MockBean
    private MessageSource messageSource;
    private RoomDto roomDto;

    @BeforeEach
    public void setup() {
        roomDto = DtoTest.getExpectedRoomWithId();
    }

    @Test
    void whenRequestAllRooms_thenReturnCorrectView() throws Exception {
        List<RoomDto> rooms = Collections.singletonList(roomDto);
        Page<RoomDto> pageRoomDto = new PageImpl<>(new ArrayList<>(rooms));
        when(roomService.findAllPages(any())).thenReturn(pageRoomDto);
        this.mockMvc.perform(get("/rooms/all"))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        model().attribute("rooms", rooms),
                        view().name(PagesConstants.PAGE_ROOMS),
                        forwardedUrl("/WEB-INF/jsp/forms/jstl/rooms/rooms.jsp"));

        verify(roomService, times(1)).findAllPages(any());
        verifyNoMoreInteractions(roomService);
    }

    @Test
    void whenRequestAllRoomsEmpty_thenReturnCorrectView() throws Exception {
        Page<RoomDto> pageRoomDto = new PageImpl<>(new ArrayList<>());
        when(roomService.findAllPages(any())).thenReturn(pageRoomDto);
        this.mockMvc.perform(get("/rooms/all"))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        model().attribute("message", messageSource.getMessage("msg.rooms.no.rooms", null,
                                        LocaleContextHolder.getLocale())),
                        view().name(PagesConstants.PAGE_ROOMS),
                        forwardedUrl("/WEB-INF/jsp/forms/jstl/rooms/rooms.jsp"));

        verify(roomService, times(1)).findAllPages(any());
        verifyNoMoreInteractions(roomService);
    }

    @Test
    void whenRequestOneRoomById_thenReturnCorrectViewWithModelAttribute() throws Exception {
        when(roomService.findById(roomDto.getId())).thenReturn(roomDto);
        this.mockMvc.perform(
                        get("/rooms/{id}", roomDto.getId()))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        model().attribute("room", roomDto),
                        view().name(PagesConstants.PAGE_ROOM),
                        forwardedUrl("/WEB-INF/jsp/forms/jstl/rooms/room.jsp"));

        verify(roomService, times(1)).findById(roomDto.getId());
        verifyNoMoreInteractions(roomService);
    }

    @Test
    void whenRequestNonExistingRoom_thenReturnCorrectViewAndExceptionMessage() throws Exception {
        when(roomService.findById(roomDto.getId())).thenThrow(new RoomNotFoundException(""));
        this.mockMvc.perform(get("/rooms/{id}", roomDto.getId()))
                .andDo(print())
                .andExpectAll(
                        status().isNotFound(),
                        model().attributeExists("message"),
                        view().name(PagesConstants.PAGE_ERROR),
                        forwardedUrl("/WEB-INF/jsp/error.jsp"));
        verify(roomService, times(1)).findById(roomDto.getId());
        verifyNoMoreInteractions(roomService);
    }

    @Test
    void whenRequestRoomCreationForm_thenReturnCorrectView() throws Exception {
        this.mockMvc.perform(get("/rooms/create"))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        view().name(PagesConstants.PAGE_CREATE_ROOM),
                        forwardedUrl("/WEB-INF/jsp/forms/jstl/rooms/createRoomForm.jsp"));

        verifyNoInteractions(roomService);
    }

    @Test
    void whenSendRoomCreationFormCorrectData_thenReceiveCorrectDataAndDoRedirect() throws Exception {
        RoomDto roomCreated = DtoTest.getExpectedRoomWithoutId();
        when(roomService.create(refEq(roomCreated))).thenReturn(roomDto);
        this.mockMvc.perform(post("/rooms/create")
                        .param("number", roomCreated.getNumber())
                        .param("type", String.valueOf(roomCreated.getType()))
                        .param("capacity", String.valueOf(roomCreated.getCapacity()))
                        .param("status", String.valueOf(roomCreated.getStatus()))
                        .param("price", String.valueOf(roomCreated.getPrice()))
                )
                .andDo(print())
                .andExpectAll(
                        status().is3xxRedirection(),
                        header().string("Location", "/rooms/" + roomDto.getId()),
                        request().sessionAttribute("message", messageSource.getMessage("msg.room.created",
                                null, LocaleContextHolder.getLocale())));

        verify(roomService, times(1)).create(refEq(roomCreated));
        verifyNoMoreInteractions(roomService);
    }

    @Test
    void whenSendRoomCreationFormIncorrectData_thenGetErrorsAndReturnSamePage() throws Exception {
        RoomDto roomCreated = DtoTest.getExpectedRoomWithoutId();
        this.mockMvc.perform(post("/rooms/create")
                        .param("type", String.valueOf(roomCreated.getType()))
                        .param("capacity", String.valueOf(roomCreated.getCapacity()))
                        .param("status", String.valueOf(roomCreated.getStatus()))
                        .param("price", String.valueOf(roomCreated.getPrice()))
                )
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        view().name(PagesConstants.PAGE_CREATE_ROOM),
                        forwardedUrl("/WEB-INF/jsp/forms/jstl/rooms/createRoomForm.jsp"));

        verifyNoInteractions(roomService);
    }

    @Test
    void whenSendRoomCreationFormWithExistingUsername_thenReturnErrorViewAndExceptionMessage() throws Exception {
        RoomDto roomCreated = DtoTest.getExpectedRoomWithoutId();
        when(roomService.create(refEq(roomCreated))).thenThrow(new RoomAlreadyExistsException(""));
        this.mockMvc.perform(post("/rooms/create")
                        .param("number", roomCreated.getNumber())
                        .param("type", String.valueOf(roomCreated.getType()))
                        .param("capacity", String.valueOf(roomCreated.getCapacity()))
                        .param("status", String.valueOf(roomCreated.getStatus()))
                        .param("price", String.valueOf(roomCreated.getPrice()))
                )
                .andDo(print())
                .andExpectAll(
                        status().isBadRequest(),
                        model().attributeExists("message"),
                        view().name(PagesConstants.PAGE_ERROR),
                        forwardedUrl("/WEB-INF/jsp/error.jsp"));

        verify(roomService, times(1)).create(refEq(roomCreated));
        verifyNoMoreInteractions(roomService);
    }

    @Test
    void whenRequestRoomUpdateForm_thenReturnCorrectView() throws Exception {
        when(roomService.findById(roomDto.getId())).thenReturn(roomDto);
        this.mockMvc.perform(get("/rooms/update/{id}", roomDto.getId()))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        model().attribute("room", roomDto),
                        view().name(PagesConstants.PAGE_UPDATE_ROOM),
                        forwardedUrl("/WEB-INF/jsp/forms/jstl/rooms/updateRoomForm.jsp"));

        verify(roomService, times(1)).findById(roomDto.getId());
        verifyNoMoreInteractions(roomService);
    }

    @Test
    void whenSendRoomUpdateFormCorrectData_thenReceiveCorrectDataAndDoRedirectWithMessage() throws Exception {
        when(roomService.update(refEq(roomDto))).thenReturn(roomDto);
        this.mockMvc.perform(post("/rooms/update/{id}", roomDto.getId())
                        .param("number", roomDto.getNumber())
                        .param("type", String.valueOf(roomDto.getType()))
                        .param("capacity", String.valueOf(roomDto.getCapacity()))
                        .param("status", String.valueOf(roomDto.getStatus()))
                        .param("price", String.valueOf(roomDto.getPrice()))
                )
                .andDo(print())
                .andExpectAll(
                        status().is3xxRedirection(),
                        header().string("Location", "/rooms/" + roomDto.getId()),
                        request().sessionAttribute("message", messageSource.getMessage("msg.room.updated",
                                null, LocaleContextHolder.getLocale()))
                );

        verify(roomService, times(1)).update(refEq(roomDto));
        verifyNoMoreInteractions(roomService);
    }

    @Test
    void whenSendRoomUpdateFormIncorrectData_thenGetErrorsAndReturnSamePage() throws Exception {
        this.mockMvc.perform(post("/rooms/update/{id}", roomDto.getId())
                        .param("type", String.valueOf(roomDto.getType()))
                        .param("capacity", String.valueOf(roomDto.getCapacity()))
                        .param("status", String.valueOf(roomDto.getStatus()))
                        .param("price", String.valueOf(roomDto.getPrice()))
                )
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        model().attribute("room", roomDto),
                        view().name(PagesConstants.PAGE_UPDATE_ROOM),
                        forwardedUrl("/WEB-INF/jsp/forms/jstl/rooms/updateRoomForm.jsp"));

        verifyNoInteractions(roomService);
    }

    @Test
    void whenSendRoomUpdateFormCorrectDataAndRuntimeExceptionOccurs_thenReturnErrorPage() throws Exception {
        when(roomService.update(refEq(roomDto))).thenThrow(RuntimeException.class);
        this.mockMvc.perform(post("/rooms/update/{id}", roomDto.getId())
                        .param("number", roomDto.getNumber())
                        .param("type", String.valueOf(roomDto.getType()))
                        .param("capacity", String.valueOf(roomDto.getCapacity()))
                        .param("status", String.valueOf(roomDto.getStatus()))
                        .param("price", String.valueOf(roomDto.getPrice()))
                )
                .andDo(print())
                .andExpectAll(
                        status().isInternalServerError(),
                        view().name(PagesConstants.PAGE_ERROR),
                        forwardedUrl("/WEB-INF/jsp/error.jsp"));

        verify(roomService, times(1)).update(refEq(roomDto));
        verifyNoMoreInteractions(roomService);
    }

    @Test
    void whenRequestRoomDelete_thenReturnErrorPageAndMessage() throws Exception {
        this.mockMvc.perform(get("/rooms/delete/{id}", roomDto.getId()))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        model().attribute("message", messageSource.getMessage("msg.delete.not.available",
                                null, LocaleContextHolder.getLocale())),
                        view().name(PagesConstants.PAGE_ERROR),
                        forwardedUrl("/WEB-INF/jsp/error.jsp"));

        verifyNoInteractions(roomService);
    }

    @Test
    void whenRequestSearchAvailableRoomForm_thenReturnCorrectView() throws Exception {
        this.mockMvc.perform(get("/rooms/search_available_rooms"))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        view().name(PagesConstants.PAGE_SEARCH_AVAILABLE_ROOMS),
                        forwardedUrl("/WEB-INF/jsp/forms/jstl/reservations/searchAvailableRoomsForm.jsp"));

        verifyNoInteractions(roomService);
    }

    @Test
    void whenRequestSearchAvailableRoomFormCorrectData_thenReceiveCorrectDataAndDoRedirect() throws Exception {
        String type = roomDto.getType().toString();
        String capacity = roomDto.getCapacity().toString();
        Long typeId = Room.RoomType.valueOf(type.toUpperCase()).getId();
        Long capacityId = Room.Capacity.valueOf(capacity.toUpperCase()).getId();
        LocalDate checkIn = LocalDate.parse("2022-11-28");
        LocalDate checkOut = LocalDate.parse("2022-11-30");
        List<RoomDto> roomsAvailable = Collections.singletonList(roomDto);

        when(roomService.findAvailableRooms(typeId, capacityId, checkIn, checkOut)).thenReturn(roomsAvailable);
        this.mockMvc.perform(post("/rooms/search_available_rooms")
                        .param("check_in", String.valueOf(checkIn))
                        .param("check_out", String.valueOf(checkOut))
                        .param("type", type)
                        .param("capacity", capacity)
                )
                .andDo(print())
                .andExpectAll(
                        status().is3xxRedirection(),
                        header().string("Location", "/rooms/rooms_available"),
                        request().sessionAttribute("rooms_available", roomsAvailable),
                        request().sessionAttribute("check_in", checkIn),
                        request().sessionAttribute("check_out", checkOut)
                );

        verify(roomService, times(1)).findAvailableRooms(typeId, capacityId, checkIn, checkOut);
        verifyNoMoreInteractions(roomService);
    }

    @Test
    void whenRequestAvailableRoomsEmpty_thenReturnCorrectViewAndExceptionMessage() throws Exception {
        String type = roomDto.getType().toString();
        String capacity = roomDto.getCapacity().toString();
        Long typeId = Room.RoomType.valueOf(type.toUpperCase()).getId();
        Long capacityId = Room.Capacity.valueOf(capacity.toUpperCase()).getId();
        LocalDate checkIn = LocalDate.parse("2022-11-28");
        LocalDate checkOut = LocalDate.parse("2022-11-30");
        List<RoomDto> roomsAvailable = new ArrayList<>();

        when(roomService.findAvailableRooms(typeId, capacityId, checkIn, checkOut)).thenReturn(roomsAvailable);
        this.mockMvc.perform(post("/rooms/search_available_rooms")
                        .param("check_in", String.valueOf(checkIn))
                        .param("check_out", String.valueOf(checkOut))
                        .param("type", type)
                        .param("capacity", capacity)
                )
                .andDo(print())
                .andExpectAll(
                        status().is3xxRedirection(),
                        header().string("Location", "/rooms/rooms_available"),
                        request().sessionAttribute("rooms_available", roomsAvailable),
                        request().sessionAttribute("check_in", checkIn),
                        request().sessionAttribute("check_out", checkOut)
                );

        verify(roomService, times(1)).findAvailableRooms(typeId, capacityId, checkIn, checkOut);
        verifyNoMoreInteractions(roomService);
    }

    @Test
    void whenRequestGetAvailableRoom_thenReturnCorrectView() throws Exception {
        this.mockMvc.perform(get("/rooms/rooms_available"))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        view().name(PagesConstants.PAGE_ROOMS_AVAILABLE),
                        forwardedUrl("/WEB-INF/jsp/forms/jstl/reservations/roomsAvailable.jsp"));

        verifyNoInteractions(roomService);
    }

    @Test
    void whenRequestReceiveAvailableRoom_thenReturnCorrectView() throws Exception {
        this.mockMvc.perform(post("/rooms/rooms_available"))
                .andDo(print())
                .andExpectAll(
                        status().is3xxRedirection(),
                        header().string("Location", "/rooms/rooms_available"));

        verifyNoInteractions(roomService);
    }
}