$(document).ready(function () {
    // 表格搜索功能
    $("#searchInput").on("keyup", function () {
        let value = $(this).val().toLowerCase();
        $("#Table tbody tr").filter(function () {
            $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
        });
    });

    // 行点击高亮
    $("#Table tbody tr").on("click", function () {
        $(this).addClass("selected").siblings().removeClass("selected");
    });
});
