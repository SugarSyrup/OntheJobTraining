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

noBtn.addEventListener('click', modalClose)
