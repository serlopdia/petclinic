<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="clinic">
    <h2>Detalles</h2>
    <table id="ownersTable" class="table table-striped">
        <thead>
        <tr>
        	<th style="width: 200px;">Nombre</th>
        	<th style="width: 200px;">Apellidos</th>
            <th style="width: 200px;">Email</th>
            <th style="witdh: 200px;">Teléfono</th>
        </tr>
        </thead>
        <tbody>
        <tr>
     		<td>Antonio</td><td>Campos Gil</td><td>acotopografia@gmail.com</td><td>954 22 78 47</td>
        </tr>
        <tr>
        	<td>Alejandro</td><td>Sánchez Mayorga</td><td>alejandrosanchezmayorga@lopezdearenas.net</td><td>955 28 61 41</td>
        </tr>
        <tr>
        	<td>Javier</td><td>Pacheco Márquez</td><td>pachecomarquezjavier@gmail.com</td><td>954 90 88 27</td>
        </tr>
        <tr>
        	<td>Sergio</td><td>López Díaz</td><td>sergio.ld00@gmail.com</td><td>954 94 54 65</td>
        </tr>
        <tr>
        	<td>María</td><td>Barranco Márquez</td><td>m.barrancos@alumno.laudealtillo.com</td><td>954 22 53 96</td>
        </tr>
        <tr>
        	<td>José Ignacio</td><td>Castro Vázquez</td><td>JICASTROTIC99@gmail.com</td><td>954 03 50 30</td>
        </tr>
    </table>
    <sec:authorize access="hasAnyAuthority('admin','owner','clinicOwner')">
    <spring:url value="contact/ca" var="addUrl"></spring:url>
	<a href="${fn:escapeXml(addUrl)}" class="btn btn-default">Acuerdo de Cliente</a>
	</sec:authorize>
</petclinic:layout>