package com.company.hotel_booking.controller.command.factory;

import com.company.hotel_booking.controller.command.CommandResolver;
import com.company.hotel_booking.controller.command.api.CommandName;
import com.company.hotel_booking.controller.command.api.ICommand;
import com.company.hotel_booking.controller.command.impl.authorizations.LoginCommand;
import com.company.hotel_booking.controller.command.impl.authorizations.LoginFormCommand;
import com.company.hotel_booking.controller.command.impl.authorizations.LogoutCommand;
import com.company.hotel_booking.controller.command.impl.errors.ErrorCommand;
import com.company.hotel_booking.controller.command.impl.local.LanguageSelectionCommand;
import com.company.hotel_booking.controller.command.impl.reservations.AddBookingCommand;
import com.company.hotel_booking.controller.command.impl.reservations.BookingCommand;
import com.company.hotel_booking.controller.command.impl.reservations.CancelReservationCommand;
import com.company.hotel_booking.controller.command.impl.reservations.CleanBookingCommand;
import com.company.hotel_booking.controller.command.impl.reservations.CreateReservationCommand;
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
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandResolverTest {
    private final CommandResolver commandResolver = new CommandResolver();

    @Test
    public void createLoginFormCommand() {
        Class<? extends ICommand> actual = commandResolver.getCommand(String.valueOf(CommandName.LOGIN_FORM));
        assertEquals(actual, LoginFormCommand.class);
    }

    @Test
    public void createLoginCommand() {
        Class<? extends ICommand> actual = commandResolver.getCommand(String.valueOf(CommandName.LOGIN));
        assertEquals(actual, LoginCommand.class);
    }

    @Test
    public void createLogoutCommand() {
        Class<? extends ICommand> actual = commandResolver.getCommand(String.valueOf(CommandName.LOGOUT));
        assertEquals(actual, LogoutCommand.class);
    }

    @Test
    public void createUserCommand() {
        Class<? extends ICommand> actual = commandResolver.getCommand(String.valueOf(CommandName.USER));
        assertEquals(actual, UserCommand.class);
    }

    @Test
    public void createUsersCommand() {
        Class<? extends ICommand> actual = commandResolver.getCommand(String.valueOf(CommandName.USERS));
        assertEquals(actual, UsersCommand.class);
    }

    @Test
    public void createCreateUserFormCommand() {
        Class<? extends ICommand> actual = commandResolver.getCommand(String.valueOf(CommandName.CREATE_USER_FORM));
        assertEquals(actual, CreateUserFormCommand.class);
    }

    @Test
    public void createCreateUserCommand() {
        Class<? extends ICommand> actual = commandResolver.getCommand(String.valueOf(CommandName.CREATE_USER));
        assertEquals(actual, CreateUserCommand.class);
    }

    @Test
    public void createUpdateUserFormCommand() {
        Class<? extends ICommand> actual = commandResolver.getCommand(String.valueOf(CommandName.UPDATE_USER_FORM));
        assertEquals(actual, UpdateUserFormCommand.class);
    }

    @Test
    public void createUpdateUserCommand() {
        Class<? extends ICommand> actual = commandResolver.getCommand(String.valueOf(CommandName.UPDATE_USER));
        assertEquals(actual, UpdateUserCommand.class);
    }

    @Test
    public void createDeleteUserCommand() {
        Class<? extends ICommand> actual = commandResolver.getCommand(String.valueOf(CommandName.DELETE_USER));
        assertEquals(actual, DeleteUserCommand.class);
    }

    @Test
    public void createChangePasswordFormCommand() {
        Class<? extends ICommand> actual = commandResolver.getCommand(String.valueOf(CommandName.CHANGE_PASSWORD_FORM));
        assertEquals(actual, ChangePasswordFormCommand.class);
    }

    @Test
    public void createChangePasswordCommand() {
        Class<? extends ICommand> actual = commandResolver.getCommand(String.valueOf(CommandName.CHANGE_PASSWORD));
        assertEquals(actual, ChangePasswordCommand.class);
    }

    @Test
    public void createRoomCommand() {
        Class<? extends ICommand> actual = commandResolver.getCommand(String.valueOf(CommandName.ROOM));
        assertEquals(actual, RoomCommand.class);
    }

    @Test
    public void createRoomsCommand() {
        Class<? extends ICommand> actual = commandResolver.getCommand(String.valueOf(CommandName.ROOMS));
        assertEquals(actual, RoomsCommand.class);
    }

    @Test
    public void createCreateRoomFormCommand() {
        Class<? extends ICommand> actual = commandResolver.getCommand(String.valueOf(CommandName.CREATE_ROOM_FORM));
        assertEquals(actual, CreateRoomFormCommand.class);
    }

    @Test
    public void createCreateRoomCommand() {
        Class<? extends ICommand> actual = commandResolver.getCommand(String.valueOf(CommandName.CREATE_ROOM));
        assertEquals(actual, CreateRoomCommand.class);
    }

    @Test
    public void createUpdateRoomFormCommand() {
        Class<? extends ICommand> actual = commandResolver.getCommand(String.valueOf(CommandName.UPDATE_ROOM_FORM));
        assertEquals(actual, UpdateRoomFormCommand.class);
    }

    @Test
    public void createUpdateRoomCommand() {
        Class<? extends ICommand> actual = commandResolver.getCommand(String.valueOf(CommandName.UPDATE_ROOM));
        assertEquals(actual, UpdateRoomCommand.class);
    }

    @Test
    public void createReservationCommand() {
        Class<? extends ICommand> actual = commandResolver.getCommand(String.valueOf(CommandName.RESERVATION));
        assertEquals(actual, ReservationCommand.class);
    }

    @Test
    public void createReservationsCommand() {
        Class<? extends ICommand> actual = commandResolver.getCommand(String.valueOf(CommandName.RESERVATIONS));
        assertEquals(actual, ReservationsCommand.class);
    }

    @Test
    public void createRoomsSearchAvailableFormCommand() {
        Class<? extends ICommand> actual = commandResolver.getCommand(
                String.valueOf(CommandName.SEARCH_AVAILABLE_ROOMS_FORM));
        assertEquals(actual, RoomsSearchAvailableFormCommand.class);
    }

    @Test
    public void createRoomsSearchAvailableCommand() {
        Class<? extends ICommand> actual = commandResolver.getCommand(
                String.valueOf(CommandName.SEARCH_AVAILABLE_ROOMS));
        assertEquals(actual, RoomsSearchAvailableCommand.class);
    }

    @Test
    public void createRoomsAvailableCommand() {
        Class<? extends ICommand> actual = commandResolver.getCommand(String.valueOf(CommandName.ROOMS_AVAILABLE));
        assertEquals(actual, RoomsAvailableCommand.class);
    }

    @Test
    public void createAddBookingCommand() {
        Class<? extends ICommand> actual = commandResolver.getCommand(String.valueOf(CommandName.ADD_BOOKING));
        assertEquals(actual, AddBookingCommand.class);
    }

    @Test
    public void createCleanBookingCommand() {
        Class<? extends ICommand> actual = commandResolver.getCommand(String.valueOf(CommandName.CLEAN_BOOKING));
        assertEquals(actual, CleanBookingCommand.class);
    }

    @Test
    public void createDeleteBookingCommand() {
        Class<? extends ICommand> actual = commandResolver.getCommand(String.valueOf(CommandName.DELETE_BOOKING));
        assertEquals(actual, DeleteBookingCommand.class);
    }

    @Test
    public void createBookingCommand() {
        Class<? extends ICommand> actual = commandResolver.getCommand(String.valueOf(CommandName.BOOKING));
        assertEquals(actual, BookingCommand.class);
    }

    @Test
    public void createCreateReservationCommand() {
        Class<? extends ICommand> actual = commandResolver.getCommand(String.valueOf(CommandName.CREATE_RESERVATION));
        assertEquals(actual, CreateReservationCommand.class);
    }

    @Test
    public void createUpdateReservationFormCommand() {
        Class<? extends ICommand> actual = commandResolver.getCommand(
                String.valueOf(CommandName.UPDATE_RESERVATION_FORM));
        assertEquals(actual, UpdateReservationFormCommand.class);
    }

    @Test
    public void createUpdateReservationCommand() {
        Class<? extends ICommand> actual = commandResolver.getCommand(String.valueOf(CommandName.UPDATE_RESERVATION));
        assertEquals(actual, UpdateReservationCommand.class);
    }

    @Test
    public void createCancelReservationCommand() {
        Class<? extends ICommand> actual = commandResolver.getCommand(String.valueOf(CommandName.CANCEL_RESERVATION));
        assertEquals(actual, CancelReservationCommand.class);
    }

    @Test
    public void createUserReservationsCommand() {
        Class<? extends ICommand> actual = commandResolver.getCommand(String.valueOf(CommandName.USER_RESERVATIONS));
        assertEquals(actual, UserReservationsCommand.class);
    }

    @Test
    public void createErrorCommand() {
        Class<? extends ICommand> actual = commandResolver.getCommand(String.valueOf(CommandName.ERROR));
        assertEquals(actual, ErrorCommand.class);
    }

    @Test
    public void createLanguageSelectionCommand() {
        Class<? extends ICommand> actual = commandResolver.getCommand(String.valueOf(CommandName.LANGUAGE_SELECT));
        assertEquals(actual, LanguageSelectionCommand.class);
    }
}
