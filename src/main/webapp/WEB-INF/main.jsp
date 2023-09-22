<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.monitoring.domain.TemperatureStaticsVO" %>

<%List<TemperatureStaticsVO> TEMPERATURE_LIST = (List<TemperatureStaticsVO>)  request.getAttribute("temperature_statics"); %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="./css/layout/Modal.css">
    <link rel="stylesheet" href="./css/layout/MainLayout.css">
    <link rel="stylesheet" href="./css/common/main.css">
    <title>Monitoring | Main</title>

    <script src="https://www.gstatic.com/charts/loader.js"></script>
    <script>
        /* Draw Google Charts */
        google.charts.load('current', {packages: ['corechart']});
        google.charts.setOnLoadCallback(drawChart);

        const TEMPERATURE_LIST_STRING = "<%= TEMPERATURE_LIST  %>";
        const TEMPERATURE_LIST = TEMPERATURE_LIST_STRING.substring(1,TEMPERATURE_LIST_STRING.length-2).split(",,");
        console.log(TEMPERATURE_LIST);

        function drawChart() {
            var data = new google.visualization.DataTable();
            let minimum = 100;
            let maximum = -100;
            data.addColumn('string', '날짜');
            data.addColumn('number', '최고 기온');
            data.addColumn('number', '평균 기온');
            data.addColumn('number', '최저 기온');

            let rows = [];
            for(let i = 0; i < TEMPERATURE_LIST.length; i++) {
                const TEMPERATURE_DATA = TEMPERATURE_LIST[i].split(",");
                let row = []
                row.push(TEMPERATURE_DATA[4].substring(0,10));
                if(Number(TEMPERATURE_DATA[2]) > maximum) {
                    maximum = Number(TEMPERATURE_DATA[2]);
                }
                row.push(Number(TEMPERATURE_DATA[2]));
                row.push(Number(TEMPERATURE_DATA[1]));

                if(Number(TEMPERATURE_DATA[3]) < minimum) {
                    minimum = Number(TEMPERATURE_DATA[3]);
                }
                row.push(Number(TEMPERATURE_DATA[3]));
                rows.push(row);
            }

            data.addRows(rows);

            var options = {
                animation:{ //animation
                  duration:1000,
                  easing:'inAndOut',
                  startup:true
                },
                backgroundColor:'#FCF6F5',
                chartArea: {
                    width:'1200px',
                    height:'800px',
                    /*backgroundColor:{
                        stroke:"black",
                        strokeWidth:'0 1 1 1'
                    }*/
                },
                focusTarget:'category',
                fontName:'GmarketSans',
                hAxis: {
                    title: '일시',
                    gridlines: {
                        interval:10
                    }
                },
                vAxis: {
                    title: '기온',
                    viewWindow: {
                        max:maximum + 5,
                        min:minimum - 5
                    }
                },
                series:{
                    0:{
                        color:'#FF6262',
                        lineWidth:4
                    },
                    1:{
                        color:"#6EFF62",
                        lineWidth:4
                    },
                    2:{
                        color:"#628EFF",
                        lineWidth:4
                    }
                }
            };

            var chart = new google.visualization.LineChart(document.getElementById('myPieChart'));

            chart.draw(data, options);
        }

        google.setOnLoadCallback(drawChart);
    </script>

    <script>
        function drawChart2(server_data) {
            var data = new google.visualization.DataTable();
            let minimum = 100;
            let maximum = -100;
            data.addColumn('string', '시간');
            data.addColumn('number', '기온');

            let rows = [];
            for(let i = 0; i < server_data.length; i++) {
                let row = []
                row.push(server_data[i].date.substring(11,16));
                if(Number(server_data[i].value) > maximum) {
                    maximum = Number(server_data[i].value);
                }

                if(Number(server_data[i].value) < minimum) {
                    minimum = Number(server_data[i].value);
                }
                row.push(server_data[i].value);
                rows.push(row);
            }

            data.addRows(rows);

            var options = {
                width:'1200px',
                height:'700px',
                animation:{
                    duration:1000,
                    easing:'inAndOut',
                    startup:true
                },
                backgroundColor:'#fcfcfc',
                chartArea: {
                    width:'80%',
                    height:'80%',
                },
                focusTarget:'category',
                fontName:'GmarketSans',
                hAxis: {
                    title: '일시',
                    showTextEvery: 36,
                    allowContainerBoundaryTextCutoff: false,
                },
                vAxis: {
                    title: '기온',
                    viewWindow: {
                        max:maximum + 2,
                        min:minimum - 2
                    }
                },
                series:{
                    0:{
                        color:"#6EFF62",
                        lineWidth:4
                    }
                }
            };

            var chart = new google.visualization.LineChart(document.getElementById('detailChart'));

            chart.draw(data, options);
        }

    </script>
</head>
<body>

