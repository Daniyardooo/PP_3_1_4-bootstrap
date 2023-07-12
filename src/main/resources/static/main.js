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
            $('#password').val(user.password);
            $('#role').val(user.roles);


            for (var i = 0; i < user.roles.length; i++) {
                var role = user.roles[i];
                if (role.name === 'ROLE_ADMIN') {
                    $('#adminRole').prop('checked', true);
                }
                if (role.name === 'ROLE_USER') {
                    $('#userRole').prop('checked', true);
                }
                // Добавьте другие условия, если есть больше ролей
            }

        });

        $('.myForm #editModal').modal();
    });
});