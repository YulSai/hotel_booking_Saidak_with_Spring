$(function() {
    refresh();

    function refresh() {
        const queryString = $(".query-string").text();
        $.get("/api/users?" + queryString, processUsers);
    }

    function processUsers(page) {
        updateQueryString(page);
        renderTable(page);
        renderPaginationButtons(page);
    }

    function updateQueryString(page) {
        $(".query-string").text(`page=${page.number}&size${page.size}`);
    }

    function renderTable(page) {
        const $tbody = $("tbody");
        $tbody.empty();
        page.content.forEach((user, index) => renderTableRow(user, index, $tbody));
    }

    function renderTableRow(user, index, $tbody) {
        const $row = $(`
			    <tr id="row-${user.id}">
                <td>${index + 1}</td>
				<td><a id="view">${user.firstName}</a></td>
				<td><a id="view"">${user.lastName}</a></td>
				<td>${user.email}</td>
				<td>${user.phoneNumber}</td>
                <td>${user.role.toString().toLowerCase()}</td>
                <td>${user.block}</td>
				<td>
					<button class="btn" id="edit">${user_update_role}</button>
					<button class="btn" id="delete">${user_delete}</button>
				</td>
			</tr>
			`);
        $row.find("#view").on("click", () => window.location.href = `/users/js/${user.id}`);
        $row.find("#edit").on("click", () => window.location.href = `/users/update_role/${user.id}`);
        $row.find("#delete").on("click", () => $.ajax({
            url: `/api/users/${user.id}`,
            type: "DELETE",
            success: refresh
        }));
        $tbody.append($row);
    }

    function renderPaginationButtons(page) {
        $(".pagination button").off();
        const prevPage = Math.max(0, page.number - 1);
        const lastPage = page.totalPages - 1;
        const nextPage = Math.min(lastPage, page.number + 1);
        $("#first").on("click", () => $.get(`/api/users?page=0&size=${page.size}`, processUsers));
        $("#prev").on("click", () => $.get(`/api/users?page=${prevPage}&size=${page.size}`, processUsers));
        $("#current").text(page.number + 1);
        $("#next").on("click", () => $.get(`/api/users?page=${nextPage}&size=${page.size}`, processUsers));
        $("#last").on("click", () => $.get(`/api/users?page=${lastPage}&size=${page.size}`, processUsers));
    }
});