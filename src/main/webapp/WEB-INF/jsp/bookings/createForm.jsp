<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="owners">
	<jsp:attribute name="customScript">
        <script>
            $(function () {
                $("#startDate").datepicker({dateFormat: 'yy/mm/dd'});
                $("#finishDate").datepicker({dateFormat: 'yy/mm/dd'});
            });
        </script>
    </jsp:attribute>
    <jsp:body>
    <h2>
        Nueva Reserva
    </h2>
    <form:form modelAttribute="hotel" class="form-horizontal" id="add-booking-form">
        <div class="form-group has-feedback">
            <petclinic:inputField label="Fecha de inicio: " name="startDate"/>
            <petclinic:inputField label="Fecha de fin: " name="finishDate"/>
            <div class="row">
            	<div class="col-md-2"></div>
            	<div class="col-md">
            	<small class="form-text text-muted">Selecciona una habitación entre la 1 y la 30</small>
            	</div>
            </div>
            <petclinic:inputField label="Habitación: " name="room"/>
            <div class="control-group">
               <petclinic:selectField name="pet" label="Mascota:" names="${pets}" size="1"/>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button class="btn btn-default" type="submit">Confirmar</button>
            </div>
        </div>
    </form:form>
    </jsp:body>
</petclinic:layout>