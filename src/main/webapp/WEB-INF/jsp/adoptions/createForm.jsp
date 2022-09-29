<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="adoptions">
    <h2>
        Nueva Adopción
    </h2>
    <form:form modelAttribute="adoption" class="form-horizontal" id="add-booking-form">
        <div class="form-group has-feedback">
            <petclinic:inputField label="Descripción: " name="description"/>
            <div class="control-group">
               <petclinic:selectField name="pet" label="Mascota:" names="${pets}" size="1"/>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
            <c:choose>
            <c:when test="${pets.size() == 0}">
        		<spring:url value="/adoptions" var="addUrl"></spring:url>
    			<a href="${fn:escapeXml(addUrl)}" class="btn btn-default">Volver</a>
        	</c:when>
        	<c:otherwise>
        		<button class="btn btn-default" type="submit">Confirmar</button>
        	</c:otherwise>
        	</c:choose>
            </div>
        </div>
    </form:form>
</petclinic:layout>