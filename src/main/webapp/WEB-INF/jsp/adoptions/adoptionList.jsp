<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="bookings">
    <h2>Adopciones</h2>

    <table id="ownersTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Dueño</th>
            <th style="width: 200px;">Mascota</th>
            <th style="width: 120px">Descripcion:</th>
            <th style="width: 50px"></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${adoptions}" var="adoption">
            <tr>
                <td>
                    <c:out value="${adoption.pet.owner.firstName}"/>
                </td>
                <td>
                    <c:out value="${adoption.pet}"/>
                </td>
                <td>
                    <c:out value="${adoption.description}"/>
                </td>
                <td>
                <c:choose>
                <c:when test="${owner==adoption.pet.owner.id}"></c:when>
                <c:when test="${owner!=adoption.pet.owner.id}">
                <spring:url value="adoptions/{adoptionId}/{petId}" var="adoptUrl">
                		<spring:param name="adoptionId" value="${adoption.id}"></spring:param>
                		<spring:param name="petId" value="${adoption.pet.id}"></spring:param>
                	</spring:url>
    				<a href="${fn:escapeXml(adoptUrl)}" class="btn btn-default">Aceptar adopción</a>
                </c:when>
                </c:choose>
                </td>    
            </tr>
        </c:forEach>
    </table>
    <spring:url value="adoptions/new" var="addUrl"></spring:url>
    <a href="${fn:escapeXml(addUrl)}" class="btn btn-default">Añadir una adopción!!!</a>
    </tbody>
    </tbody>
</petclinic:layout>
