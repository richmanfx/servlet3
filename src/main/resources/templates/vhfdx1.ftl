<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Скрапинг vhfdx.ru</title>
    <link rel="stylesheet" type="text/css" href="../../../servlet3-1.1/css/apps.css">
    <link rel="stylesheet" type="text/css" href="../../../css/apps.css">
</head>
<body>
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
