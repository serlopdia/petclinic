<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="causes">
    <h2>Causas</h2>

    <table id="ownersTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 300px;">Nombre</th>
            <th style="width: 300px">Recaudado</th>
            <th style="width: 300px">Objetivo</th>
            <th style="width: 300px">Situación</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${causes}" var="cause">
            <tr>
                <td>
                    <c:out value="${cause.name}"/>
                </td>
                <td>
                    <c:out value="${cause.donationsTotal} euros"/>
                </td>
                <td>
                    <c:out value="${cause.target} euros"/>
                </td>
                <td>
                    <c:choose>
					    <c:when test="${cause.isClosed==false}">
					        Abierta
					    </c:when>    
					    <c:otherwise>
					        Cerrada
					    </c:otherwise>
					</c:choose>
                </td>
            	<c:if test="${cause.isClosed==false}">
                <sec:authorize access="hasAuthority('owner')">
                <td>
				    <spring:url value="causes/{causeId}/donations/new" var="addUrl">
				        <spring:param name="causeId" value="${cause.id}"/>
				    </spring:url>
				    <a href="${fn:escapeXml(addUrl)}" class="btn btn-default">Realizar Donación</a>
                </td>
                </sec:authorize>
            	</c:if>
                <td>
                	<spring:url value="causes/{causeId}" var="infoUrl">
                		<spring:param name="causeId" value="${cause.id}"></spring:param>
                	</spring:url>
    				<a href="${fn:escapeXml(infoUrl)}" class="btn btn-default">Más información</a>
                </td>
            </tr>
        </c:forEach>
    </table>
    <sec:authorize access="hasAuthority('owner')">
    <spring:url value="causes/new" var="addUrl"></spring:url>
    <a href="${fn:escapeXml(addUrl)}" class="btn btn-default">Crea causa</a>
    </sec:authorize>
    </tbody>
</petclinic:layout>
