package com.company.hotel_booking.controller.command.api;

import com.company.hotel_booking.controller.command.impl.errors.ErrorCommand;
import com.company.hotel_booking.controller.command.impl.local.LanguageSelectionCommand;
import com.company.hotel_booking.controller.command.impl.authorizations.LoginCommand;
import com.company.hotel_booking.controller.command.impl.authorizations.LoginFormCommand;
import com.company.hotel_booking.controller.command.impl.authorizations.LogoutCommand;

import com.company.hotel_booking.controller.command.impl.reservations.AddBookingCommand;
import com.company.hotel_booking.controller.command.impl.reservations.BookingCommand;
import com.company.hotel_booking.controller.command.impl.reservations.CancelReservationCommand;
import com.company.hotel_booking.controller.command.impl.reservations.CreateReservationCommand;
import com.company.hotel_booking.controller.command.impl.reservations.CleanBookingCommand;
import com.company.hotel_booking.controller.command.impl.reservations.DeleteBookingCommand;
import com.company.hotel_booking.controller.command.impl.reservations.ReservationCommand;
import com.company.hotel_booking.controller.command.impl.reservations.ReservationsCommand;
import com.company.hotel_booking.controller.command.impl.reservations.UpdateReservationCommand;
import com.company.hotel_booking.controller.command.impl.reservations.UpdateReservationFormCommand;
import com.company.hotel_booking.controller.command.impl.reservations.UserReservationsCommand;
import com.company.hotel_booking.controller.command.impl.rooms.CreateRoomCommand;
import com.company.hotel_booking.controller.command.impl.rooms.CreateRoomFormCommand;
import com.company.hotel_booking.controller.command.impl.rooms.RoomCommand;
import com.company.hotel_booking.controller.command.impl.rooms.RoomsAvailableCommand;
import com.company.hotel_booking.controller.command.impl.rooms.RoomsCommand;
import com.company.hotel_booking.controller.command.impl.rooms.RoomsSearchAvailableCommand;
import com.company.hotel_booking.controller.command.impl.rooms.RoomsSearchAvailableFormCommand;
import com.company.hotel_booking.controller.command.impl.rooms.UpdateRoomCommand;
import com.company.hotel_booking.controller.command.impl.rooms.UpdateRoomFormCommand;
import com.company.hotel_booking.controller.command.impl.users.ChangePasswordCommand;
import com.company.hotel_booking.controller.command.impl.users.ChangePasswordFormCommand;
import com.company.hotel_booking.controller.command.impl.users.CreateUserCommand;
import com.company.hotel_booking.controller.command.impl.users.CreateUserFormCommand;
import com.company.hotel_booking.controller.command.impl.users.DeleteUserCommand;
import com.company.hotel_booking.controller.command.impl.users.UpdateUserCommand;
import com.company.hotel_booking.controller.command.impl.users.UpdateUserFormCommand;
import com.company.hotel_booking.controller.command.impl.users.UserCommand;
import com.company.hotel_booking.controller.command.impl.users.UsersCommand;
import com.company.hotel_booking.controller.command.util.PagingUtil;
import com.company.hotel_booking.service.api.IReservationInfoService;
import com.company.hotel_booking.service.api.IReservationService;
import com.company.hotel_booking.service.api.IRoomService;
import com.company.hotel_booking.service.api.IUserService;
import com.company.hotel_booking.service.factory.ServiceFactory;

/**
 * Enum with commands
 */
public enum CommandName {

    //Authorizations
    LOGIN_FORM(new LoginFormCommand(), SecurityLevel.GUEST),
    LOGIN(new LoginCommand(ServiceFactory.getINSTANCE().getService(IUserService.class)), SecurityLevel.GUEST),
    LOGOUT(new LogoutCommand(), SecurityLevel.CLIENT),

    // Users
    USER(new UserCommand(ServiceFactory.getINSTANCE().getService(IUserService.class)), SecurityLevel.CLIENT),
    USERS(new UsersCommand(ServiceFactory.getINSTANCE().getService(IUserService.class), PagingUtil.INSTANCE),
            SecurityLevel.ADMIN),
    CREATE_USER_FORM(new CreateUserFormCommand(), SecurityLevel.GUEST),
    CREATE_USER(new CreateUserCommand(ServiceFactory.getINSTANCE().getService(IUserService.class)),
            SecurityLevel.GUEST),
    UPDATE_USER_FORM(new UpdateUserFormCommand(ServiceFactory.getINSTANCE().getService(IUserService.class)),
            SecurityLevel.CLIENT),
    UPDATE_USER(new UpdateUserCommand(ServiceFactory.getINSTANCE().getService(IUserService.class)),
            SecurityLevel.CLIENT),
    DELETE_USER(new DeleteUserCommand(ServiceFactory.getINSTANCE().getService(IUserService.class),
            ServiceFactory.getINSTANCE().getService(IReservationService.class)),
            SecurityLevel.ADMIN),
    CHANGE_PASSWORD_FORM(new ChangePasswordFormCommand(ServiceFactory.getINSTANCE().getService(IUserService.class)),
            SecurityLevel.CLIENT),
    CHANGE_PASSWORD(new ChangePasswordCommand(ServiceFactory.getINSTANCE().getService(IUserService.class)),
            SecurityLevel.CLIENT),

