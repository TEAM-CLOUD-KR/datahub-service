window.addEventListener('DOMContentLoaded', function () {
    const btn_search = document.getElementById('btn-search');
    const text_search = document.getElementById('text-search');

    btn_search.addEventListener('click', () => {
        console.log(text_search.value);
    });
});