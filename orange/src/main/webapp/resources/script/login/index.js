$(document).ready(function () {
    $("input[value='User Id']").focus(function () {
       this.value = '';
    })

    $("input[value='User Id']").blur(function () {
        if (this.value == '') {
            $(this).val('User');
        }
    })

    $("input[type='submit']").click(function () {
        $("#loginForm").submit();
    })

})