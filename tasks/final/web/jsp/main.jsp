<%--
  Created by IntelliJ IDEA.
  User: GRAFIK529
  Date: 06/11/19
  Time: 11:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ page import="by.training.machine.monitoring.ApplicationConstant" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${empty cookie.get('Language')}">
    <fmt:setLocale value="en-EN"/>
</c:if>
<c:if test="${not empty cookie.get('Language')}">
    <c:if test="${cookie.get('Language').value.equalsIgnoreCase('EN')}">
        <fmt:setLocale value="en-EN"/>
    </c:if>
    <c:if test="${cookie.get('Language').value.equalsIgnoreCase('RU')}">
        <fmt:setLocale value="ru-RU"/>
    </c:if>
</c:if>
<fmt:setBundle basename="i18n/AppMessages"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
<%--    <meta http-equiv="X-UA-Compatible" content="IE=edge">--%>
    <meta http-equiv="content-type" content="text/html">
    <meta name="description" content="A front-end template that helps you build fast, modern mobile web apps.">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0">
    <title><fmt:message key="title.msg"/></title>

    <!-- Add to homescreen for Chrome on Android -->
    <meta name="mobile-web-app-capable" content="yes">
    <link rel="icon" sizes="192x192" href="${pageContext.request.contextPath}/static/images/android-desktop.png">

    <!-- Add to homescreen for Safari on iOS -->
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-title" content="Material Design Lite">
    <link rel="apple-touch-icon-precomposed" href="${pageContext.request.contextPath}/static/images/ios-desktop.png">

    <!-- Tile icon for Win8 (144x144 + tile color) -->
    <meta name="msapplication-TileImage" content="images/touch/ms-touch-icon-144x144-precomposed.png">
    <meta name="msapplication-TileColor" content="#3372DF">

    <link rel="shortcut icon" href="${pageContext.request.contextPath}/static/images/favicon.png">

    <!-- SEO: If your mobile URL is different from the desktop URL, add a canonical link to the desktop page https://developers.google.com/webmasters/smartphone-sites/feature-phones -->
    <!--
    <link rel="canonical" href="http://www.example.com/">
    -->

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/fronts.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/material.icons.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/material.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/styles.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/popup.css">

</head>
<body>
<div class="demo-layout mdl-layout mdl-js-layout mdl-layout--fixed-drawer mdl-layout--fixed-header has-drawer is-upgraded is-small-screen">
    <header class="demo-header mdl-layout__header mdl-color--grey-100 mdl-color-text--grey-600"
            <c:if test="${not ApplicationConstant.SECURITY_SERVICE.isLogIn(pageContext.request.session)}">
                style="margin-left: auto; width: 100%"
            </c:if>>
        <div class="mdl-layout__header-row">
            <%@include file="views/signInUpView.jsp" %>
            <%@include file="views/deleteMachineView.jsp" %>
            <span class="mdl-layout-title">Home</span>
            <div class="mdl-layout-spacer"></div>
            <button class="mdl-button mdl-js-button mdl-js-ripple-effect mdl-button--icon" id="hdrbtn">
                <i class="material-icons">language</i>
            </button>
            <ul class="mdl-menu mdl-js-menu mdl-js-ripple-effect mdl-menu--bottom-right" for="hdrbtn">
                <%@include file="views/languageChangeView.jsp" %>
            </ul>
        </div>
    </header>
    <%@include file="views/userPanelView.jsp" %>
    <%@include file="views/adminPanelView.jsp" %>
    <%@include file="views/manufacturerPanelView.jsp" %>
    <main class="mdl-layout__content mdl-color--grey-100"
            <c:if test="${not ApplicationConstant.SECURITY_SERVICE.isLogIn(pageContext.request.session)}">
                style="margin-left: 0px"
            </c:if>>
        <div class="mdl-grid demo-content">
            <%@include file="views/viewAllUsersView.jsp" %>
            <%@include file="views/mainInfoView.jsp" %>
            <%@include file="views/viewAllCarsView.jsp" %>
            <%@include file="views/addNewCarView.jsp" %>
        </div>
    </main>
</div>
<svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" version="1.1"
     style="position: fixed; left: -1000px; height: -1000px;">
    <defs>
        <mask id="piemask" maskContentUnits="objectBoundingBox">
            <circle cx="0.5" cy="0.5" r="0.49" fill="white"></circle>
            <circle cx="0.5" cy="0.5" r="0.40" fill="black"></circle>
        </mask>
        <g id="piechart">
            <circle cx="0.5" cy="0.5" r="0.5"></circle>
            <path d="M 0.5 0.5 0.5 0 A 0.5 0.5 0 0 1 0.95 0.28 z" stroke="none" fill="rgba(255, 255, 255, 0.75)"></path>
        </g>
    </defs>
