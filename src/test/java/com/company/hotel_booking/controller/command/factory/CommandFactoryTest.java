package com.company.hotel_booking.controller.command.factory;

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

public class CommandFactoryTest {

    @Test
    public void createLoginFormCommand() {
        ICommand actual = CommandFactory.getINSTANCE().getCommand(String.valueOf(CommandName.LOGIN_FORM));
        assertEquals(actual.getClass(), LoginFormCommand.class);
    }

    @Test
    public void createLoginCommand() {
        ICommand actual = CommandFactory.getINSTANCE().getCommand(String.valueOf(CommandName.LOGIN));
        assertEquals(actual.getClass(), LoginCommand.class);
    }

    @Test
    public void createLogoutCommand() {
        ICommand actual = CommandFactory.getINSTANCE().getCommand(String.valueOf(CommandName.LOGOUT));
        assertEquals(actual.getClass(), LogoutCommand.class);
    }

    @Test
    public void createUserCommand() {
        ICommand actual = CommandFactory.getINSTANCE().getCommand(String.valueOf(CommandName.USER));
        assertEquals(actual.getClass(), UserCommand.class);
    }

    @Test
    public void createUsersCommand() {
        ICommand actual = CommandFactory.getINSTANCE().getCommand(String.valueOf(CommandName.USERS));
        assertEquals(actual.getClass(), UsersCommand.class);
    }

    @Test
    public void createCreateUserFormCommand() {
        ICommand actual = CommandFactory.getINSTANCE().getCommand(String.valueOf(CommandName.CREATE_USER_FORM));
        assertEquals(actual.getClass(), CreateUserFormCommand.class);
    }

    @Test
    public void createCreateUserCommand() {
        ICommand actual = CommandFactory.getINSTANCE().getCommand(String.valueOf(CommandName.CREATE_USER));
        assertEquals(actual.getClass(), CreateUserCommand.class);
    }

    @Test
    public void createUpdateUserFormCommand() {
        ICommand actual = CommandFactory.getINSTANCE().getCommand(String.valueOf(CommandName.UPDATE_USER_FORM));
        assertEquals(actual.getClass(), UpdateUserFormCommand.class);
    }

    @Test
    public void createUpdateUserCommand() {
        ICommand actual = CommandFactory.getINSTANCE().getCommand(String.valueOf(CommandName.UPDATE_USER));
        assertEquals(actual.getClass(), UpdateUserCommand.class);
    }

    @Test
    public void createDeleteUserCommand() {
        ICommand actual = CommandFactory.getINSTANCE().getCommand(String.valueOf(CommandName.DELETE_USER));
        assertEquals(actual.getClass(), DeleteUserCommand.class);
    }

    @Test
    public void createChangePasswordFormCommand() {
        ICommand actual = CommandFactory.getINSTANCE().getCommand(String.valueOf(CommandName.CHANGE_PASSWORD_FORM));
        assertEquals(actual.getClass(), ChangePasswordFormCommand.class);
    }

    @Test
    public void createChangePasswordCommand() {
        ICommand actual = CommandFactory.getINSTANCE().getCommand(String.valueOf(CommandName.CHANGE_PASSWORD));
        assertEquals(actual.getClass(), ChangePasswordCommand.class);
    }

    @Test
    public void createRoomCommand() {
        ICommand actual = CommandFactory.getINSTANCE().getCommand(String.valueOf(CommandName.ROOM));
        assertEquals(actual.getClass(), RoomCommand.class);
    }

    @Test
    public void createRoomsCommand() {
        ICommand actual = CommandFactory.getINSTANCE().getCommand(String.valueOf(CommandName.ROOMS));
        assertEquals(actual.getClass(), RoomsCommand.class);
    }

    @Test
    public void createCreateRoomFormCommand() {
        ICommand actual = CommandFactory.getINSTANCE().getCommand(String.valueOf(CommandName.CREATE_ROOM_FORM));
        assertEquals(actual.getClass(), CreateRoomFormCommand.class);
    }

    @Test
    public void createCreateRoomCommand() {
        ICommand actual = CommandFactory.getINSTANCE().getCommand(String.valueOf(CommandName.CREATE_ROOM));
        assertEquals(actual.getClass(), CreateRoomCommand.class);
    }

    @Test
    public void createUpdateRoomFormCommand() {
        ICommand actual = CommandFactory.getINSTANCE().getCommand(String.valueOf(CommandName.UPDATE_ROOM_FORM));
        assertEquals(actual.getClass(), UpdateRoomFormCommand.class);
    }

