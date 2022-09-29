<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="DonationForm">
    <jsp:attribute name="customScript">
        <script>
            $(function () {
                $("#date").datepicker({dateFormat: 'yy/mm/dd'});
            });
        </script>
    </jsp:attribute>
	<jsp:body>
		<h2>Donaci�n</h2>
		<form:form modelAttribute="donation" class="form-horizontal" >
            <input type="hidden" name="id" value="${donation.id}"/>
            <div class="form-group has-feedback">
                <petclinic:inputField label="Cantidad" name="amount"/>
            </div>

	        <div class="form-group">
	            <div class="col-sm-offset-2 col-sm-10">
	                <button class="btn btn-default" type="submit">Confirmar</button>
	            </div>
	        </div>
		</form:form>
	</jsp:body>
</petclinic:layout>
