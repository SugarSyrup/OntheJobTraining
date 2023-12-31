<%@ page import="com.example.monitoring.domain.User" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.monitoring.domain.UserRole" %>
<%@ page import="com.example.monitoring.domain.UserRole" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="./css/layout/Modal.css">
    <link rel="stylesheet" href="./css/layout/MainLayout.css">
    <link rel="stylesheet" href="./css/common/users.css">
    <title>Monitoring | Users</title>
</head>
<body>


<!-- Modal -->
<dialog class="modal_wrapper role_overlay">
    <div class="modal ">
        <svg xmlns="http://www.w3.org/2000/svg" height="1em" class="role_modal_no" viewBox="0 0 384 512"><!--! Font Awesome Free 6.4.2 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license (Commercial License) Copyright 2023 Fonticons, Inc. --><path d="M376.6 84.5c11.3-13.6 9.5-33.8-4.1-45.1s-33.8-9.5-45.1 4.1L192 206 56.6 43.5C45.3 29.9 25.1 28.1 11.5 39.4S-3.9 70.9 7.4 84.5L150.3 256 7.4 427.5c-11.3 13.6-9.5 33.8 4.1 45.1s33.8 9.5 45.1-4.1L192 306 327.4 468.5c11.3 13.6 31.5 15.4 45.1 4.1s15.4-31.5 4.1-45.1L233.7 256 376.6 84.5z"/></svg>
        <span>권한 변경</span>

        <form class="input_wrapper" id="modalForm" action="/api/role-change" method="post">
            <div class="input_wrapper">
                <label for="userRole">권한</label>
                <input name="user_no" style="display:none" type="text" id="user_no_input"/>
                <select name="userRole" form="modalForm" id="modal_input">
                    <option value="USER">
                        일반 유저
                    </option>
                    <option value="ADMIN">
                        관리자
                    </option>
                </select>
            </div>
            <input type="submit" class="modal_btn role_modal_yes" value="수정" />
        </form>
    </div>
</dialog>


<!-- Modal -->
<dialog class="modal_wrapper delete_overlay">
    <div class="modal delete_modal ">
        <span>해당 유저를 삭제 하시겠습니까?</span>
        <div class="btns">
            <span class="modal_btn delete_modal_yes">예</span>
            <span class="modal_btn delete_modal_no">아니요</span>
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
            <span class="link">장비</span>
        </a>

        <% if((Boolean) session.getAttribute("isAdmin")) { %>
        <a href="/users"  class="users_link">
            <span class="link accent_underline">유저관리</span>
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
        <form class="sidemenu" id="sidemenuForm" action="/users" method="post">
            <div class="input_wrapper">
                <label for="name">이름</label>
                <input id="name" placeholder="이름을 입력하세요.." name="name" value="${requestScope.name == "" ? "" : requestScope.name}" />
            </div>
            <div class="input_wrapper">
                <label for="userRole">권한</label>
                <select name="userRole" id="userRole" form="sidemenuForm">
                    <option value="NONE" ${requestScope.userRole.equals("NONE") ? "selected" : ""} >
                        전체
                    </option>
                    <option value="USER" ${requestScope.userRole.equals("USER") ? "selected" : ""} >
                        일반 유저
                    </option>
                    <option value="ADMIN" ${requestScope.userRole.equals("ADMIN") ? "selected" : ""} >
                        관리자
                    </option>
                </select>
            </div>
            <input type="submit" value="검색하기" class="submit"/>
            <button class="reset_button">조건 초기화</button>
        </form>
    <div class="table_wrapper">
        <table class="users_table">
            <thead>
                <tr>
                    <th>이름</th>
                    <th>이메일</th>
                    <th>권한</th>
                    <th>등록날짜</th>
                    <th> </th>
                </tr>
            </thead>
            <tbody>
            <%
                List<User> USER_LIST = (List<User>) request.getAttribute("userList");
                for(User user : USER_LIST) {
            %>
                <tr>
                    <td><%= user.getName() %></td>
                    <td><%= user.getEmail() %></td>
                    <td>
                        <div class="role_wrapper">
                            <% if(user.getUserRole() == UserRole.USER) { %>
                            일반 유저
                            <% } else { %>
                            관리자
                            <% } %>
                            <span class="role_btn role_modal_open" data-userno="<%= user.getUser_no() %>" data-userrole="<%= user.getUserRole() %>">권한 변경</span>
                        </div>
                    </td>
                    <td><%= user.getRegist_date() %></td>
                    <td>
                        <span class="delete_btn delete_modal_open" data-userno="<%= user.getUser_no() %>">삭제</span>
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
<script src="./js/common/users.js"></script>

</body>
</html>