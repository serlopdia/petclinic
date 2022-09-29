<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="bookings">
    <h2>Reservas</h2>

    <table id="ownersTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Habitación</th>
            <th style="width: 200px;">Mascota</th>
            <th style="width: 120px">Fecha de inicio:</th>
            <th style="width: 120px">Fecha de fin:</th>
            <th style="width: 50px"></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${bookings}" var="booking">
            <tr>
                <td>
                    <c:out value="${booking.room}"/>
                </td>
                <td>
                    <c:out value="${booking.pet}"/>
                </td>
                <td>
                    <c:out value="${booking.startDate}"/>
                </td>
                <td>
                    <c:out value="${booking.finishDate}"/>
                </td>
                <td>
                	<spring:url value="hotel/delete/{hotelId}" var="delUrl">
                		<spring:param name="hotelId" value="${booking.id}"></spring:param>
                	</spring:url>
    				<a href="${fn:escapeXml(delUrl)}" class="btn btn-default">Eliminar reserva</a>
                </td>
                
      
<!--
                <td> 
                    <c:out value="${owner.user.username}"/> 
                </td>
                <td> 
                   <c:out value="${owner.user.password}"/> 
                </td> 
-->
                
            </tr>
        </c:forEach>
    </table>
    <spring:url value="hotel/new" var="addUrl"></spring:url>
    <a href="${fn:escapeXml(addUrl)}" class="btn btn-default">Reserva una habitación!!!</a>
    </tbody>
</petclinic:layout>
