//$.get("/api/users/{id}", id, userToFields(user), linksToRows());

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
        return $("tbody").append($(
            `<tr><tr><th><img src="/images/avatars/${user.avatar}" alt="${user.avatar}" class="avatar"></th></tr>
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
            <td>${user.role.toString().toLowerCase()}</td> </tr>`));
    }

    function linksToRows(user) {
        if (user_role_session == 'ADMIN') {
            $("div.links").append($(
                `<ul>
                <li><a href="/users/update_role/${user.id}">${user_update_role}</a></li>
                <li><a href="/users/delete/${user.id}"> ${user_delete}</a></li>
        </ul>`));
        } else if (user_role_session == 'CLIENT') {
            $("div.links").append($(
                `<ul>
                <li><a href="/users/update/${user.id}">${user_update}</a></li>
                <li><a href="/users/change_password/${user.id}">${user_change_password}</a></li>
        </ul>`));
        }
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