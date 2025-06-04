document.addEventListener('DOMContentLoaded', () => {
    // Maneja el clic en el contenedor de la imagen para abrir la imagen grande
    document.querySelectorAll('.librarymanager-item').forEach(item => {
        item.addEventListener('click', (e) => {
            // Verifica si el clic fue en un enlace de descarga
            if (!e.target.classList.contains('download-link')) {
                window.location.href = item.getAttribute('data-link');
            }
        });
    });
});

document.addEventListener('DOMContentLoaded', () => {
    // Maneja el clic en la imagen para mostrarla en grande
    const overlay = document.createElement('div');
    overlay.className = 'overlay';
    document.body.appendChild(overlay);

    const overlayImg = document.createElement('img');
    overlay.appendChild(overlayImg);

    document.querySelectorAll('.librarymanager-image-container img').forEach(img => {
        img.addEventListener('click', (e) => {
            overlayImg.src = e.target.src;
            overlay.classList.add('show');
            e.stopPropagation(); // Evita que el clic en la imagen redirija al plugin
        });
    });

    overlay.addEventListener('click', () => {
        overlay.classList.remove('show');
    });
});