</svg>
<svg version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" viewBox="0 0 500 250"
     style="position: fixed; left: -1000px; height: -1000px;">
    <defs>
        <g id="chart">
            <g id="Gridlines">
                <line fill="#888888" stroke="#888888" stroke-miterlimit="10" x1="0" y1="27.3" x2="468.3"
                      y2="27.3"></line>
                <line fill="#888888" stroke="#888888" stroke-miterlimit="10" x1="0" y1="66.7" x2="468.3"
                      y2="66.7"></line>
                <line fill="#888888" stroke="#888888" stroke-miterlimit="10" x1="0" y1="105.3" x2="468.3"
                      y2="105.3"></line>
                <line fill="#888888" stroke="#888888" stroke-miterlimit="10" x1="0" y1="144.7" x2="468.3"
                      y2="144.7"></line>
                <line fill="#888888" stroke="#888888" stroke-miterlimit="10" x1="0" y1="184.3" x2="468.3"
                      y2="184.3"></line>
            </g>
            <g id="Numbers">
                <text transform="matrix(1 0 0 1 485 29.3333)" fill="#888888" font-family="'Roboto'" font-size="9">500
                </text>
                <text transform="matrix(1 0 0 1 485 69)" fill="#888888" font-family="'Roboto'" font-size="9">400</text>
                <text transform="matrix(1 0 0 1 485 109.3333)" fill="#888888" font-family="'Roboto'" font-size="9">300
                </text>
                <text transform="matrix(1 0 0 1 485 149)" fill="#888888" font-family="'Roboto'" font-size="9">200</text>
                <text transform="matrix(1 0 0 1 485 188.3333)" fill="#888888" font-family="'Roboto'" font-size="9">100
                </text>
                <text transform="matrix(1 0 0 1 0 249.0003)" fill="#888888" font-family="'Roboto'" font-size="9">1
                </text>
                <text transform="matrix(1 0 0 1 78 249.0003)" fill="#888888" font-family="'Roboto'" font-size="9">2
                </text>
                <text transform="matrix(1 0 0 1 154.6667 249.0003)" fill="#888888" font-family="'Roboto'" font-size="9">
                    3
                </text>
                <text transform="matrix(1 0 0 1 232.1667 249.0003)" fill="#888888" font-family="'Roboto'" font-size="9">
                    4
                </text>
                <text transform="matrix(1 0 0 1 309 249.0003)" fill="#888888" font-family="'Roboto'" font-size="9">5
                </text>
                <text transform="matrix(1 0 0 1 386.6667 249.0003)" fill="#888888" font-family="'Roboto'" font-size="9">
                    6
                </text>
                <text transform="matrix(1 0 0 1 464.3333 249.0003)" fill="#888888" font-family="'Roboto'" font-size="9">
                    7
                </text>
            </g>
            <g id="Layer_5">
                <polygon opacity="0.36" stroke-miterlimit="10" points="0,223.3 48,138.5 154.7,169 211,88.5
              294.5,80.5 380,165.2 437,75.5 469.5,223.3 	"></polygon>
            </g>
            <g id="Layer_4">
                <polygon stroke-miterlimit="10" points="469.3,222.7 1,222.7 48.7,166.7 155.7,188.3 212,132.7
              296.7,128 380.7,184.3 436.7,125 	"></polygon>
            </g>
        </g>
    </defs>
</svg>
<button id="demo-show-toast" class="mdl-button mdl-js-button mdl-button--raised" type="button" style="display: none">
    Show Toast
</button>
<div id="demo-toast-example" class="mdl-js-snackbar mdl-snackbar">
    <div class="mdl-snackbar__text"></div>
    <button class="mdl-snackbar__action" type="button"></button>
</div>
<%--<a href="https://github.com/google/material-design-lite/blob/mdl-1.x/templates/dashboard/" target="_blank"--%>
<script src="${pageContext.request.contextPath}/static/js/material.min.js"></script>
<%--<script src="https://code.getmdl.io/1.3.0/material.min.js"></script>--%>
<%--<script>--%>
<%--    fetch('url', {--%>
<%--        --%>
<%--    }).then()--%>
<%--</script>--%>
<script>
    var modal = document.getElementById('modal-wrapper');
    window.onclick = function (event) {
        if (event.target === modal) {
            modal.style.display = "none";
        }
    }
</script>
<script>
    var modal = document.getElementById('modal-wrapper');
    var boolReg = `${pageContext.findAttribute("user.registration")}`;
    var boolLogin = `${pageContext.findAttribute("user.login.exception")}`;
    var toast = `${pageContext.findAttribute("toast")}`;

    if (boolReg) {
        modal.style.display = "block";
        setTimeout(() => {
            var registrationButton = document.querySelector('#registration > span');
            registrationButton.click();
        }, 60);
    }

    if (boolLogin) {
        modal.style.display = "block";
        setTimeout(() => {
            var registrationButton = document.querySelector('#login > span');
            registrationButton.click();
        }, 60);
    }

    window['counter'] = 0;
    var snackbarContainer = document.querySelector('#demo-toast-example');
    var showToastButton = document.querySelector('#demo-show-toast');

    showToastButton.addEventListener('click', function () {
        setTimeout(() => {
            var data = {
                message: toast
            };

            snackbarContainer.MaterialSnackbar.showSnackbar(data);
        }, 1000);
    });

    if (toast) {
        showToastButton.click();
    }

    // window.onbeforeunload = (e) => {
    //     e.preventDefault();
    // }
</script>
</body>
</html>
