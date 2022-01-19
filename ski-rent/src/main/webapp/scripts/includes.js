$(function () {
    const includes = $('[data-include]');
    $.each(includes, function () {
        const file = 'templates/' + $(this).data('include') + '.html';
        $(this).load(file)
    })
})