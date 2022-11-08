$(document).ready(function () {
    let id = document.URL.substring(document.URL.lastIndexOf('/') + 1);

    function getUserById(id) {
        $.ajax({
            url: "/api/users/" + id,
            type: 'GET',
            dataType: 'json',
            success: function (user) {
                $('#userTable tbody').empty();
                userToFields(user);
                linksToRows(user);
            },

            error: function (request, message, error) {
                handleException(request, message, error);
            }
        });
    }

    function userToFields(user) {
        $("tbody").append($(
            `<tr>
            <tr><th><img src="/images/avatars/${user.avatar}" alt="${user.avatar}" class="avatar"></th></tr>
            <tr> <th>${user_field}</th>
            <th>${user_value}</th> </tr>
            <tr> <td>${user_first_name}</td>
            <td>${user.firstName}</td> </tr>
            <tr> <td>${user_last_name}</td>
            <td>${user.lastName}</td> </tr>
            <tr> <td>${user_email}</td>
            <td>${user.email}</td> </tr>
            <tr> <td>${user_phone}</td>
            <td>${user.phoneNumber}</td> </tr>
            <tr> <td>${user_role}</td>
            <td>${user.role.toString().toLowerCase()}</td><tr> 
            <tr><td>${user_status}</td>
            <td>${user.block}</td><tr> 
            </tr>
            </tr>`));
    }

    function linksToRows(user) {
        let $row = "";
        if (user_role_session == 'ADMIN') {
            $row = $(`
                <button class="btn" class="edit">${user_update_role}</button>
                <button class="btn" class="delete">${user_delete}</button>`);
            $row.find(".edit").on("click", () => window.location.href = `/users/update_role/${user.id}`);
            $row.find(".delete").on("click", () => $.ajax({
                url: `/api/users/${user.id}`,
                type: "DELETE",
                success: refresh
            }));

        } else if (user_role_session == 'CLIENT') {
            $row = $(`
                <button class="btn" class="update">${user_update}</button>
                <button class="btn" class="change_password">${user_change_password}</button>`);
            $row.find(".update").on("click", () => window.location.href = `/users/update/${user.id}`);
            $row.find(".change_password").on("click", () => window.location.href = `/users/change_password/${user.id}`);
        }
         $("div.links").append($row);
    }

    function handleException(request, message, error) {
        let msg = "";
        msg += request.status + "\n";
        msg += request.statusText + "\n";
        if (request.responseJSON != null) {
            msg += request.responseJSON.Message + "\n";
        }
        alert(msg);
    }

    (function () {
        getUserById(id);
    })();
});