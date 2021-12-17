<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<html lang="pl" style="scroll-behavior: smooth ;">

<head>
    <meta charset="UTF-8">
    <title>Wypożyczalnia</title>
    <meta name="keywords" content="stronka, strona">
    <meta name="robots" content="index, follow">
    <link rel="stylesheet" href="./Style-css/style.css">

</head>

<body>
    <div class="center">
        <div id="kontener">
            <div data-include="header"></div>
            <div data-include="middle-menu"></div>
            <div class="zawartosc">
                <div id="gorna_czesc_zawartosci">

                </div>
                <div id="srodkowa_czesc_zawartosci">



                    <div id="tekst">
                        <div id="panel">
                            <c:url value="/login" var="login"/>
                            <form:form action="${login}" method="post">
                                <label for="username">Adres e-mail:</label>
                                <input type="text" id="username" name="username">
                                <label for="password">Hasło:</label>
                                <input type="password" id="password" name="password">
                                <div id="lower">
                                    <input class="input_button_submit" type="submit" value="Zaloguj się">
                                </div>
                            </form:form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>

<script src="scripts/load-scripts.js"></script>

</html>