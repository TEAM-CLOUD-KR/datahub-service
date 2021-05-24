function line_bar_chart(canvas, labels, dataset, type) {
    const data = {
        labels: labels,
        datasets: dataset
    };
    const config = {
        type: type,
        data: data,
        options: {}
    };
    const myChart = new Chart(
        canvas,
        config
    );
}


function circle_chart(canvas, labels, dataset, type) {
    const data = {
        labels: labels,
        datasets: dataset
    };
    const config = {
        type: type,
        data: data,
    };

    const myChart = new Chart(
        canvas,
        config
    );
}

function stepped_line(canvas, labels, datasets) {
    const data = {
        labels: labels,
        datasets: datasets
    };
    const config = {
        type: 'line',
        data: data,
        options: {
            responsive: true,
            interaction: {
                intersect: false,
                axis: 'x'
            }
        }
    };
    const actions = [
        {
            name: 'Step: false (default)',
            handler: (chart) => {
                chart.data.datasets.forEach(dataset => {
                    dataset.stepped = false;
                });
                chart.update();
            }
        },
        {
            name: 'Step: true',
            handler: (chart) => {
                chart.data.datasets.forEach(dataset => {
                    dataset.stepped = true;
                });
                chart.update();
            }
        },
        {
            name: 'Step: before',
            handler: (chart) => {
                chart.data.datasets.forEach(dataset => {
                    dataset.stepped = 'before';
                });
                chart.update();
            }
        },
        {
            name: 'Step: after',
            handler: (chart) => {
                chart.data.datasets.forEach(dataset => {
                    dataset.stepped = 'after';
                });
                chart.update();
            }
        },
        {
            name: 'Step: middle',
            handler: (chart) => {
                chart.data.datasets.forEach(dataset => {
                    dataset.stepped = 'middle';
                });
                chart.update();
            }
        }
    ];
    const myChart = new Chart(
        canvas,
        config
    );
}

function combo_chart(canvas, labels, dataset) {
    const data = {
        labels: labels,
        datasets: dataset
    };
    const config = {
        type: 'bar',
        data: data,
        options: {
            responsive: true,
            plugins: {
                legend: {
                    position: 'top',
                },
                title: {
                    display: true,
                    text: 'Chart.js Combined Line/Bar Chart'
                }
            }
        },
    };

    const myChart = new Chart(
        canvas,
        config
    );
}

function radar_chart(canvas, label, dataset) {
    const data = {
        labels: label,
        datasets: dataset
    };
    const config = {
        type: 'radar',
        data: data,
        options: {
            responsive: true
        },
    };
    const myChart = new Chart(
        canvas,
        config
    );
}


function polarArea_chart(canvas, label, dataset) {
    const data = {
        labels: label,
        datasets: dataset
    };
    const config = {
        type: 'polarArea',
        data: data,
        options: {
            responsive: true
        },
    };
    const myChart = new Chart(
        canvas,
        config
    );
}