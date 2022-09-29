package com.company.hotel_booking.controller.command.util;

import com.company.hotel_booking.service.api.IAbstractService;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Class with methods for object Paging
 */
public enum PagingUtil {
    INSTANCE;

    /**
     * Method gets Paging
     *
     * @param req HttpServletRequest
     * @return Paging
     */
    public Paging getPaging(HttpServletRequest req) {
        String limitStr = req.getParameter("limit");
        String offsetStr = req.getParameter("page");

        int limit = limitStr == null ? 10 : Integer.parseInt(limitStr);
        long page = offsetStr == null ? 1 : Long.parseLong(offsetStr);
        long offset = (page - 1) * limit;
        return new Paging(limit, offset, page);
    }

    /**
     * Method gets total pages
     *
     * @param totalEntities count row
     * @param limit         max number of lines per page
     * @return total pages
     */
    public long getTotalPages(long totalEntities, int limit) {
        long totalPages = totalEntities / limit;
        totalPages += (totalEntities - (totalPages * limit) > 0 ? 1 : 0);
        return totalPages;
    }

    /**
     * Method sets the total number of pages and the current page
     *
     * @param req     HttpServletRequest
     * @param paging  object Paging
     * @param service instance of object IAbstractService
     */
    public void setTotalPages(HttpServletRequest req, Paging paging, IAbstractService service) {
        long totalEntities = service.countRow();
        long totalPages = getTotalPages(totalEntities, paging.getLimit());
        String command = req.getParameter("command");
        req.setAttribute("current_page", paging.getPage());
        req.setAttribute("total_pages", totalPages);
        req.setAttribute("command", command);
    }
}