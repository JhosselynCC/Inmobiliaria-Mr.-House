// scrip.js

// $(document).ready(function () {
//     // Manejar el clic en el enlace "Seguir leyendo"
//     $('.read-more-link').on('click', function (e) {
//         e.preventDefault(); // Prevenir el comportamiento predeterminado del enlace
        
//         // Obtener el contenedor de la noticia completa (podría ser un modal u otro elemento)
//         var fullNewsContainer = $(this).closest('.p-4'); // Ajusta esto según tu estructura

//         // Mostrar la noticia completa
//         fullNewsContainer.find('.full-news').slideDown(); // Ajusta esto según tu estructura
//     });
// });


$(document).ready(function () {
    // Manejar el clic en el enlace "Seguir leyendo"
    $('.read-more-link').on('click', function (e) {
        e.preventDefault(); // Prevenir el comportamiento predeterminado del enlace

        // Obtener el contenedor de la noticia completa
        var target = $(this).attr('data-target');
        var fullNewsContainer = $(target);

        // Mostrar la noticia completa
        fullNewsContainer.html($(this).closest('.p-4').find('.full-news').html());
        fullNewsContainer.slideDown();
    });
});
