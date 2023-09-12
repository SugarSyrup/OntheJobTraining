const logout_btn = document.querySelector(".logout");

logout_btn.addEventListener('click', (e) => {
    e.preventDefault();

    fetch(`http://${window.location.host}/api/logout`, {
        method: 'POST',
        redirect: 'follow'
    }).then((response) => {
        location.href = response.url;
    }).catch((error) => {
        console.log(error);
    })
})