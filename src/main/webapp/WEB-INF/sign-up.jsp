<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="./css/layout/UserLayout.css">
    <link rel="stylesheet" href="./css/common/signup.css">
    <title>Monitoring | SignUp</title>
</head>
<body>
<main class="layout_main">
    <a href="/">
        <h1 class="logo">LOGO</h1>
    </a>

    <% if(session.getAttribute("id")==null) { %>
        <form class="login_wrapper" action="/sign-up" method="post">
            <div class="input_wrapper">
                <label for="email">Email</label>
                <input type="text" placeholder="이메일을 입력해주세요..." id="email" name="email" value="${ requestScope.UserInfo == null ? "" : requestScope.UserInfo.getEmail()}" required />
            </div>
            <div class="input_wrapper">
                <label for="password">Password</label>
                <input type="password" placeholder="비밀번호를 입력해주세요..." id="password" name="password" required value="${ requestScope.UserInfo == null ? "" : requestScope.UserInfo.getPassword()}" />
            </div>
            <div class="input_wrapper">
                <label for="password_check">Password Check</label>
                <input type="password" placeholder="비밀번호를 다시한번 입력해주세요..." id="password_check" name="password_check" required value="${ requestScope.password_check == null ? "" : requestScope.password_check}" />
            </div>
            <div class="input_wrapper">
                <label for="name">Name</label>
                <input type="text" placeholder="이름을 입력해주세요..." id="name" name="name" required value="${ requestScope.UserInfo == null ? "" : requestScope.UserInfo.getName()}" />
            </div>
            <span class="errmsg">${ requestScope.Message.getOk() == "false" ? requestScope.Message.getMessage() : ""}</span>
            <input type="submit" value="회원가입" class="submit_button"/>
        </form>
    <% } else {
        response.sendRedirect("/");
    } %>
</main>
</body>
</html>