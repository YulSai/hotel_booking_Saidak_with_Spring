package com.company.hotel_booking.controller.command.util;

import com.company.hotel_booking.aspects.logging.annotations.LogInvocation;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

/**
 * Class with methods for object Paging
 */
@Component
public class PagingUtil {

    /**
     * Method gets Pageable
     *
     * @param req    HttpServletRequest
     * @param sortBy properties for sorting
     * @return Paging
     */
    @LogInvocation
    public Pageable getPaging(HttpServletRequest req, String sortBy) {
        String limitStr = req.getParameter("limit");
        String offsetStr = req.getParameter("page");

        int limit = limitStr == null ? 10 : Integer.parseInt(limitStr);
        int page = offsetStr == null ? 1 : Integer.parseInt(offsetStr);

        Sort sort = Sort.by(Sort.Direction.ASC, sortBy);
        return PageRequest.of(page - 1, limit, sort);
    }

    /**
     * Method sets the total number of pages and the current page
     *
     * @param req     HttpServletRequest
     * @param dtoPage Page of entities meeting the paging restriction
     */
    @LogInvocation
    public void setTotalPages(HttpServletRequest req, Page dtoPage) {
        long totalPages = dtoPage.getTotalPages();
        String command = req.getParameter("command");
        req.setAttribute("current_page", dtoPage.getPageable().getPageNumber() + 1);
        req.setAttribute("total_pages", totalPages);
        req.setAttribute("command", command);
    }
}