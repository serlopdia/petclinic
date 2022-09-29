<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!--  >%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%-->

<petclinic:layout pageName="owners">
	
	<sec:authorize access="hasAuthority('admin')">
    <h2>Buscar Propietarios</h2>
	</sec:authorize>
    
    <form:form modelAttribute="owner" action="/owners" method="get" class="form-horizontal"
               id="search-owner-form">
        <div class="form-group">
            <div class="control-group" id="lastName">
            	<sec:authorize access="hasAnyAuthority('admin', 'veterinarian')">
	                <label class="col-sm-2 control-label">Apellidos </label>
	                <div class="col-sm-10">
	                    <form:input class="form-control" path="lastName" size="30" maxlength="80"/>
	                    <span class="help-inline"><form:errors path="*"/></span>
	                </div>
	        	</sec:authorize>
	        	<sec:authorize access="hasAuthority('owner')">
	                <div class="col-sm-10">
	                    <form:input type="hidden" class="form-control" path="lastName" size="30" maxlength="80"/>
	                    <span class="help-inline"><form:errors path="*"/></span>
	                </div>
	        	</sec:authorize>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
	            <sec:authorize access="hasAuthority('owner')">
	                <button type="submit" class="btn btn-default">Ver mis datos</button>
	            </sec:authorize>
	            <sec:authorize access="hasAnyAuthority('admin','veterinarian')">
	                <button type="submit" class="btn btn-default">Buscar Propietario</button>
	            </sec:authorize>
            </div>
        </div>

    </form:form>

    <br/> 
    <sec:authorize access="hasAuthority('admin')">
		<a class="btn btn-default" href='<spring:url value="/owners/new" htmlEscape="true"/>'>Añadir Propietario</a>
	</sec:authorize>
	
</petclinic:layout>
