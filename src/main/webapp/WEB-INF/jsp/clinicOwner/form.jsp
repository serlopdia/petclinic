<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<petclinic:layout pageName="translate">
<form:form modelAttribute="cause" class="form-horizontal" id="add-booking-form">
	<petclinic:inputField label="Traducción: " name="description"/>
	<div class="form-group">
           <div class="col-sm-offset-2 col-sm-10">
            	<button class="btn btn-default" type="submit">Confirmar</button>
           </div>
      </div>
</form:form>
</petclinic:layout>