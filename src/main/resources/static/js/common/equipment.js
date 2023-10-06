/* Register Equipment Change Modal */

const RegisterBtn = document.querySelector('.register_modal_open');
const RegisterOverlay = document.querySelector('.register_overlay');
const RoleModalYes = document.querySelector('.register_modal_yes');
const RegisterModalNo = document.querySelector('.register_modal_no');

Modal(RegisterBtn, RegisterOverlay, RegisterModalNo);

/* Duplicated Not Button */
const duplicatedNotBtn = document.querySelector(".duplicated_not_button");
const nameInput = document.querySelector("#regist_name");
const locationInput = document.querySelector("#regist_location");
const checkboxs = document.querySelectorAll('.division_checkbox');

if(duplicatedNotBtn.innerText == "다시입력") {
    RoleModalYes.disabled = false;
    RoleModalYes.style.backgroundColor = "#7b9acc";
} else {
    RoleModalYes.disabled = true;
    RoleModalYes.style.backgroundColor = "#c1d2ee";
}

duplicatedNotBtn.addEventListener('click', (e) => {
    e.preventDefault();

    if(e.currentTarget.innerText == "다시입력") {
        nameInput.disabled = false;
        RoleModalYes.disabled = true;
        RoleModalYes.style.backgroundColor = "#c1d2ee";
        e.currentTarget.innerText = "중복검사";
    } else {
        fetch(`http://${window.location.host}/equipment/duplicated-check`, {
            method: 'POST',
            headers: {
                "Content-Type": "application/json",
            },
            redirect: 'follow',
            body: JSON.stringify({
                "URI" : "equipment",
                "name" : nameInput.value,
                "temperature" : checkboxs[0].checked,
                "humidity" : checkboxs[1].checked,
                "location" : locationInput.value,
                "modal_id" : "equipment#1"
            })
        }).then((response) => {
            location.href = response.url;
        }).catch((error) => {
            console.log(error);
        });
    }
})

/* Delete Equipment Modal */

const DeleteBtns = document.querySelectorAll('.delete-button');
const DeleteOverlay = document.querySelector('.delete_overlay');
const DeleteModalYes = document.querySelector('.delete_modal_yes');
const DeleteModalNo = document.querySelector('.delete_modal_no');
let CurrentDeleteEquipment;

DeleteBtns.forEach((DeleteBtn) => {
    Modal(DeleteBtn, DeleteOverlay, DeleteModalNo);
    DeleteBtn.addEventListener('click', (e) => {
        CurrentDeleteEquipment = e.currentTarget.dataset.id;
    })
})

DeleteModalYes.addEventListener('click', (e) => {
    e.preventDefault();
    fetch(`http://${window.location.host}/api/equipment-delete`, {
        method: 'POST',
        headers: {
            "Content-Type": "application/json",
        },
        redirect: 'follow',
        body: JSON.stringify({
            "name" : CurrentDeleteEquipment
        })
    }).then((response) => {
        location.href = response.url;
    }).catch((error) => {
        console.log(error);
    })
})



/* Register Form Submit */
const registerForm =document.querySelector("#registerModalForm");

registerForm.addEventListener('submit', (e) => {
    e.preventDefault();
    if(!checkboxs[0].checked && !checkboxs[1].checked) {
        document.querySelector('#divisionErrMsg').innerText = "반드시 하나 이상 선택해야 합니다.";
    } else {
        fetch(`http://${window.location.host}/api/equipment/register`, {
            method: 'POST',
            headers: {
                "Content-Type": "application/json",
            },
            redirect: 'follow',
            body: JSON.stringify({
                "name" : nameInput.value,
                "temperature" : checkboxs[0].checked,
                "humidity" : checkboxs[1].checked,
                "location" : locationInput.value
            })
        }).then((response) => {
            location.href = response.url;
        }).catch((error) => {
            console.log(error);
        });
    }
})


/* update modal */

