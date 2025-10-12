$(document).ready(function () {
    $("#leaveForm").on("submit", function (e) {
        e.preventDefault(); // 阻止表单默认提交

        $.ajax({
            url: $(this).attr("action"),
            type: "POST",
            data: $(this).serialize(),
            success: function (res) {
                $("#message")
                    .css("color", "green")
                    .text("提交成功，请等待审批！")
                    .css("opacity", 1);

                setTimeout(function () {
                    location.reload();
                }, 2000);
            },
            error: function () {
                $("#message")
                    .css("color", "red")
                    .text("提交失败，请稍后再试！")
                    .css("opacity", 1);

                setTimeout(function () {
                    location.reload();
                }, 2000);
            }
        });
    });
});
