<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="./css/layout/UserLayout.css">
    <link rel="stylesheet" href="./css/common/index.css">
    <title>Monitoring | SignUp</title>
</head>
<body>
<main class="layout_main">
    <a href="/">
        <h1 class="logo">LOGO</h1>
    </a>
    <span class="desc">융합 다중 센서 단말 장치 모니터링 프로그램</span>

    <% if(session.getAttribute("id")==null) { %>
    <form class="login_wrapper" action="/" method="post">
        <div class="input_wrapper">
            <label for="email">Email</label>
            <input type="text" placeholder="이메일을 입력해주세요..." id="email" name="email" required/>
        </div>
    <div class="input_wrapper">
        <label for="password">Password</label>
        <input type="password" placeholder="비밀번호를 입력해주세요..." id="password" name="password"/>
    </div>
    <span class="errmsg">${ requestScope.Message.isOk() == "false" ? requestScope.Message.getMessage() : ""}</span>
    <input type="submit" value="로그인" class="submit_button" required/>
    </form>
    <a href="/sign-up" class="submit_button">
        <span>
            회원가입
        </span>
    </a>
    <% } else {
        response.sendRedirect("/main");
    } %>
</main>
</body>
<script>
    var isOk = ${requestScope.Message.isOk()};
    var Message = ${requestScope.Message.getMessage()};

    if(isOk) {
        window.alert(Message);
    }
</script>
</html>