const UpdateBtns = document.querySelectorAll('.update-button');
const UpdateOverlay = document.querySelector('.update_overlay');
const UpdateModalYes = document.querySelector('.update_modal_yes');
const UpdateModalNo = document.querySelector('.update_modal_no');
const updateForm = document.querySelector('#updateModalForm');
const updateFormInput =document.querySelector("#update_name");

let INITIAL_NAME;
let CurrentUpdateEquipment;


UpdateBtns.forEach((UpdateBtn) => {
    Modal(UpdateBtn, UpdateOverlay, UpdateModalNo);
    UpdateBtn.addEventListener('click', (e) => {
        CurrentUpdateEquipment = e.currentTarget.dataset.id;
        const params = {
            id : CurrentUpdateEquipment,
        };
        const query = new URLSearchParams(params).toString();

        fetch(`http://${window.location.host}/api/equipment/update/info?${query}`, {
            method: "GET"
        }).then((response) => response.json())
            .then((data) => {
                document.querySelector("#update_name").value = data.name;
                INITIAL_NAME = data.name;

                if(data.division.includes("기온")) {
                    document.querySelector("#update_division_type1").checked = true;
                }
                if(data.division.includes("습도")) {
                    document.querySelector("#update_division_type2").checked = true;
                }

                document.querySelector("#update_location").value = data.location;
            })
            .catch((error) => {

        })
    })
})

const updateDNBtn = document.querySelector('.updateDNButton');
updateDNBtn.addEventListener('click', (e) => {
    e.preventDefault();

    if(e.currentTarget.innerText == '다시 입력') {
        e.currentTarget.innerText = "중복 검사";
        updateFormInput.disabled = false;
        UpdateModalYes.style.backgroundColor = "#c1d2ee";
        UpdateModalYes.disabled = true;
    } else {
        UpdateModalYes.style.backgroundColor = "#7b9acc";
        UpdateModalYes.disabled = false;
        if(INITIAL_NAME === updateFormInput.value) {
                e.currentTarget.innerText = "다시 입력";
                updateFormInput.disabled = true;
        } else {
            console.log("fetch work!")
            fetch(`http://${window.location.host}/api/equipment/duplicated-check`, {
                method: 'POST',
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    "name": updateFormInput.value
                })
            }).then((response) => response.json())
                .then((data) => {
                    const emailErr = document.querySelector('.updateEmailErrmsg');
                    const btn = document.querySelector('.updateDNButton');
                    console.log(data);
                    if (data.flag) {
                        console.log(true);
                        emailErr.innerText = "중복된 장비명 입니다.";
                    } else {
                        emailErr.innerText = "";

                        btn.innerText = "다시 입력";
                        updateFormInput.disabled = true;
                    }
                })
                .catch((error) => {
                    console.log(error);
            });
        }
    }
})

const updateLocationInput = document.querySelector("#update_location")

updateForm.addEventListener('submit', (e) => {
    e.preventDefault();
    if(!checkboxs[18].checked && !checkboxs[19].checked) {
        document.querySelector('#updateDivisionErrMsg').innerText = "반드시 하나 이상 선택해야 합니다.";
    } else {
        fetch(`http://${window.location.host}/api/equipment/update`, {
            method: 'POST',
            headers: {
                "Content-Type": "application/json",
            },
            redirect: 'follow',
            body: JSON.stringify({
                "id" : CurrentUpdateEquipment,
                "name" : updateFormInput.value,
                "temperature" : checkboxs[18].checked,
                "humidity" : checkboxs[19].checked,
                "location" : updateLocationInput.value
            })
        }).then((response) => {
            location.href = response.url;
        }).catch((error) => {
            console.log(error);
        });
    }
})



/* Excel Download */
const excelButton = document.querySelector('.excel');

excelButton.addEventListener('click', (e) => {
    e.preventDefault();
    location.href = `http://${window.location.host}/api/equipment/download`;
})