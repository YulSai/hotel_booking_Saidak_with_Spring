package com.company.hotel_booking.controller.command.factory;

import com.company.hotel_booking.controller.command.api.CommandName;
import com.company.hotel_booking.controller.command.api.ICommand;
import com.company.hotel_booking.controller.command.api.SecurityLevel;

/**
 * Class with methods for processing commands
 */
public class CommandFactory {
    private static CommandFactory INSTANCE;

    /**
     * Method gets an instance of the class object
     *
     * @return an instance of the class object
     */
    public static CommandFactory getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new CommandFactory();
        }
        return INSTANCE;
    }

    /**
     * Method gets command
     *
     * @param commandName passed command
     * @return command
     */
    public ICommand getCommand(String commandName) {
        ICommand command = CommandName.valueOf(commandName.toUpperCase()).getCommand();
        if (command == null) {
            command = CommandName.valueOf("ERROR").getCommand();
        }
        return command;
    }

    /**
     * Method gets security level
     *
     * @param commandName passed command
     * @return security level
     */
    public SecurityLevel getSecurityLevel(String commandName) {
        return CommandName.valueOf(commandName.toUpperCase()).getLevel();
    }
}