<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="CausesForm">
	
	<jsp:body>
		<h2>Causa</h2>
		<form:form modelAttribute="cause" class="form-horizontal" action="/causes/new" >
			<div class="form-group has-feedback">
				<input type="hidden" name="id" value="${cause.id}"/>
				<petclinic:inputField label="Nombre" name="name" />
				<petclinic:inputField label="Descripción" name="description" />
				<petclinic:inputField label="Organización" name="organization" />
				<petclinic:inputField label="Objetivo" name="target" />
			</div>

	        <div class="form-group">
	            <div class="col-sm-offset-2 col-sm-10">
	                <button class="btn btn-default" type="submit">Confirmar</button>
	            </div>
	        </div>
		</form:form>
	</jsp:body>
</petclinic:layout>