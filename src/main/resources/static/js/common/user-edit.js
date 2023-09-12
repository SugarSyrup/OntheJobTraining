const yesBtn = document.querySelector('.modal_yes');
const noBtn = document.querySelector('.modal_no');

yesBtn.addEventListener('click', (e) => {
    e.preventDefault();

    fetch(`http://${window.location.host}/api/user-edit`, {
        method: 'DELETE',
        redirect: 'follow'
    }).then((response) => {
        location.href = response.url;
    }).catch((error) => {
        console.log(error);
    })
})



/* Modal  */
const btn = document.querySelector('#modal_open');
const overlay = document.querySelector('.modal_wrapper');
Modal(btn, overlay, noBtn);