    //Rooms
    ROOM(new RoomCommand(ServiceFactory.getINSTANCE().getService(IRoomService.class)), SecurityLevel.ADMIN),
    ROOMS(new RoomsCommand(ServiceFactory.getINSTANCE().getService(IRoomService.class), PagingUtil.INSTANCE),
            SecurityLevel.ADMIN),
    CREATE_ROOM_FORM(new CreateRoomFormCommand(), SecurityLevel.ADMIN),
    CREATE_ROOM(new CreateRoomCommand(ServiceFactory.getINSTANCE().getService(IRoomService.class)),
            SecurityLevel.ADMIN),
    UPDATE_ROOM_FORM(new UpdateRoomFormCommand(ServiceFactory.getINSTANCE().getService(IRoomService.class)),
            SecurityLevel.ADMIN),
    UPDATE_ROOM(new UpdateRoomCommand(ServiceFactory.getINSTANCE().getService(IRoomService.class)),
            SecurityLevel.ADMIN),

    //Reservations
    RESERVATION(new ReservationCommand(ServiceFactory.getINSTANCE().getService(IReservationService.class)),
            SecurityLevel.CLIENT),
    RESERVATIONS(new ReservationsCommand(ServiceFactory.getINSTANCE().getService(IReservationService.class),
            PagingUtil.INSTANCE), SecurityLevel.ADMIN),
    SEARCH_AVAILABLE_ROOMS_FORM(new RoomsSearchAvailableFormCommand(), SecurityLevel.GUEST),
    SEARCH_AVAILABLE_ROOMS(
            new RoomsSearchAvailableCommand((ServiceFactory.getINSTANCE().getService(IRoomService.class))),
            SecurityLevel.GUEST),
    ROOMS_AVAILABLE(new RoomsAvailableCommand(), SecurityLevel.GUEST),
    ADD_BOOKING(new AddBookingCommand(), SecurityLevel.GUEST),
    CLEAN_BOOKING(new CleanBookingCommand(), SecurityLevel.GUEST),
    DELETE_BOOKING(new DeleteBookingCommand(), SecurityLevel.GUEST),
    BOOKING(new BookingCommand(ServiceFactory.getINSTANCE().getService(IReservationService.class)),
            SecurityLevel.GUEST),
    CREATE_RESERVATION(new CreateReservationCommand(ServiceFactory.getINSTANCE().getService(IReservationService.class),
            ServiceFactory.getINSTANCE().getService(IReservationInfoService.class)), SecurityLevel.CLIENT),
    UPDATE_RESERVATION_FORM(new UpdateReservationFormCommand(ServiceFactory.getINSTANCE()
            .getService(IReservationService.class)), SecurityLevel.ADMIN),
    UPDATE_RESERVATION(new UpdateReservationCommand(ServiceFactory.getINSTANCE().getService(IReservationService.class)),
            SecurityLevel.ADMIN),
    CANCEL_RESERVATION(new CancelReservationCommand(ServiceFactory.getINSTANCE().getService(IReservationService.class)),
            SecurityLevel.CLIENT),
    USER_RESERVATIONS(new UserReservationsCommand(ServiceFactory.getINSTANCE().getService(IReservationService.class),
            PagingUtil.INSTANCE), SecurityLevel.CLIENT),

    //Other
    ERROR(new ErrorCommand(), SecurityLevel.GUEST),
    LANGUAGE_SELECT(new LanguageSelectionCommand(), SecurityLevel.GUEST);

    private final ICommand command;
    private final SecurityLevel level;

    CommandName(ICommand command, SecurityLevel level) {
        this.command = command;
        this.level = level;
    }

    /**
     * Method to check if the command exists
     *
     * @param command passed command
     * @return true/false
     */
    public static boolean contains(String command) {
        try {
            CommandName.valueOf(command);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public ICommand getCommand() {
        return command;
    }

    public SecurityLevel getLevel() {
        return level;
    }
}