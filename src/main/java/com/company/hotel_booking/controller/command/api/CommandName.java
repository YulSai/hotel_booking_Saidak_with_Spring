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

/**
 * Enum with commands
 */
public enum CommandName {

    //Authorizations
    LOGIN_FORM(LoginFormCommand.class, SecurityLevel.GUEST),
    LOGIN(LoginCommand.class, SecurityLevel.GUEST),
    LOGOUT(LogoutCommand.class, SecurityLevel.CLIENT),

    // Users
    USER(UserCommand.class, SecurityLevel.CLIENT),
    USERS(UsersCommand.class, SecurityLevel.ADMIN),
    CREATE_USER_FORM(CreateUserFormCommand.class, SecurityLevel.GUEST),
    CREATE_USER(CreateUserCommand.class, SecurityLevel.GUEST),
    UPDATE_USER_FORM(UpdateUserFormCommand.class, SecurityLevel.CLIENT),
    UPDATE_USER(UpdateUserCommand.class, SecurityLevel.CLIENT),
    DELETE_USER(DeleteUserCommand.class, SecurityLevel.ADMIN),
    CHANGE_PASSWORD_FORM(ChangePasswordFormCommand.class, SecurityLevel.CLIENT),
    CHANGE_PASSWORD(ChangePasswordCommand.class, SecurityLevel.CLIENT),

    //Rooms
    ROOM(RoomCommand.class, SecurityLevel.ADMIN),
    ROOMS(RoomsCommand.class, SecurityLevel.ADMIN),
    CREATE_ROOM_FORM(CreateRoomFormCommand.class, SecurityLevel.ADMIN),
    CREATE_ROOM(CreateRoomCommand.class, SecurityLevel.ADMIN),
    UPDATE_ROOM_FORM(UpdateRoomFormCommand.class, SecurityLevel.ADMIN),
    UPDATE_ROOM(UpdateRoomCommand.class, SecurityLevel.ADMIN),

    //Reservations
    RESERVATION(ReservationCommand.class, SecurityLevel.CLIENT),
    RESERVATIONS(ReservationsCommand.class, SecurityLevel.ADMIN),
    SEARCH_AVAILABLE_ROOMS_FORM(RoomsSearchAvailableFormCommand.class, SecurityLevel.GUEST),
    SEARCH_AVAILABLE_ROOMS(RoomsSearchAvailableCommand.class, SecurityLevel.GUEST),
    ROOMS_AVAILABLE(RoomsAvailableCommand.class, SecurityLevel.GUEST),
    ADD_BOOKING(AddBookingCommand.class, SecurityLevel.GUEST),
    CLEAN_BOOKING(CleanBookingCommand.class, SecurityLevel.GUEST),
    DELETE_BOOKING(DeleteBookingCommand.class, SecurityLevel.GUEST),
    BOOKING(BookingCommand.class, SecurityLevel.GUEST),
    CREATE_RESERVATION(CreateReservationCommand.class, SecurityLevel.CLIENT),
    UPDATE_RESERVATION_FORM(UpdateReservationFormCommand.class, SecurityLevel.ADMIN),
    UPDATE_RESERVATION(UpdateReservationCommand.class, SecurityLevel.ADMIN),
    CANCEL_RESERVATION(CancelReservationCommand.class, SecurityLevel.CLIENT),
    USER_RESERVATIONS(UserReservationsCommand.class, SecurityLevel.CLIENT),

    //Other
    ERROR(ErrorCommand.class, SecurityLevel.GUEST),
    LANGUAGE_SELECT(LanguageSelectionCommand.class, SecurityLevel.GUEST);

    private final Class<? extends ICommand> command;
    private final SecurityLevel level;

    CommandName(Class<? extends ICommand> command, SecurityLevel level) {
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

    public Class<? extends ICommand> getCommand() {
        return command;
    }

    public SecurityLevel getLevel() {
        return level;
    }
}