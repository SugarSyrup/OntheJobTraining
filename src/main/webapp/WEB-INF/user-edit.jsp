<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="./css/layout/Modal.css">
    <link rel="stylesheet" href="./css/layout/UserLayout.css">
    <link rel="stylesheet" href="./css/common/useredit.css">
    <title>Monitoring | UserEdit</title>
</head>
<body>

<!-- Modal -->
<dialog class="modal_wrapper ">
    <div class="modal ">
        <span>회원 탈퇴를 하시겠습니까?</span>
        <div class="btns">
            <span class="modal_btn modal_yes">예</span>
            <span class="modal_btn modal_no">아니요</span>
        </div>
    </div>
</dialog>

<main class="layout_main">
    <a>
        <h1 class="logo">개인 정보</h1>
    </a>

    <% if(session.getAttribute("id")!=null) { %>
    <form class="login_wrapper" action="/user-edit" method="post">
        <div class="input_wrapper position_relative">
            <label for="email">Email</label>
            <input type="text" placeholder="이메일을 입력해주세요..." id="email" name="email" value=<%=request.getAttribute("email")%> required readonly/>
            <span class="announcement">* 수정불가</span>
        </div>
        <div class="input_wrapper">
            <label for="password">Password</label>
            <input type="password" placeholder="비밀번호를 입력해주세요..." id="password" name="password" />
        </div>
        <div class="input_wrapper">
            <label for="password_check">Password Check</label>
            <input type="password" placeholder="비밀번호를 다시한번 입력해주세요..." id="password_check" name="password_check" />
        </div>
        <div class="input_wrapper">
            <label for="name">Name</label>
            <input type="text" placeholder="이름을 입력해주세요..." id="name" name="name" value=<%=request.getAttribute("name")%> required/>
        </div>
        <span class="errmsg">${ requestScope.Message.isOK() == "false" ? requestScope.Message.getMessage() : ""}</span>
        <a class="submit_button red" id="modal_open">
            <span>
                회원 탈퇴
            </span>
        </a>
        <input type="submit" value="회원정보 수정" class="submit_button"/>
    </form>
    <a href="/main" class="submit_button">
        <span>
            홈 으로
        </span>
    </a>
    <% } else {
        response.sendRedirect("/");
    } %>
</main>


<script src="./js/utils/modal.js"></script>
<script src="./js/common/user-edit.js"></script>
</body>
</html>