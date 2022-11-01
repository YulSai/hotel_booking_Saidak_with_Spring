$(document).ready(function () {
    let totalPages = 1;

    function fetchRooms(startPage) {
        //get data from Backend's REST API
        $.ajax({
            type: "GET",
            url: "/api/rooms",
            data: {
                page: startPage,
                size: 10
            },
            success: function (rooms) {
                $('#roomsTable tbody').empty();
                // add table rows
                rooms.content.forEach((room, index) => {
                    $("tbody").append($(`<tr>
                     <td>${index + 1}</td>
                     <td><a href="/rooms/${room.id}">${room.number}</a></td>
                        <td>${room.type}</td>
                        <td>${room.capacity}</td>
                        <td>${room.status}</td>
                        <td>${room.price}</td>
                        <td><a href="/rooms/update/${room.id}">${room_update}</a></td>
                     </tr>`));
                });

                if ( !$('ul.pagination').empty()){

                }

                if ($('ul.pagination li').length - 2 !== rooms.totalPages) {
                    buildPagination(rooms);
                }
             },
            error: function (e) {
                alert("ERROR: ", e);
                console.log("ERROR: ", e);
            }
        });
    }

    function buildPagination(rooms) {
        totalPages = rooms.totalPages;
        let pageNumber = rooms.pageable.pageNumber;
        const numLinks = 10;

        // print 'previous' link only if not on page one
        let first = '';
        let prev = '';
        if (pageNumber > 0) {
            if (pageNumber !== 0) {
                first = '<li class="page-item"><a class="page-link">« First</a></li>';
            }
            prev = '<li class="page-item"><a class="page-link">‹ Prev</a></li>';
        } else {
            prev = ''; // on the page one, don't show 'previous' link
            first = ''; // nor 'first page' link
        }

        // print 'next' link only if not on the last page
        let next = '';
        let last = '';
        if (pageNumber < totalPages) {
            if (pageNumber !== totalPages - 1) {
                next = '<li class="page-item"><a class="page-link">Next ›</a></li>';
                last = '<li class="page-item"><a class="page-link">Last »</a></li>';
            }
        } else {
            next = ''; // on the last page, don't show 'next' link
            last = ''; // nor 'last page' link
        }

        let start = pageNumber - (pageNumber % numLinks) + 1;
        let end = start + numLinks - 1;
        end = Math.min(totalPages, end);
        let pagingLink = '';

        for (let i = start; i <= end; i++) {
            if (i == pageNumber + 1) {
                pagingLink += '<li class="page-item active"><a class="page-link"> ' + i + ' </a></li>';
                // no need to create a link to current page
            } else {
                pagingLink += '<li class="page-item"><a class="page-link"> ' + i + ' </a></li>';
            }
        }

        // return the page navigation link
        pagingLink = first + prev + pagingLink + next + last;

        if ( !$('ul.pagination').empty()){
            $('ul.pagination').clear();
        }
        $("ul.pagination").append(pagingLink);
    }

    $(document).on("click", "ul.pagination li a", function () {
        let data = $(this).attr('data');
        let val = $(this).text();
        let currentActive;
        let startPage;
        console.log('val: ' + val);

        // click on the NEXT tag

        if (val.toUpperCase() === "« FIRST") {
            currentActive = $("li.active");
            fetchRooms(0);
            $("li.active").removeClass("active");
            // add .active to next-pagination li
            currentActive.next().addClass("active");
        } else if (val.toUpperCase() === "LAST »") {
            startPage = totalPages - 1;
            fetchRooms(startPage);
            $("li.active").removeClass("active");
        } else if (val.toUpperCase() === "NEXT ›") {
            let activeValue = parseInt($("ul.pagination li.active").text());
            if (activeValue < totalPages) {
                currentActive = $("li.active");
                startPage = activeValue;
                fetchRooms(startPage);
                // remove .active class for the old li tag
                $("li.active").removeClass("active");
                // add .active to next-pagination li
                currentActive.next().addClass("active");
            }
        } else if (val.toUpperCase() === "‹ PREV") {
            let activeValue = parseInt($("ul.pagination li.active").text());
            if (activeValue > 1) {
                // get the previous page
                startPage = activeValue - 2;
                fetchRooms(startPage);
                currentActive = $("li.active");
                currentActive.removeClass("active");
                // add .active to previous-pagination li
                currentActive.prev().addClass("active");
            }
        } else {
            startPage = parseInt(val - 1);
            fetchRooms(startPage);
            // add focus to the li tag
            $("li.active").removeClass("active");
            $(this).parent().addClass("active");
        }
    });

    (function () {
        // get first-page at initial time
        fetchRooms(0);
    })();
});

