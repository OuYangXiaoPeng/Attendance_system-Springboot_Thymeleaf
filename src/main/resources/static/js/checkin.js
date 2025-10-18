$(document).ready(function () {
    $("#checkinForm").on("submit", function (e) {
        e.preventDefault(); // 阻止表单默认提交

        $.ajax({
            url: '/attendance/checkin',
            type: 'POST',
            data: $(this).serialize(),
            success: function (res) {

                if (res.success === true) {
                    $("#message")
                        .css("color", "green")
                        .text(res.message)
                        .css("opacity", "1");

                    setTimeout(function () {
                        location.reload();
                    }, 2000);
                } else {
                    $("#message")
                        .css("color", "red")
                        .text(res.message)
                        .css("opacity", "1");

                    setTimeout(function () {
                        location.reload();
                    }, 2000);
                }
            },
            error: function () {
                $("#message")
                    .css("color", "red")
                    .text("请求失败，请稍后再试！")
                    .css("opacity", "1");
            }
        });
    });
});
