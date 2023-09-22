const divisionSelect = document.querySelector('#division');
const temperatureOptios = document.querySelectorAll('.temperatureOption');
const humidityOptions = document.querySelectorAll('.humidityOption');

if(divisionSelect.value == "기온") {
    temperatureOptios.forEach((temperatureOption) => {
        temperatureOption.style.display = 'block';
    })
    humidityOptions.forEach((humidityOption) => {
        humidityOption.style.display = 'none';
    })
} else {
    temperatureOptios.forEach((temperatureOption) => {
        temperatureOption.style.display = 'none';
    })
    humidityOptions.forEach((humidityOption) => {
        humidityOption.style.display = 'block';
    })
}

divisionSelect.addEventListener('change', (event) => {
    if(event.currentTarget.value == "기온") {
        temperatureOptios.forEach((temperatureOption) => {
            temperatureOption.style.display = 'block';
        })
        temperatureOptios[0].selected = true;
        humidityOptions.forEach((humidityOption) => {
            humidityOption.style.display = 'none';
        })
    } else {
        temperatureOptios.forEach((temperatureOption) => {
            temperatureOption.style.display = 'none';
        })
        humidityOptions.forEach((humidityOption) => {
            humidityOption.style.display = 'block';
        })
        humidityOptions[0].selected = true;
    }
});

/* 조건 초기화 버튼 */
const resetButton = document.querySelector('.reset_button');
resetButton.addEventListener('click', (event) => {
    event.preventDefault();
    const URL = `http://${window.location.host}/main/reset`;

    fetch(URL, {
        method: 'POST',
        redirect: 'follow'
    }).then((response) => {
        location.href = response.url;
    }).catch((error) => {
        console.log(error);
    });
})

/* 검색 날짜 */
const startDate = document.querySelector('.startDate');
const endDate = document.querySelector('.endDate');

endDate.min = startDate.value;
startDate.max = endDate.value;

startDate.addEventListener('input', (event) => {
    endDate.min = event.currentTarget.value;
})

endDate.addEventListener('input', (event) => {
    startDate.max = event.currentTarget.value;
})


/* Modal */
let modalCurrent;

const DetailBtns = document.querySelectorAll('.detailButton');
const DetailOverlay = document.querySelector('.modal_wrapper');
const DetailModalNo = document.querySelector('.detail_modal_no');

DetailBtns.forEach((detailBtn) => {
    Modal(detailBtn, DetailOverlay, DetailModalNo);
    detailBtn.addEventListener('click', () => {
        modalCurrent = detailBtn.dataset.id.substring(0,10);
        fetch(`http://${window.location.host}/main/info`, {
            method:'POST',
            headers: {
                'Content-Type' : 'application/json',
            },
            body: JSON.stringify({
                date: modalCurrent
            })
        }).then((response) => {
            return response.json();
        }).then((data) => {
            const hourSelect = document.querySelector('.hourSelect');
            google.setOnLoadCallback(drawChart2(data));

            document.querySelector('.modalTBody').innerHTML = "";
            for(let i = 0; i < 12; i++) {
                const TR = document.createElement('tr');
                TR.innerHTML = `
                        <td>${data[i].date.substring(14,16)}분</td>
                        <td>${data[i].value}</td>
                    `;
                document.querySelector('.modalTBody').appendChild(TR);
            }

            hourSelect.innerHTML = "";
            for(let i = 0; i < data.length/12; i++) {
                const Option = document.createElement('option');
                Option.value = i + "";
                Option.innerText = `${i}시`;
                hourSelect.appendChild(Option);
            }

            hourSelect.addEventListener('change', (e) => {
                document.querySelector('.hourSelectedTime').innerText = `${e.currentTarget.value} 시`;
                document.querySelector('.modalTBody').innerHTML = "";
                for(let i = e.currentTarget.value * 12; i < e.currentTarget.value * 12 +12; i++) {
                    const TR = document.createElement('tr');
                    TR.innerHTML = `
                        <td>${data[i].date.substring(14,16)}분</td>
                        <td>${data[i].value}</td>
                    `;
                    document.querySelector('.modalTBody').appendChild(TR);
                }
            })
        }).catch((error) => {
            console.log(error);
        })
    })
})