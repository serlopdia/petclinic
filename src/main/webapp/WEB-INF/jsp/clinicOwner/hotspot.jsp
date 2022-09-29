<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="HotSpot">
<h1>Sitios de interés para disfrutar con tu mascota en Sevilla</h1>
	<c:forEach var = "linea" items="${response2}">
		<c:out value="${linea}"/><br>
	</c:forEach><br>
<h1>Sitios de interés para disfrutar con tu mascota en Málaga</h1>
	<c:forEach var = "linea" items="${malaga}">
		<c:out value="${linea}"/><br>
	</c:forEach><br>
<h1>Sitios de interés para disfrutar con tu mascota en Marchena</h1>
	<c:forEach var = "linea" items="${marchena}">
		<c:out value="${linea}"/><br>
	</c:forEach><br>
<h1>Sitios de interés para disfrutar con tu mascota en Ceuta</h1>
	<c:forEach var = "linea" items="${ceuta}">
		<c:out value="${linea}"/><br>
	</c:forEach>
</petclinic:layout>