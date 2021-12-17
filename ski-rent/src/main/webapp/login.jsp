<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<html lang="pl" style="scroll-behavior: smooth ;">

<head>
    <meta charset="UTF-8">
    <title>Wypożyczalnia</title>
    <meta name="keywords" content="stronka, strona">
    <meta name="robots" content="index, follow">
    <link rel="stylesheet" href="Style-css/style.css">

</head>

<body>
<div class="center">
    <div id="kontener">
        <div id="gora_kontenera">
            <div>
                <a href="index.html"><img class="logo" src="/images/logo.png" alt="logo"></a>
                <div id="gorne_menu">
                    <div id="gorne_menu_wlasciwe">
                        <ul>
                            <li><a href="index.html">Strona główna</a></li>
                            <li><a href="priceList.html">Cennik</a></li>
                            <li><a href="login.jsp">Logowanie</a></li>
                            <li><a href="registration.html">Rejestracja</a></li>
                            <li><a href="logout">Wyloguj</a></li>
                        </ul>
                    </div>
                </div>

                <div id="contact_box">
                    <table>
                        <tr>
                            <th>Kontakt:</th>
                        </tr>
                        <tr>
                            <td>Wypożyczalnia sprzętu narciarskiego</td>
                        </tr>
                        <tr>
                            <td>ul. Narciarska 3</td>
                        </tr>
                        <tr>
                            <td>Skoczów, 43-430</td>
                        </tr>
                        <tr>
                            <td>nr telefonu : 123 123 123</td>
                        </tr>
                        <tr>
                            <td>e-mail: <a href="mailto:wypozyczalnia@gmail.com">wypozyczalnia@gmail.com</a></td>
                        </tr>
                    </table>
                </div>


            </div>

        </div>
        <div id="srodkowe_menu">
            <ul id="pierwszy_poziom">
                <li><a href="equipment.html"><span class="link2">Sprzęt</span></a>
                    <ul class="drugi_poziom">
                        <li class="pierwszy_el_listy_rozwijanej"><a href="/equipment.html#ski_poles">kijki narciarskie</a></li>
                        <li><a href="/equipment.html#ski">narty</a></li>
                        <li><a href="/equipment.html#snowboard">deski snowboardowe</a></li>
                        <li><a href="/equipment.html#goggles">gogle</a></li>
                        <li><a href="/equipment.html#ski_boots">buty narciarskie</a></li>
                        <li><a href="/equipment.html#snowboard_boots">buty snowboardowe</a></li>
                        <li><a href="/equipment.html#helmet">kaski</a></li>
                    </ul>
                </li>
                <li><span class="odstep">|</span></li>

                <li><a href="clothes.html"><span class="link2">Odzież narciarska</span></a>
                    <ul class="drugi_poziom">
                        <li class="pierwszy_el_listy_rozwijanej"><a href="/clothes.html#pants">spodnie</a></li>
                        <li><a href="/clothes.html#jackets">kurtki</a></li>
                        <li><a href="/clothes.html#gloves">rękawice</a></li>
                        <li><a href="/clothes.html#balaclavas">kominiarki</a></li>
                        <li><a href="/clothes.html#caps">czapki</a></li>
                        <li><a href="/clothes.html#scarves">szaliki</a></li>
                    </ul>
                </li>
            </ul>
        </div>
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
<script src="alerts.js"></script>
</body>

</html>