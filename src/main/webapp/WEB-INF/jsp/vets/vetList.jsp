<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="vets">
	<h2>Veterinarios</h2>
	<sec:authorize access="hasAuthority('admin')">
    	<a href="/vets/create">Nuevo veterinario</a>
    </sec:authorize>
    <table id="vetsTable" class="table table-striped">
        <thead>
        <tr>
            <th>Nombre</th>
            <th>Especialidades</th>
            <sec:authorize access="hasAuthority('admin')">
	            <th></th>
	            <th></th>
	        </sec:authorize>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${vets.vetList}" var="vet">
            <tr>
                <td>
                    <c:out value="${vet.firstName} ${vet.lastName}"/>
                </td>
               	<td>
                    <c:forEach var="specialty" items="${vet.specialties}">
                        <c:out value="${specialty.name} "/>
                        <spring:url value="/vets/delete/spec/{specId}" var="dspecUrl">
                        <spring:param name="specId" value="${specialty.id}"></spring:param>
                    </spring:url>
                    <sec:authorize access="hasAuthority('admin')">
                    	<a href="${fn:escapeXml(dspecUrl)}" style="color:red">X</a>
                    </sec:authorize>
                    </c:forEach>
                    <c:if test="${vet.nrOfSpecialties == 0}">ninguna</c:if>
               	</td>

				<sec:authorize access="hasAuthority('admin')">
	                <td>
	                    <spring:url value="/vets/edit/{vetId}" var="vetUrl">
	                        <spring:param name="vetId" value="${vet.id}"></spring:param>
	                    </spring:url>
	                    <a href="${fn:escapeXml(vetUrl)}">Editar</a>
	                </td>
	                
	                
	                <td>
	                    <spring:url value="/vets/delete/{vetId}" var="dvetUrl">
	                        <spring:param name="vetId" value="${vet.id}"></spring:param>
	                    </spring:url>
	                    <a href="${fn:escapeXml(dvetUrl)}">Borrar</a>
	                </td>
                </sec:authorize>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <table class="table-buttons">
        <tr>
            <td>
                <a href="<spring:url value="/vets.xml" htmlEscape="true" />">Ver en XML</a>
            </td>            
        </tr>
    </table>
</petclinic:layout>