    @Test
    public void createUpdateRoomCommand() {
        ICommand actual = CommandFactory.getINSTANCE().getCommand(String.valueOf(CommandName.UPDATE_ROOM));
        assertEquals(actual.getClass(), UpdateRoomCommand.class);
    }

    @Test
    public void createReservationCommand() {
        ICommand actual = CommandFactory.getINSTANCE().getCommand(String.valueOf(CommandName.RESERVATION));
        assertEquals(actual.getClass(), ReservationCommand.class);
    }

    @Test
    public void createReservationsCommand() {
        ICommand actual = CommandFactory.getINSTANCE().getCommand(String.valueOf(CommandName.RESERVATIONS));
        assertEquals(actual.getClass(), ReservationsCommand.class);
    }

    @Test
    public void createRoomsSearchAvailableFormCommand() {
        ICommand actual = CommandFactory.getINSTANCE().getCommand(
                String.valueOf(CommandName.SEARCH_AVAILABLE_ROOMS_FORM));
        assertEquals(actual.getClass(), RoomsSearchAvailableFormCommand.class);
    }

    @Test
    public void createRoomsSearchAvailableCommand() {
        ICommand actual = CommandFactory.getINSTANCE().getCommand(String.valueOf(CommandName.SEARCH_AVAILABLE_ROOMS));
        assertEquals(actual.getClass(), RoomsSearchAvailableCommand.class);
    }

    @Test
    public void createRoomsAvailableCommand() {
        ICommand actual = CommandFactory.getINSTANCE().getCommand(String.valueOf(CommandName.ROOMS_AVAILABLE));
        assertEquals(actual.getClass(), RoomsAvailableCommand.class);
    }

    @Test
    public void createAddBookingCommand() {
        ICommand actual = CommandFactory.getINSTANCE().getCommand(String.valueOf(CommandName.ADD_BOOKING));
        assertEquals(actual.getClass(), AddBookingCommand.class);
    }

    @Test
    public void createCleanBookingCommand() {
        ICommand actual = CommandFactory.getINSTANCE().getCommand(String.valueOf(CommandName.CLEAN_BOOKING));
        assertEquals(actual.getClass(), CleanBookingCommand.class);
    }

    @Test
    public void createDeleteBookingCommand() {
        ICommand actual = CommandFactory.getINSTANCE().getCommand(String.valueOf(CommandName.DELETE_BOOKING));
        assertEquals(actual.getClass(), DeleteBookingCommand.class);
    }

    @Test
    public void createBookingCommand() {
        ICommand actual = CommandFactory.getINSTANCE().getCommand(String.valueOf(CommandName.BOOKING));
        assertEquals(actual.getClass(), BookingCommand.class);
    }

    @Test
    public void createCreateReservationCommand() {
        ICommand actual = CommandFactory.getINSTANCE().getCommand(String.valueOf(CommandName.CREATE_RESERVATION));
        assertEquals(actual.getClass(), CreateReservationCommand.class);
    }

    @Test
    public void createUpdateReservationFormCommand() {
        ICommand actual = CommandFactory.getINSTANCE().getCommand(String.valueOf(CommandName.UPDATE_RESERVATION_FORM));
        assertEquals(actual.getClass(), UpdateReservationFormCommand.class);
    }

    @Test
    public void createUpdateReservationCommand() {
        ICommand actual = CommandFactory.getINSTANCE().getCommand(String.valueOf(CommandName.UPDATE_RESERVATION));
        assertEquals(actual.getClass(), UpdateReservationCommand.class);
    }

    @Test
    public void createCancelReservationCommand() {
        ICommand actual = CommandFactory.getINSTANCE().getCommand(String.valueOf(CommandName.CANCEL_RESERVATION));
        assertEquals(actual.getClass(), CancelReservationCommand.class);
    }

    @Test
    public void createUserReservationsCommand() {
        ICommand actual = CommandFactory.getINSTANCE().getCommand(String.valueOf(CommandName.USER_RESERVATIONS));
        assertEquals(actual.getClass(), UserReservationsCommand.class);
    }

    @Test
    public void createErrorCommand() {
        ICommand actual = CommandFactory.getINSTANCE().getCommand(String.valueOf(CommandName.ERROR));
        assertEquals(actual.getClass(), ErrorCommand.class);
    }

    @Test
    public void createLanguageSelectionCommand() {
        ICommand actual = CommandFactory.getINSTANCE().getCommand(String.valueOf(CommandName.LANGUAGE_SELECT));
        assertEquals(actual.getClass(), LanguageSelectionCommand.class);
    }
}
