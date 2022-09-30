package com.company.hotel_booking.controller.command;

import com.company.hotel_booking.controller.command.api.CommandName;
import com.company.hotel_booking.controller.command.api.ICommand;
import com.company.hotel_booking.controller.command.api.SecurityLevel;
import org.springframework.stereotype.Component;

/**
 * Class with methods for processing commands
 */
@Component
public class CommandResolver {
    private static CommandResolver INSTANCE;

    /**
     * Method gets an instance of the class object
     *
     * @return an instance of the class object
     */
    public static CommandResolver getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new CommandResolver();
        }
        return INSTANCE;
    }

    /**
     * Method gets command
     *
     * @param commandName passed command
     * @return command
     */
    public Class<? extends ICommand> getCommand(String commandName) {
        Class<? extends ICommand> command = CommandName.valueOf(commandName.toUpperCase()).getCommand();
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