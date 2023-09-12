const btn = document.querySelector('#modal_open');
const overlay = document.querySelector('.modal_wrapper');

const modalOpen = (e) => {
    e.preventDefault();
    overlay.showModal();
}

const modalClose = (e) => {
    e.preventDefault();
    overlay.close();
}

btn.addEventListener('click', modalOpen);