<!-- Modal -->
<dialog class="modal_wrapper">
    <div class="modal detailModal">
        <svg xmlns="http://www.w3.org/2000/svg" height="1em" class="detail_modal_no" viewBox="0 0 384 512" >
            <path d="M376.6 84.5c11.3-13.6 9.5-33.8-4.1-45.1s-33.8-9.5-45.1 4.1L192 206 56.6 43.5C45.3 29.9 25.1 28.1 11.5 39.4S-3.9 70.9 7.4 84.5L150.3 256 7.4 427.5c-11.3 13.6-9.5 33.8 4.1 45.1s33.8 9.5 45.1-4.1L192 306 327.4 468.5c11.3 13.6 31.5 15.4 45.1 4.1s15.4-31.5 4.1-45.1L233.7 256 376.6 84.5z"/>
        </svg>
        <div class="modalHeader">
            <h1>23년 2월 28일</h1>
            <span>북구</span>
        </div>
        <main class="mainWrapper">
            <div class="detailChartWrapper" id="detailChart"></div>
            <div class="hourDataWrapper">
                <div class="hourDataHeader">
                    <label>시간</label>
                    <select class="hourSelect"></select>
            </div>
            <table>
                <thead>
                    <tr>
                        <th class="hourSelectedTime">00시</th>
                        <th>기온(도)</th>
                    </tr>
                </thead>
                <tbody class="modalTBody">

                </tbody>
            </table>
            </div>
        </main>
    </div>
</dialog>

<header>
    <a href="/main">
        <span class="logo">LOGO</span>
    </a>
    <div class="links">
        <a href="/main" class="main_link">
            <span class="link accent_underline">조회</span>
        </a>
        <a href="/equipment"  class="equipment_link">
            <span class="link">장비</span>
        </a>
        <% if((Boolean) session.getAttribute("isAdmin")) { %>
        <a href="/users"  class="users_link">
            <span class="link">유저관리</span>
        </a>
        <% } %>
        <a href="/user-edit">
            <span class="link">개인정보</span>
        </a>
        <span class="accent_button logout">
                로그아웃
        </span>
    </div>
</header>

<main>
    <div class="sidemenu_wrapper">
        <form class="sidemenu" id="sidemenuForm" action="/main/search" method="post">
            <div class="input_wrapper">
                <label for="division">구분</label>
                <select name="division" id="division" form="sidemenuForm">
                    <!--
                    <option value="NONE" ${requestScope.division.equals("NONE") ? "selected" : ""} >
                        전체
                    </option>
                    -->
                    <option value="기온" ${requestScope.division.equals("기온") ? "selected" : ""} >
                        기온
                    </option>
                    <option value="습도" ${requestScope.division.equals("습도") ? "selected" : ""} >
                        습도
                    </option>
                </select>
            </div>
            <div class="input_wrapper">
                <label for="location">지역</label>
                <select name="location" id="location" form="sidemenuForm">
                    <%
                        List<String> LOCATIONS_LIST = (List<String>) request.getAttribute("temperature_locations");
                        for(String locationElement : LOCATIONS_LIST) {
                    %>
                    <option value="<%= locationElement %>" <%= request.getAttribute("location").equals(locationElement) ? "selected" : ""%> class="temperatureOption" >
                        <%= locationElement %>
                    </option>
                    <% }
                        LOCATIONS_LIST = (List<String>) request.getAttribute("humidity_locations");
                        for(String locationElement : LOCATIONS_LIST) {
                    %>
                    <option value="<%= locationElement %>" <%= request.getAttribute("location").equals(locationElement) ? "selected" : ""%> class="humidityOption" >
                        <%= locationElement %>
                    </option>
                    <%
                        }
                    %>
                </select>
            </div>
            <div class="input_wrapper">
                <label>기간</label>
<%--                <input type="date" name="start_date" class="startDate" min="2023-08-01" max="2023-08-07" value="<%= request.getAttribute("startDate") %>" />--%>
                <input type="date" name="start_date" class="startDate" value="<%= request.getAttribute("startDate") %>" />
                <span class="dateSeperator">~</span>
<%--                <input type="date" name="end_date" class="endDate" min="2023-08-01" max="2023-08-07" value="<%= request.getAttribute("endDate") %>" />--%>
                <input type="date" name="end_date" class="endDate" value="<%= request.getAttribute("endDate") %>" />
            </div>
            <div class="input_wrapper">
                <label for="name">장비명</label>
                <input id="name" placeholder="장비명을 입력하세요.." name="equipment_name" value="${requestScope.name == "" ? "" : requestScope.name}" />
            </div>
            <input type="submit" value="검색하기" class="submit"/>
            <button class="reset_button">조건 초기화</button>
        </form>
    </div>
    <% if (TEMPERATURE_LIST.size() > 0 ) { %>
        <div class="content_wrapper">
        <div class="chart_container" id="myPieChart">

        </div>
        <div class="table_container">
            <table class="statics_table">
                <thead>
                <tr>
                    <th>장비명</th>
                    <th>일시</th>
                    <th>평균 기온(°C)</th>
                    <th>최고 기온(°C)</th>
                    <th>최저 기온(°C)</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <%
                    for(TemperatureStaticsVO temperatureStatic : TEMPERATURE_LIST) {
                %>
                    <tr>
                        <td><%= temperatureStatic.getName() %></td>
                        <td><%= temperatureStatic.getDate().substring(0,10) %></td>
                        <td><%= temperatureStatic.getAVG() %></td>
                        <td><%= temperatureStatic.getMAX() %></td>
                        <td><%= temperatureStatic.getMIN() %></td>
                        <td>
                            <button class="detailButton" data-id="<%= temperatureStatic.getDate() %>">자세히 보기</button>
                        </td>
                    </tr>
                <% } %>
                </tbody>
            </table>
        </div>
    </div>
    <% } else { %>
        <div class="SearchNotFound">
            <h1>조건에 부합하는 데이터가 없습니다.</h1>
        </div>
    <% } %>
</main>
    <script src="./js/utils/modal.js"></script>
    <script src="./js/utils/logout.js"></script>
    <script src="./js/common/main.js"></script>
</body>
</html>