
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Tour catalogue</h1><br>

<jsp:include page="/tours/catalogue"/>
<form id="catalogue-filter" action="catalogue" method="post">
    <h3>Tour type</h3>
    <select form="catalogue-filter" name="tour-type" id="tour-type">
        <option value="REST">Rest</option>
        <option value="EXCURSION">Excursion</option>
        <option value="SHOPPING">Shopping</option>
    </select>

    <h3>Min price</h3>
    <input id="min-price" name="min-price" type="number"/>
    <h3>Max price</h3>
    <input id="max-price" name="max-price" type="number"/>
    <h3>Min people number</h3>
    <input id="min-people-number" name="min-people-number" type="number"/>
    <h3>Max people number</h3>
    <input id="max-people-number" name="max-people-number" type="number"/>
    <h3>Hotel type</h3>
    <select form="catalogue-filter" name="hotel-type" id="hotel-type">
        <option value="HOSTEL">Hostel</option>
        <option value="ALL_INCLUSIVE">All inclusive</option>
    </select>
    <button type="submit" name="Filter" value="Filter" title="Filter">Filter</button>
</form>

</body>
</html>
