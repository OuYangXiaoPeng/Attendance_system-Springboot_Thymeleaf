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
    const errorDiv = $('<div style="margin-top:5px; font-size:13px;"></div>')
        .insertAfter(usernameInput);

    let debounceTimer;
    function checkUsername() {
        clearTimeout(debounceTimer);
        debounceTimer = setTimeout(() => {
            const username = usernameInput.val().trim();

            // 空输入不提示
            // if (!username) {
            //     errorDiv.text('');
            //     return;
            // }

            // 检查是否只有 S 或 T
            if (/^[ST]$/.test(username)) {
                errorDiv.css("color", "red").text("请输入完整的用户名（例如 S2025XX或者T2025XX）");
                return;
            }

            // 检查是否只含字母和数字
            if (!/^[A-Za-z0-9]+$/.test(username)) {
                errorDiv.css("color", "red").text("用户名只能包含字母和数字");
                return;
            }

            // AJAX 检查用户名是否存在
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

    $('form[action="/register"]').on('submit', function () {
        const errorText = errorDiv.text();
        const password = $('input[name="password"]').val().trim();

        // 1. 检查用户名错误提示
        if (
            errorText === '用户名已存在' ||
            errorText === '用户名只能包含字母和数字' ||
            errorText === '请输入完整的用户名（例如 S2025XX或者T2025XX）'
        ) {
            alert('请修改用户名后再提交');
            return false;
        }

        // 2. 检查密码长度
        if (password.length < 4) {
            alert('密码必须至少 4 位');
            return false;
        }

        // 允许提交
        return true;
    });

});
