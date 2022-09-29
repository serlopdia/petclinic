<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="causes">

    <h2>Información de la causa</h2>

    <table class="table table-striped">
        <tr>
            <th>Nombre</th>
            <td><c:out value="${cause.name}"/></td>
        </tr>
        <tr>
            <th>Descripción</th>
            <td><c:out value="${cause.description}"/></td>
        </tr>
        <tr>
            <th>Organización</th>
            <td><c:out value="${cause.organization}"/></td>
        </tr>
        <tr>
            <th>Objetivo</th>
            <td><c:out value="${cause.target} euros"/></td>
        </tr>
        <tr>
            <th>Recaudado</th>
            <td><c:out value="${cause.donationsTotal} euros"/></td>
        </tr>
    </table>

	<c:if test="${ authority == 'admin' || cause.owner.user.username == username }">
	<sec:authorize access="hasAuthority('owner')">
	    <spring:url value="{causeId}/edit" var="editUrl">
	        <spring:param name="causeId" value="${cause.id}"/>
	    </spring:url>
	    <a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Editar Causa</a>
	</sec:authorize>
	
	<sec:authorize access="hasAnyAuthority('admin','owner')">
	<spring:url value="{causeId}/delete" var="delUrl">
	<spring:param name="causeId" value="${cause.id}"></spring:param>
	</spring:url>
	<a href="${fn:escapeXml(delUrl)}" class="btn btn-default">Eliminar Causa</a>
	</sec:authorize>
	</c:if>
	
	<c:if test="${cause.isClosed==false}">
	<sec:authorize access="hasAuthority('owner')">
	<spring:url value="{causeId}/donations/new" var="addUrl">
	<spring:param name="causeId" value="${cause.id}"/>
	</spring:url>
	<a href="${fn:escapeXml(addUrl)}" class="btn btn-default">Realizar Donación</a>
	</sec:authorize>
	</c:if>

    <br/>
    <br/>
    <br/>
    <h2>Donaciones</h2>

    <table class="table table-striped">
        <c:forEach var="donation" items="${cause.donations}">
            <tr>
                <td valign="top">
                    <dl class="dl-horizontal">
                        <dt>Fecha</dt>
                        <dd><petclinic:localDate date="${donation.date}" pattern="dd/MM/yyyy"/></dd>
                        <dt>Cantidad</dt>
                        <dd><c:out value="${donation.amount} euros"/></dd>
                        <dt>Donante</dt>
                        <dd><c:out value="${donation.owner.firstName} ${donation.owner.lastName}"/></dd>
                    </dl>
                </td>
                <td valign="top">
                	<table class="table-condensed">
                        <tr>
                        <c:if test="${donation.owner.user.username==username }">
                            <td>
                                <spring:url value="/causes/{causeId}/delete/{donationId}" var="DdonationUrl">
                                	<spring:param name="causeId" value="${cause.id}"/>
                                    <spring:param name="donationId" value="${donation.id}"/>
                                </spring:url>
                               	<a href="${fn:escapeXml(DdonationUrl)}">Borrar Donación</a>
                           </td>
                        </c:if>
                		</tr>
                	</table>
                </td>
            </tr>

        </c:forEach>
    </table>

</petclinic:layout>
