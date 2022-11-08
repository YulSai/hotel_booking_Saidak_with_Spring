package com.company.hotel_booking.web.controllers.utils;

import com.company.hotel_booking.utils.aspects.logging.annotations.LogInvocation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Class with methods for object Paging
 */
@Component
public class PagingUtil {

    /**
     * Method gets Pageable for REST API
     *
     * @param req    HttpServletRequest
     * @param sortBy properties for sorting
     * @return Paging
     */
    @LogInvocation
    public Pageable getPagingRest(HttpServletRequest req, String sortBy) {
        String limitStr = req.getParameter("limit");
        String offsetStr = req.getParameter("page");

        int limit = limitStr == null ? 10 : Integer.parseInt(limitStr);
        int page = offsetStr == null ? 0 : Integer.parseInt(offsetStr);

        Sort sort = Sort.by(Sort.Direction.ASC, sortBy);
        return PageRequest.of(page, limit, sort);
    }

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
    @GetMapping("/${page}")
    public void setTotalPages(HttpServletRequest req, Page dtoPage, String command) {
        long totalPages = dtoPage.getTotalPages();
        req.setAttribute("current_page", dtoPage.getPageable().getPageNumber() + 1);
        req.setAttribute("total_pages", totalPages);
        req.setAttribute("command", command);
    }
}