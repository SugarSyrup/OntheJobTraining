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

/* Password Check */
const passwordValue = document.querySelector("#password");
const passwordCheckValue = document.querySelector("#password_check");
const editForm = document.querySelector('.login_wrapper');

editForm.addEventListener('submit', (e) => {
    const PASSWORD_REGEXP = new RegExp("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,16}$");
    if(!PASSWORD_REGEXP.test(passwordValue.value)) {
        e.preventDefault();
        document.querySelector('.errmsg').innerText = "비밀번호는 최소 8자리에서 최대 16자리 숫자,영문,특수문자를 포함해야 합니다.";
        passwordValue.addEventListener('input', (e) => {
            if(PASSWORD_REGEXP.test(passwordValue.value)) {
                document.querySelector('.errmsg').innerText = "";
            }
        })
    }
    else if(passwordValue.value !== passwordCheckValue.value) {
        e.preventDefault();
        document.querySelector('.errmsg').innerText = "비밀번호는 반드시 일치해야 합니다.";
        passwordCheckValue.addEventListener('input', (e) => {
            if(passwordValue.value === passwordCheckValue.value) {
                document.querySelector('.errmsg').innerText = "";
            }
        })
    }
})