/* Modal Role Change */
let Modal_CurrentUser;

const RoleBtns = document.querySelectorAll('.role_modal_open');
const RoleOverlay = document.querySelector('.role_overlay');
const RoleModalYes = document.querySelector('.role_modal_yes');
const RoleModalNo = document.querySelector('.role_modal_no');

console.log(RoleModalNo);
RoleBtns.forEach((btn) => {
    Modal(btn, RoleOverlay, RoleModalNo);
    btn.addEventListener('click', () => {
        Modal_CurrentUser = btn.dataset.userno;
        const user_role = btn.dataset.userrole;
        const selectBox = document.getElementById("modal_input");
        if(user_role == "USER") {
            selectBox.options[0].selected = true;
        } else {
            selectBox.options[1].selected = true;
        }

        const userNoInput = document.querySelector("#user_no_input");
        userNoInput.value = Modal_CurrentUser;
    })
})


/* Modal Delete User */
const DeleteBtns = document.querySelectorAll('.delete_modal_open');
const DeleteOverlay = document.querySelector('.delete_overlay');
const DeleteModalYes = document.querySelector('.delete_modal_yes');
const DeleteModalNo = document.querySelector('.delete_modal_no');

console.log(DeleteBtns);
DeleteBtns.forEach((btn) => {
    Modal(btn, DeleteOverlay, DeleteModalNo);
    btn.addEventListener('click', () => {
        Modal_CurrentUser = btn.dataset.userno;
    })
})

DeleteModalYes.addEventListener('click', () => {
    fetch(`http://${window.location.host}/api/user-delete`, {
        method: 'POST',
        headers: {
            "Content-Type": "application/json",
        },
        redirect: 'follow',
        body: JSON.stringify({
            "user_no" : Modal_CurrentUser
        })
    }).then((response) => {
        location.href = response.url;
    }).catch((error) => {
        console.log(error);
    })
})