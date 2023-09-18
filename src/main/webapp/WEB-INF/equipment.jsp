<%@ page import="java.util.List" %>
<%@ page import="com.example.monitoring.domain.Equipment" %>
<%@ page import="com.example.monitoring.domain.ResponseMessage" %>
<%@ page import="com.example.monitoring.domain.EquipmentNameCheck" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    ResponseMessage rm = request.getServletContext().getAttribute("response_msg") == null ? new ResponseMessage() : (ResponseMessage) request.getServletContext().getAttribute("response_msg");
    String modalId = request.getServletContext().getAttribute("modalID") == null ? "" : (String) request.getServletContext().getAttribute("modalID");
    EquipmentNameCheck duplicatedNotBody = request.getServletContext().getAttribute("formConditions") == null ? new EquipmentNameCheck() : (EquipmentNameCheck) request.getServletContext().getAttribute("formConditions");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="./css/layout/Modal.css">
    <link rel="stylesheet" href="./css/layout/MainLayout.css">
    <link rel="stylesheet" href="./css/common/equipment.css">
    <title>Monitoring | Equipment</title>
</head>
<body>
<!-- Register Modal -->
<dialog class="modal_wrapper register_overlay" <%= modalId.equals("equipment#1") ? "open" : "" %>>
    <div class="modal ">
        <svg xmlns="http://www.w3.org/2000/svg" height="1em" class="register_modal_no" viewBox="0 0 384 512" >
            <path d="M376.6 84.5c11.3-13.6 9.5-33.8-4.1-45.1s-33.8-9.5-45.1 4.1L192 206 56.6 43.5C45.3 29.9 25.1 28.1 11.5 39.4S-3.9 70.9 7.4 84.5L150.3 256 7.4 427.5c-11.3 13.6-9.5 33.8 4.1 45.1s33.8 9.5 45.1-4.1L192 306 327.4 468.5c11.3 13.6 31.5 15.4 45.1 4.1s15.4-31.5 4.1-45.1L233.7 256 376.6 84.5z"/>
        </svg>
        <span>장비 등록</span>

        <form id="registerModalForm">
            <div class="input_wrapper">
                <label for="regist_name">장비명</label>
                <div class="duplicated_not_wrapper">
                    <input name="regist_name" type="text" id="regist_name" placeholder="장비명을 입력해주세요..." value="<%= rm.getOk() ? rm.getMessage() : duplicatedNotBody.getName() %>" <%= rm.getOk() ? "disabled" : ""%> required />
                    <button class="duplicated_not_button"> <%= !rm.getOk() ? "중복체크" : "다시입력" %> </button>
                </div>
                <span class="errmsg"> <%=rm.getOk() ? "" : rm.getMessage()%> </span>
            </div>
            <div class="input_wrapper">
                <label>수집하는 데이터</label>
                <span class="errmsg"  id="divisionErrMsg"> <%=(String) request.getAttribute("divisionErrorMsg") %> </span>
                <div class="checkboxs_wrapper">
                    <%
                        String[] DIVISION_AVAILABLE_LIST = {"기온", "습도"} ;
                        int idx = 1;
                        for(String division : DIVISION_AVAILABLE_LIST) {
                    %>
                        <div class="checkbox_wrapper">
                            <input type="checkbox" class="division_checkbox" id="<%="regist_division_type" + idx%>" name="<%=division%>" <%= division == "기온" ? ( duplicatedNotBody.getTemperature() == true ? "checked" : "" ) : ( duplicatedNotBody.getHumidity() == true ? "checked" : "" ) %> />
                            <label for="<%="regist_division_type" + idx%>" class="division_checkbox_label"><%=division%></label>
                        </div>
                    <%
                        idx += 1;
                        }
                        String[] DIVISION_DISAVAILABLE_LIST = {"미세먼지", "(초)미세먼지", "조도", "자외선","소음","진동","CO","풍향","풍속", "기압", "오존 (O3)", "아황산가스(SO2)", "이산화질소(NO2)", "황화수소(H2S)", "TVOC", "암모니아(NH3)"} ;
                        for(String division : DIVISION_DISAVAILABLE_LIST) {
                    %>
                        <div class="checkbox_wrapper">
                            <input type="checkbox" class="division_checkbox" id="<%="regist_division_type" + idx%>" name="<%=division%>" disabled />
                            <label for="<%="regist_division_type" + idx%>" class="division_checkbox_label disabled_label"><%=division%></label>
                        </div>
                    <% } %>
                </div>
            </div>
            <div class="input_wrapper">
                <label for="regist_location" >지역</label>
                <input name="location" type="text" id="regist_location" placeholder="지역을 입력해주세요..." value="<%= duplicatedNotBody.getLocation()%>" required/>
            </div>
            <input type="submit" class="modal_btn register_modal_yes" value="등록" />
        </form>
    </div>
</dialog>

