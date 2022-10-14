package com.company.hotel_booking.controller;

import com.company.hotel_booking.aspects.logging.annotations.LogInvocation;
import com.company.hotel_booking.aspects.logging.annotations.NotFoundEx;
import com.company.hotel_booking.controller.command.api.CommandName;
import com.company.hotel_booking.controller.command.api.ICommand;
import com.company.hotel_booking.controller.command.CommandResolver;
import com.company.hotel_booking.exceptions.ExceptionsHandler;
import com.company.hotel_booking.exceptions.NotFoundException;
import com.company.hotel_booking.managers.MessageManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.Locale;

/**
 * Class for processing HttpServletRequest "/controller"
 */
@WebServlet("/controller")
@MultipartConfig(maxFileSize = GeneralController.MB * 10, maxRequestSize = GeneralController.MB * 100)
@Controller
@RequiredArgsConstructor
public class GeneralController extends HttpServlet {
    public static final int MB = 1024 * 1024;
    public static final String REDIRECT = "redirect:";
    private CommandResolver commandResolver;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processDo(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processDo(req, resp);
    }

    private void processDo(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        MessageManager messageManger = getLocale(req);
        String commandName = req.getParameter("command");
        validateCommandName(commandName);
        Class<? extends ICommand> commandDefinition = commandResolver.getCommand(commandName);
        ICommand command = ContextListener.context.getBean(commandDefinition);
        String page;
        try {
            page = command.execute(req);
        } catch (Exception e) {
            ExceptionsHandler exceptionsHandler = new ExceptionsHandler();
            page = exceptionsHandler.handleException(req, resp, e);
        }
        if (page.startsWith(REDIRECT)) {
            setMessageSession(req);
            resp.sendRedirect(req.getContextPath() + "/" + page.substring(REDIRECT.length()));
        } else {
            req.getRequestDispatcher(page).forward(req, resp);
        }
    }

    /**
     * Method checks for a message in the request and, if it's not null, sets it into the session
     *
     * @param req HttpServletRequest
     */
    private void setMessageSession(HttpServletRequest req) {
        String message = (String) req.getAttribute("message");
        if (message != null) {
            HttpSession session = req.getSession();
            session.setAttribute("message", message);
        }
    }

    /**
     * Method gets localization information passes it to MessageManger
     *
     * @param req HttpServletRequest
     * @return object MessageManger
     */
    private MessageManager getLocale(HttpServletRequest req) {
        HttpSession session = req.getSession();
        String language = (String) session.getAttribute("language");
        return new MessageManager(new Locale(language));
    }

    /**
     * Method validates command names
     *
     * @param commandName command name
     */
    @NotFoundEx
    public void validateCommandName(String commandName) {
        if (commandName == null || !CommandName.contains(commandName.toUpperCase())) {
            throw new NotFoundException();
        }
    }

    @Override
    @LogInvocation
    public void init() {
        commandResolver = ContextListener.context.getBean(CommandResolver.class);
    }

    @Override
    @LogInvocation
    public void destroy() {
    }
}