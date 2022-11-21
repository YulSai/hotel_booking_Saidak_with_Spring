$(function() {

    const options = {
        year: 'numeric',
        month: 'numeric',
        day: 'numeric',
    }

    const date = new Date();

    const today = date.toLocaleString("ru", options).split('.').reverse().join('-');
    document.getElementById("check_in").setAttribute("min", today);

    date.setDate(date.getDate() + 1)

    const tomorrow = date.toLocaleString("ru", options).split('.').reverse().join('-');
    document.getElementById("check_out").setAttribute("min", tomorrow);
});