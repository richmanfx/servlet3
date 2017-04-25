<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Скрапинг vhfdx.ru</title>

    <link rel="stylesheet" type="text/css" href="../../../servlet3-1.1/css/apps.css">
    <link rel="stylesheet" type="text/css" href="../../../css/apps.css">

    <script type="text/javascript" src="../../../servlet3-1.1/js/jquery.min.js"></script>
    <script type="text/javascript" src="../../../js/jquery.min.js"></script>

    <#--<script>-->
        <#--$(function(){-->
            <#--$('.hidescreen, .load_page').fadeIn(10); //показывает фон и индикатор-->
            <#--$(window).load(function() {-->
                <#--$('.hidescreen,.load_page').fadeOut(600); //скрывает, после загрузки страницы-->
            <#--});-->
            <#--$('.close').click(function(){ //для тех кому невтерпеж-->
                <#--$('.hidescreen,.load_page').fadeOut(300);-->
            <#--});-->
        <#--});-->
    <#--</script>-->

</head>
<body>
    <div class='load_page'>
        <b class='close'>X</b>Страница загружается...
        <br>
        <img src="../../../img/loader.gif" /> <!--картинка индикатора загрузки-->
        <img src="../../../servlet3-1.1/img/loader.gif" /> <!--картинка индикатора загрузки-->
        <br>
        <b>Подождите</b>
    </div>

    <div class='hidescreen'></div>

    <div align="center">
        <table border="1">
        <caption>
            Информация по квадратам участников соревнований с сайта VHFDX.RU
        </caption>
        <tr>
            <th>Позывной</th>
            <th>Квадрат</th>
            <th>Диапазоны</th>
            <th>Дополнительная информация</th>
        </tr>
        <#list tableStrings as tableString>
    	    ${tableString}
        </#list>
        </table>
    </div>
</body>
</html>
