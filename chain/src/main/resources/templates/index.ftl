<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:th="http://www.thymeleaf.org"
      xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity3"
      xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout">

<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <title>Blockchain</title>
    <link href="css/style.css" rel="stylesheet">
</head>
<body>

<form id="addMessage" enctype="multipart/form-data" action="/index" method="post">
    <div><label> Сообщение : <input type="text" name="message"/> </label></div>
    <input type="file" name="file"/><br/><br/>
    <input type="submit" value="Отправить">
</form>
<p><#if chainvalid??>Действителен ли блокчейн: ${chainvalid}<#else></#if></p>
<table id="table" id="list" border="1">
    <caption>Блок цепочка</caption>
    <tr>
        <th>ID</th>
        <th>Хеш-код блока</th>
        <th>Предыдущий хеш-код</th>
        <th>Данные</th>
        <th>Время</th>
        <th>Одноразовый код</th>
    </tr>
    <#list blockchain as block>
        <form action="/index" method="get">
            <tr>
                <td><input name="id" value="${block.id}"></td>
                <td><input name="hash" value="${block.hash}"></td>
                <td><input name="previousHash" value="${block.previousHash}"></td>
                <td><input name="data" value="${block.data}"></td>
                <td><input name="timeStamp" value="${block.timeStamp}"></td>
                <td><input name="nonce" value="${block.nonce}"></td>
            </tr>
        </form>
    </#list>
</table>
</body>
</html>