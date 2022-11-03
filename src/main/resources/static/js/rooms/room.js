$(document).ready(function () {
    let id = document.URL.substring(document.URL.lastIndexOf('/') + 1);

    function getRoomById(id) {
        $.ajax({
            url: "/api/rooms/" + id,
            type: 'GET',
            dataType: 'json',
            success: function (room) {
                $('#userTable tbody').empty();
                roomToFields(room);
                linksToRows(room);
            },

            error: function (request, message, error) {
                handleException(request, message, error);
            }
        });
    }

    function roomToFields(room) {
        return $("tbody").append($(`<tr>
        <tr><th>${room_field}</th>
        <th>${room_value}</th></tr>
        <tr><td className="name">${room_number}</td>
        <td className="sign">${room.number}</td></tr>
        <tr><td className="name">${room_type}</td>
        <td className="sign">${room.type}</td></tr>
        <tr><td className="name">${room_capacity}</td>
        <td className="sign">${room.capacity}</td></tr>
        <tr><td className="name">${room_status}</td>
        <td className="sign">${room.status}</td></tr>
        <tr><td className="name">${room_price}USD</td>
        <td className="sign">${room.price}</td></tr>
        </tr>`));
    }

    function linksToRows(room) {
        $("div.links").append($(
            `<ul><li><a href="/rooms/update/${room.id}">${room_update}</a></li></ul>`));
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
        getRoomById(id);
    })();
});