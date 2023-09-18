<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="./css/layout/MainLayout.css">
    <title>Monitoring | Main</title>
</head>
<body>
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

    </main>
    <script src="./js/utils/logout.js"></script>
</body>
</html>