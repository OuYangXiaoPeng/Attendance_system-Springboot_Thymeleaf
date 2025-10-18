$(function() {
    $.get("/admin/dashboardData", function(res) {
        if (res.error) {
            alert("您没有权限访问该页面！");
            return;
        }

        renderPieChart('userRoleChart', '用户角色分布', res.userRole);
        renderPieChart('leaveStatusChart', '请假状态统计', res.leaveStatus);
        renderBarChart('attendanceLocationChart', '打卡地点分布', res.attendanceLocation);
        renderPieChart('attendanceMethodChart', '打卡方式分布', res.attendanceMethod);
    });
});

function renderPieChart(domId, title, data) {
    const chart = echarts.init(document.getElementById(domId));
    const option = {
        backgroundColor: 'transparent',
        tooltip: { trigger: 'item' },
        legend: {
            bottom: '0%',
            textStyle: { color: '#cbd5e1' }
        },
        series: [{
            name: title,
            type: 'pie',
            radius: ['40%', '70%'],
            avoidLabelOverlap: false,
            itemStyle: {
                borderRadius: 10,
                borderColor: '#0f172a',
                borderWidth: 2
            },
            label: {
                color: '#cbd5e1',
                formatter: '{b}: {c} ({d}%)'
            },
            data: Object.keys(data).map(k => ({ name: k, value: data[k] })),
            animationEasing: 'elasticOut',
            animationDelay: idx => idx * 100
        }]
    };
    chart.setOption(option);
}

function renderBarChart(domId, title, data) {
    const chart = echarts.init(document.getElementById(domId));
    const option = {
        backgroundColor: 'transparent',
        tooltip: {},
        xAxis: {
            type: 'category',
            data: Object.keys(data),
            axisLabel: { color: '#e2e8f0' },
            axisLine: { lineStyle: { color: '#475569' } }
        },
        yAxis: {
            type: 'value',
            axisLabel: { color: '#e2e8f0' },
            splitLine: { lineStyle: { color: '#334155' } }
        },
        series: [{
            data: Object.values(data),
            type: 'bar',
            barWidth: '50%',
            itemStyle: {
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                    { offset: 0, color: '#38bdf8' },
                    { offset: 1, color: '#0ea5e9' }
                ])
            },
            label: {
                show: true,
                position: 'top',
                color: '#f8fafc'
            }
        }],
        animationDuration: 1000
    };
    chart.setOption(option);
}
