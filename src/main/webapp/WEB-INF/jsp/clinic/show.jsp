<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="clinic">
    <h2>Detalles</h2>
    <table id="ownersTable" class="table table-striped">
        <thead>
        <tr>
        	<th></th>
            <th style="width: 200px;">SLA</th>
            <th style="width: 200px;">clínicas</th>
            <th style="width: 200px;">Soporte de incidencias</th>
            <th style="width: 200px;">veterinarios</th>
            <th style="width: 200px;">visitas/mes</th>
            <th style="width: 200px;">tipos de mascota</th>
            <th style="width: 200px;">disponibilidad</th>
            <th style="width: 200px;">precio</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <tr>
        	<td>Basic
        		<c:if test="${clinic == 0}">
        			<span class="glyphicon glyphicon-star" aria-hidden="true"></span>
        		</c:if>
        	</td>
        	<td>
        		<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
        	</td>
        	<td>
        		<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
        	</td>
        	<td>
        		<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
        	</td>
        	<td>1</td><td>1000</td><td>2</td><td>75%</td><td>9.95</td>
        	<td>
        		<spring:url value="/clinic/change/{level}" var="c1Url">
                		<spring:param name="level" value="${0}"></spring:param>
                </spring:url>
    			<a href="${fn:escapeXml(c1Url)}" class="btn btn-default">Seleccionar</a>
        	</td>
        </tr>
        <tr>
        	<td>Advance	
        		<c:if test="${clinic == 1}">
        			<span class="glyphicon glyphicon-star" aria-hidden="true"></span>
        		</c:if>
        	</td>
        	<td>
        		<span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
        	</td>
        	<td>
        		<span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
        	</td>
        	<td>
        		<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
        	</td>
        	<td>4</td><td>5000</td><td>5</td><td>90%</td><td>19.95</td>
        	<td>
        		<spring:url value="/clinic/change/{level}" var="c2Url">
                		<spring:param name="level" value="${1}"></spring:param>
                </spring:url>
    			<a href="${fn:escapeXml(c2Url)}" class="btn btn-default">Seleccionar</a>
        	</td>
        </tr>
        <tr>
        	<td>Pro
        		<c:if test="${clinic == 2}">
        			<span class="glyphicon glyphicon-star" aria-hidden="true"></span>
        		</c:if>
        	</td>
        	<td>
        		<span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
        	</td>
        	<td>
        		<span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
        	</td>
        	<td>
        		<span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
        	</td>
        	<td>ilimitados</td><td>ilimitados</td><td>ilimitados</td><td>99%</td><td>49.95</td>
        	<td>
        		<spring:url value="/clinic/change/{level}" var="c3Url">
                		<spring:param name="level" value="${2}"></spring:param>
                </spring:url>
    			<a href="${fn:escapeXml(c3Url)}" class="btn btn-default">Seleccionar</a>
        	</td>
        </tr>
    </table>
</petclinic:layout>