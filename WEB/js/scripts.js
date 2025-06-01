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

    // Maneja el cambio de idioma
    const languageSelect = document.getElementById('language-select');
    if (languageSelect) {
        languageSelect.addEventListener('change', (event) => {
            const selectedLanguage = event.target.value;
            const targetPage = selectedLanguage === 'es' ? 'index_es.html' : 'index_en.html';
            window.location.href = targetPage;
        });
    }

    // MineBank pestañas click tabs
    function openTab(tabId) {
        // Ocultar todos los contenidos de las pestañas
        document.querySelectorAll('.tab-content').forEach(tab => {
            tab.classList.remove('active');
        });

        // Eliminar la clase activa de todos los botones de pestañas
        document.querySelectorAll('.tab-button').forEach(button => {
            button.classList.remove('active');
        });

        // Mostrar el contenido de la pestaña seleccionada
        const tabContent = document.getElementById(tabId);
        if (tabContent) {
            tabContent.classList.add('active');
        }

        // Marcar el botón de pestaña como activo
        const activeButton = document.querySelector(`.tab-button[data-tab="${tabId}"]`);
        if (activeButton) {
            activeButton.classList.add('active');
        }
    }

    // Inicializar la pestaña activa por defecto
    openTab('index');

    // Añadir eventos a los botones de pestañas
    document.querySelectorAll('.tab-button').forEach(button => {
        button.addEventListener('click', () => {
            const tabId = button.getAttribute('data-tab');
            openTab(tabId);
        });
    });
});

function goBack() {
    window.history.back();
}
