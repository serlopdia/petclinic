<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>



<petclinic:layout pageName="VetsForm">
	
	<jsp:body>
		<h2>Veterinario</h2>
		<form:form modelAttribute="vet" class="form-horizontal" action="/vets/new" >
			<div class="form-group has-feedback">
				<input type="hidden" name="id" value="${vet.id}"/>
				<petclinic:inputField label="Nombre" name="firstName" />
				<petclinic:inputField label="Apellido" name="lastName" />
			</div>
			<div  class="col-sm-offset-2 col-sm-10">
				<div class="form-group">
					<c:forEach var="specialty" items="${specialties}">
							<label>${specialty.name}</label>
							<input type="checkbox" name="${specialty.name}" value="${specialty.id}"/>
							<br>     
                	</c:forEach>
                </div>
			</div>
			
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<c:choose>
	                    <c:when test="${vet['new']}">
	                        <button class="btn btn-default" type="submit">Guardar Veterinario</button>
	                    </c:when>
	                    <c:otherwise>
	                        <button class="btn btn-default" type="submit">Actualizar Veterinario</button>
	                    </c:otherwise>
	                </c:choose>
	            </div>
			</div>
		</form:form>
	</jsp:body>
</petclinic:layout>