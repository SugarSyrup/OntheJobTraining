function Modal (btn, overlay, no_btn) {
    const modalOpen = (e) => {
        e.preventDefault();
        overlay.showModal();
    }
    const modalClose = (e) => {
        e.preventDefault();
        overlay.close();
    }

    btn.addEventListener('click', modalOpen);
    no_btn.addEventListener('click', modalClose);
}
