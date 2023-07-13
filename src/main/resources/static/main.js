/**
 *
 */

$('document').ready(function () {
    $('.table .eBtn').on('click', function (event) {
        event.preventDefault();

        var href = $(this).attr('href');

        $.get(href, function (user, status) {
            $('#id').val(user.id);
            $('#username').val(user.username);
            $('#email').val(user.email);
            $('#name').val(user.name);
            $('#age').val(user.age);
            // $('#password').val(user.password);

            for (var i = 0; i < user.roles.length; i++) {
                var role = user.roles[i];
                console.log(user.roles)
                if (role.name === 'ROLE_ADMIN') {
                    $('#adminRole').prop('checked', true);
                }
                if (role.name === 'ROLE_USER') {
                    $('#userRole').prop('checked', true);
                }
            }
        });

        $('.myForm #editModal').modal();
    });

    $('.table .dBtn').on('click', function (event) {
        event.preventDefault();

        var href = $(this).attr('href');

        $.get(href, function (user, status) {
            $('#deleteId').val(user.id);
            $('#deleteUsername').val(user.username);
            $('#deleteEmail').val(user.email);
            $('#deleteName').val(user.name);
            $('#deleteAge').val(user.age);
            // $('#password').val(user.password);

            for (var i = 0; i < user.roles.length; i++) {
                var role = user.roles[i];
                console.log(user.roles)
                if (role.name === 'ROLE_ADMIN') {
                    $('#deleteAdminRole').prop('checked', true);
                }
                if (role.name === 'ROLE_USER') {
                    $('#deleteUserRole').prop('checked', true);
                }
            }
        });

        $('.myDeleteForm #deleteModal').modal();
    });




});