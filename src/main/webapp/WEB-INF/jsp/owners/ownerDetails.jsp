<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="owners">

    <h2>Información del Propietario</h2>


    <table class="table table-striped">
        <tr>
            <th>Nombre</th>
            <td><b><c:out value="${owner.firstName} ${owner.lastName}"/></b></td>
        </tr>
        <tr>
            <th>Dirección</th>
            <td><c:out value="${owner.address}"/></td>
        </tr>
        <tr>
            <th>Ciudad</th>
            <td><c:out value="${owner.city}"/></td>
        </tr>
        <tr>
            <th>Teléfono</th>
            <td><c:out value="${owner.telephone}"/></td>
        </tr>
    </table>

	<sec:authorize access="hasAnyAuthority('admin','owner')">
	    <spring:url value="{ownerId}/edit" var="editUrl">
	        <spring:param name="ownerId" value="${owner.id}"/>
	    </spring:url>
	    <a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Editar Propietario</a>
	</sec:authorize>
	
    <spring:url value="{ownerId}/pets/new" var="addUrl">
        <spring:param name="ownerId" value="${owner.id}"/>
    </spring:url>
    <a href="${fn:escapeXml(addUrl)}" class="btn btn-default">Añadir Nueva Mascota</a>
	
	<sec:authorize access="hasAuthority('admin')">
	<spring:url value="{ownerId}/delete" var="DUrl">
        <spring:param name="ownerId" value="${owner.id}"/>
    </spring:url>
    <a href="${fn:escapeXml(DUrl)}" class="btn btn-default">Borrar Propietario</a>
	</sec:authorize>

    <br/>
    <br/>
    <br/>
    <h2>Mascotas y Visitas</h2>

    <table class="table table-striped">
        <c:forEach var="pet" items="${owner.pets}">

            <tr>
                <td valign="top">
                    <dl class="dl-horizontal">
                        <dt>Nombre</dt>
                        <dd><c:out value="${pet.name}"/></dd>
                        <dt>Fecha de Nacimiento</dt>
                        <dd><petclinic:localDate date="${pet.birthDate}" pattern="yyyy-MM-dd"/></dd>
                        <dt>Tipo</dt>
                        <dd><c:out value="${pet.type.name}"/></dd>
                    </dl>
                </td>
                <td valign="top">
                    <table class="table-condensed">
                        <thead>
                        <tr>
                            <th>Fecha de Visita</th>
                            <th>Descripción</th>
                        </tr>
                        </thead>
                        <c:forEach var="visit" items="${pet.visits}">
                            <tr>
                                <td><petclinic:localDate date="${visit.date}" pattern="dd-MM-yyyy"/></td>
                                <td><c:out value="${visit.description}"/></td>
                                <td>
                                <spring:url value="/owners/{petId}/{ownerId}/delete/visit/{visitId}" var="DvisitUrl">
                                	<spring:param name="ownerId" value="${owner.id}"></spring:param>
                                    <spring:param name="visitId" value="${visit.id}"/>
                                    <spring:param name="petId" value="1"/>
                                </spring:url>
                                <a href="${fn:escapeXml(DvisitUrl)}">Borrar Visita</a>
                            </td>
                            </tr>
                        </c:forEach>
	                        <tr>
	                            <td>
	                                <spring:url value="/owners/{ownerId}/pets/{petId}/edit" var="petUrl">
	                                    <spring:param name="ownerId" value="${owner.id}"/>
	                                    <spring:param name="petId" value="${pet.id}"/>
	                                </spring:url>
	                                <a href="${fn:escapeXml(petUrl)}">Editar Mascota</a>
	                            </td>
	                            <td>
	                                <spring:url value="/owners/{ownerId}/pets/{petId}/visits/new" var="visitUrl">
	                                    <spring:param name="ownerId" value="${owner.id}"/>
	                                    <spring:param name="petId" value="${pet.id}"/>
	                                </spring:url>
	                                <a href="${fn:escapeXml(visitUrl)}">Añadir Visita</a>
	                            </td>
	                            <td>
	                                <spring:url value="/owners/{ownerId}/delete/{petId}" var="DpetUrl">
	                                	<spring:param name="ownerId" value="${owner.id}"/>
	                                    <spring:param name="petId" value="${pet.id}"/>
	                                </spring:url>
                                	<a href="${fn:escapeXml(DpetUrl)}">Borrar Mascota</a>
                            </td>
	                	</tr>
                    </table>
                </td>
            </tr>

        </c:forEach>
    </table>

</petclinic:layout>
