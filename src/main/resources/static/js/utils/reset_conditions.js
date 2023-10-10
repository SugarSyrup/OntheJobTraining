const main_link = document.querySelector(".main_link");
const equipment_link = document.querySelector(".equipment_link");
const users_link = document.querySelector(".users_link");

equipment_link.addEventListener('click', (e) => {
    fetch(`http://${window.location.host}/api/equipment/reset`, {
        method: 'POST',
        redirect: 'follow'
    }).then((response) => {
        location.href = response.url;
    }).catch((error) => {

    })
})

users_link.addEventListener('click', (e) => {
    fetch(`http://${window.location.host}/api/users/reset`, {
        method: 'POST',
        redirect: 'follow'
    }).then((response) => {
        location.href = response.url;
    }).catch((error) => {
    })
})


const reset_button = document.querySelector('.reset_button')

reset_button.addEventListener('click', (e) => {
    e.preventDefault();
    fetch(`http://${window.location.host}/api${window.location.pathname}/reset`, {
        method: 'POST',
        redirect: 'follow'
    }).then((response) => {
        location.href = response.url;
    }).catch((error) => {

    })
})