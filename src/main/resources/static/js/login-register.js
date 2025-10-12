$(function () {
    // --- 表单 label 动效 ---
    $('.form').find('input, textarea').on('keyup blur focus', function (e) {
        const $this = $(this);
        const label = $this.prev('label');

        if (e.type === 'keyup') {
            label.toggleClass('active highlight', $this.val() !== '');
        } else if (e.type === 'blur') {
            label.removeClass('highlight');
            if ($this.val() === '') label.removeClass('active');
        } else if (e.type === 'focus') {
            if ($this.val() !== '') label.addClass('highlight');
        }
    });

    // --- Tab 切换 ---
    $('.tab a').on('click', function (e) {
        e.preventDefault();
        $(this).parent().addClass('active').siblings().removeClass('active');
        const target = $(this).attr('href');
        $('.tab-content > div').hide();
        $(target).fadeIn(400);
    });

    // 默认显示登录页
    $('.tab-content > div').hide();
    $('#login').show();

    // --- 用户名前缀逻辑 ---
    const roleSelect = $('#role');
    const usernameInput = $('#username');

    function updateUsernamePrefix() {
        let val = usernameInput.val().replace(/^[ST]/, "");
        if (roleSelect.val() === "STUDENT") {
            usernameInput.val("S" + val);
        } else if (roleSelect.val() === "TEACHER") {
            usernameInput.val("T" + val);
        }
    }

    roleSelect.on("change", updateUsernamePrefix);
    usernameInput.on("blur", updateUsernamePrefix);

    // --- 用户名校验 ---
    const registerButton = $('#registerButton');
    const errorDiv = $('<div style="margin-top:5px; font-size:13px;"></div>')
        .insertAfter(usernameInput);

    let debounceTimer;
    function checkUsername() {
        clearTimeout(debounceTimer);
        debounceTimer = setTimeout(() => {
            const username = usernameInput.val().trim();
            if (!username) {
                errorDiv.text('');
                return;
            }

            $.ajax({
                url: '/check-username',
                type: 'GET',
                data: { username },
                success: function (res) {
                    if (res.exists) {
                        errorDiv.css("color", "red").text('用户名已存在');
                    } else {
                        errorDiv.css("color", "green").text('用户名可用');
                    }
                },
                error: function () {
                    errorDiv.css("color", "red").text('检查失败，请稍后再试');
                }
            });
        }, 300); // 防抖 300ms
    }

    usernameInput.on('input blur', checkUsername);

    // --- 表单提交前检查 ---
    $('form[th\\:action="@{/register}"]').on('submit', function () {
        if (errorDiv.text() === '用户名已存在') {
            alert('请先修改用户名');
            return false;
        }
    });
});