<!-- Update Modal -->
<dialog class="modal_wrapper update_overlay" >
    <div class="modal">
        <svg xmlns="http://www.w3.org/2000/svg" height="1em" class="update_modal_no" viewBox="0 0 384 512" >
            <path d="M376.6 84.5c11.3-13.6 9.5-33.8-4.1-45.1s-33.8-9.5-45.1 4.1L192 206 56.6 43.5C45.3 29.9 25.1 28.1 11.5 39.4S-3.9 70.9 7.4 84.5L150.3 256 7.4 427.5c-11.3 13.6-9.5 33.8 4.1 45.1s33.8 9.5 45.1-4.1L192 306 327.4 468.5c11.3 13.6 31.5 15.4 45.1 4.1s15.4-31.5 4.1-45.1L233.7 256 376.6 84.5z"/>
        </svg>
        <span>장비 수정</span>

        <form id="updateModalForm"  method="post">
            <div class="input_wrapper">
                <label for="update_name">장비명</label>
                <div class="duplicated_not_wrapper">
                    <input name="update_name" type="text" id="update_name" placeholder="장비명을 입력해주세요..." required disabled/>
                    <button class="updateDNButton">다시 입력</button>
                </div>
                <span class="errmsg updateEmailErrmsg"></span>
            </div>
            <div class="input_wrapper">
                <label>수집하는 데이터</label>
                <span class="errmsg" id="updateDivisionErrMsg"></span>
                <div class="checkboxs_wrapper">
                    <%
                        int update_idx = 1;
                        for(String division : DIVISION_AVAILABLE_LIST) {
                    %>
                    <div class="checkbox_wrapper">
                        <input type="checkbox" class="division_checkbox" id="<%="update_division_type" + update_idx%>" name="<%=division%>" <%= division == "기온" ? ( duplicatedNotBody.getTemperature() == true ? "checked" : "" ) : ( duplicatedNotBody.getHumidity() == true ? "checked" : "" ) %> />
                        <label for="<%="update_division_type" + update_idx%>" class="division_checkbox_label"><%=division%></label>
                    </div>
                    <%
                        update_idx += 1;
                        }
                        for(String division : DIVISION_DISAVAILABLE_LIST) {
                    %>
                    <div class="checkbox_wrapper">
                        <input type="checkbox" class="division_checkbox" id="<%="update_division_type" + update_idx%>" name="<%=division%>" disabled />
                        <label for="<%="update_division_type" + update_idx%>" class="division_checkbox_label disabled_label"><%=division%></label>
                    </div>
                    <% } %>
                </div>
            </div>
            <div class="input_wrapper">
                <label for="update_location" >지역</label>
                <input name="location" type="text" id="update_location" placeholder="지역을 입력해주세요..." required/>
            </div>
            <input type="submit" class="modal_btn update_modal_yes" value="수정" />
        </form>
    </div>
</dialog>

<!-- Delete Modal -->
<dialog class="modal_wrapper delete_overlay">
    <div class="modal delete_modal ">
        <span>해당 장비를 삭제 하시겠습니까?</span>
        <div class="btns">
            <span class="modal_btn delete_modal_yes">예</span>
            <span class="modal_btn delete_modal_no" style="background-color: #CC7B7B">아니요</span>
        </div>
    </div>
</dialog>


<header>
    <a href="/main">
        <span class="logo">LOGO</span>
    </a>
    <div class="links">
        <a href="/main" class="main_link">
            <span class="link">조회</span>
        </a>
        <a href="/equipment"  class="equipment_link">
            <span class="link accent_underline">장비</span>
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
        <form class="sidemenu" id="sidemenuForm" action="/equipment" method="post">
            <div class="input_wrapper">
                <label for="division">구분</label>
                <select name="division" id="division" form="sidemenuForm">
                    <option value="NONE" ${requestScope.division.equals("NONE") ? "selected" : ""} >
                        전체
                    </option>
                    <option value="기온" ${requestScope.division.equals("기온") ? "selected" : ""} >
                        기온
                    </option>
                    <option value="습도" ${requestScope.division.equals("습도") ? "selected" : ""} >
                        습도
                    </option>
                </select>
            </div>
            <div class="input_wrapper">
                <label for="location">지역
                </label>
                <select name="location" id="location" form="sidemenuForm">
                    <option value="NONE" ${requestScope.location.equals("NONE") ? "selected" : ""} >
                        전체
                    </option>
                    <%
                        List<String> LOCATIONS_LIST = (List<String>) request.getAttribute("equipment_locations");
                        for(String locationElement : LOCATIONS_LIST) {
                    %>
                    <%= locationElement %>
                    <option value=<%= locationElement %> <%= request.getAttribute("location").equals(locationElement) ? "selected" : ""%> >
                        <%= locationElement %>
                    </option>
                    <% } %>
                </select>
            </div>
            <div class="input_wrapper">
                <label for="name">장비명</label>
                <input id="name" placeholder="장비명을 입력하세요.." name="name" value="${requestScope.name == "" ? "" : requestScope.name}" />
            </div>
            <input type="submit" value="검색하기" class="submit"/>
            <button class="reset_button">조건 초기화</button>
        </form>
        <button class="register_button register_modal_open">장비 등록</button>
    </div>
    <div class="table_wrapper">
        <table class="equipment_table">
            <thead>
            <tr>
                <th>장비명</th>
                <th>지역</th>
                <th>수집하는 데이터(구분)</th>
                <th>등록날짜</th>
                <th> </th>
            </tr>
            </thead>
            <tbody>
            <%
                List<Equipment> EQUIPMENT_LIST = (List<Equipment>) request.getAttribute("equipments");
                for(Equipment equipment : EQUIPMENT_LIST) {
            %>
            <tr>
                <td><%= equipment.getName() %></td>
                <td><%= equipment.getLocation() %></td>
                <td><%= equipment.getDivision().substring(1,equipment.getDivision().length()-1) %></td>
                <td><%= equipment.getRegist_date() %></td>
                <td>
                    <div class="btns-wrapper">
                        <button class="small-button update-button" data-id="<%=equipment.getName()%>">수정</button>
                        <button class="small-button delete-button" data-id="<%=equipment.getName()%>" >삭제</button>
                    </div>
                </td>
            </tr>

            <% } %>
            </tbody>
        </table>
    </div>
</main>
<script src="./js/utils/logout.js"></script>
<script src="./js/utils/reset_conditions.js"></script>
<script src="./js/utils/modal.js"></script>
<script src="./js/common/equipment.js"></script>

</body>
</html>