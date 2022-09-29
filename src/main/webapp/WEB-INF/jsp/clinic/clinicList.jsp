<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="clinic">
    <h2>Tus cl�nicas</h2>
    <table id="ownersTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 800px;">Nombre</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${clinic}" var="clinic">
            <tr>
                <td>
                    <c:out value="${clinic.name}"/>
                </td>
                <td>
                	<spring:url value="clinic/del/{clinicId}" var="delUrl">
                		<spring:param name="clinicId" value="${clinic.id}"></spring:param>
                	</spring:url>
    				<a href="${fn:escapeXml(delUrl)}" class="btn btn-default">Borrar</a>
                </td>         
            </tr>
        </c:forEach>
    </table>
    <spring:url value="clinic/new" var="addUrl"></spring:url>
    <a href="${fn:escapeXml(addUrl)}" class="btn btn-default">Nueva cl�nica</a>
    </tbody>
</petclinic:layout>
