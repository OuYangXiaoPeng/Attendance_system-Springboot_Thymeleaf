document.addEventListener("DOMContentLoaded", function () {
    const navLinks = document.querySelectorAll(".sidebar ul li a");
    const iframe = document.getElementById("content-frame");
    const breadcrumb = document.getElementById("breadcrumb");
    const welcomePanel = document.getElementById("welcome-panel");

    navLinks.forEach(link => {
        link.addEventListener("click", function (e) {
            e.preventDefault();

            // 去掉所有高亮
            navLinks.forEach(l => l.classList.remove("active"));
            this.classList.add("active");

            // 隐藏欢迎面板，显示 iframe
            welcomePanel.style.display = "none";
            iframe.style.display = "block";

            // 在 iframe 里加载目标页面
            const url = this.getAttribute("href");
            iframe.setAttribute("src", url);

            // 更新面包屑
            updateBreadcrumb(this);
        });
    });

    // 初始状态：显示欢迎面板
    breadcrumb.innerHTML = `当前位置 <span>&gt;</span> 首页`;

    function updateBreadcrumb(link) {
        const parentUl = link.closest("ul");
        let roleName = "";
        if (parentUl.hasAttribute("th:if")) {
            if (parentUl.getAttribute("th:if").includes("STUDENT")) roleName = "学生功能";
            if (parentUl.getAttribute("th:if").includes("TEACHER")) roleName = "老师功能";
            if (parentUl.getAttribute("th:if").includes("ADMIN")) roleName = "管理员功能";
        }
        breadcrumb.innerHTML = `当前位置<span>${roleName}</span> <span>&gt;</span> <span class="active">${link.textContent}</span>`;
    }
});

