<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!--  >%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%-->
<%@ attribute name="name" required="true" rtexprvalue="true"
	description="Name of the active menu: home, owners, vets or error"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<nav class="navbar navbar-default" role="navigation">
	<div class="container">
		<div class="navbar-header">
			<a class="navbar-brand"
				href="<spring:url value="/" htmlEscape="true" />"><span></span></a>
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target="#main-navbar">
				<span class="sr-only"><os-p>Toggle navigation</os-p></span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
		</div>
		<div class="navbar-collapse collapse" id="main-navbar">
			<ul class="nav navbar-nav">
				<sec:authorize access="hasAuthority('owner')">
				<petclinic:menuItem active="${name eq 'owners'}" url="/owners/find"
					title="find owners">
					<span>perfil</span>
				</petclinic:menuItem>
				</sec:authorize>
				
				<sec:authorize access="hasAuthority('admin')">
				<petclinic:menuItem active="${name eq 'owners'}" url="/owners/find"
					title="find owners">
					<span>propietarios</span>
				</petclinic:menuItem>
				</sec:authorize>
				
				<sec:authorize access="hasAuthority('owner')">
				<petclinic:menuItem active="${name eq 'adoptions'}" url="/adoptions"
					title="find adoptions">
					<span>adopci&oacute;n</span>
				</petclinic:menuItem>
				</sec:authorize>

				<sec:authorize access="hasAnyAuthority('admin','owner')">
				<petclinic:menuItem active="${name eq 'vets'}" url="/vets"
					title="veterinarians">
					<span>Veterinarios</span>
				</petclinic:menuItem>
				</sec:authorize>
				
				<sec:authorize access="hasAuthority('owner')">
				<petclinic:menuItem active="${name eq 'hotel'}" url="/hotel"
					title="bookings">
					<span>Hotel</span>
				</petclinic:menuItem>
				</sec:authorize>
				
				<sec:authorize access="hasAuthority('clinicOwner')">
				<petclinic:menuItem active="${name eq 'clinicas'}" url="/clinic/price"
					title="clinic">
					<span>Plan de precios</span>
				</petclinic:menuItem>
				</sec:authorize>
				
				<sec:authorize access="hasAuthority('clinicOwner')">
				<petclinic:menuItem active="${name eq 'clinicas'}" url="/clinic"
					title="clinic">
					<span>Tus Cl&iacute;nicas</span>
				</petclinic:menuItem>
				</sec:authorize>
				
				<sec:authorize access="!hasAnyAuthority('admin','owner','clinicOwner')">
				<petclinic:menuItem active="${name eq 'clinicas'}" url="/contact/ca"
					title="CA">
					<span>Acuerdo de Cliente</span>
				</petclinic:menuItem>
				</sec:authorize>
				
				<sec:authorize access="hasAnyAuthority('admin','owner')">
				<petclinic:menuItem active="${name eq 'traductor'}" url="/translate"
					title="traductor">
					<span>Traductor</span>
				</petclinic:menuItem>
				</sec:authorize>
				
				<sec:authorize access="hasAnyAuthority('admin','owner')">
				<petclinic:menuItem active="${name eq 'interes'}" url="/sites"
					title="interes">
					<span>Lugares</span>
				</petclinic:menuItem>
				</sec:authorize>
				
				<sec:authorize access="hasAnyAuthority('admin','owner')">
				<petclinic:menuItem active="${name eq 'causes'}" url="/causes"
					title="causes">
					<span>Causas</span>
				</petclinic:menuItem>
				</sec:authorize>
			</ul>




			<ul class="nav navbar-nav navbar-right">
				<sec:authorize access="!isAuthenticated()">
					<li><a href="<c:url value="/login" />">Iniciar sesi&oacute;n</a></li>
					<li><a href="<c:url value="/users/new" />">Registrarse</a></li>
				</sec:authorize>
				<sec:authorize access="isAuthenticated()">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown"> <span class="glyphicon glyphicon-user"></span>
							<strong><sec:authentication property="name" /></strong> <span
							class="glyphicon glyphicon-chevron-down"></span>
					</a>
						<ul class="dropdown-menu">
							<li>
								<div class="navbar-login">
									<div class="row">
										<div class="col-lg-4">
											<p class="text-center">
												<span class="glyphicon glyphicon-user icon-size"></span>
											</p>
										</div>
										<div class="col-lg-8">
											<p class="text-left">
												<strong><sec:authentication property="name" /></strong>
											</p>
											<p class="text-left">
												<a href="<c:url value="/logout" />"
													class="btn btn-primary btn-block btn-sm">Cerrar sesi&oacute;n</a>
											</p>
										</div>
									</div>
								</div>
							</li>
							<li class="divider"></li>
<!-- 							
                            <li> 
								<div class="navbar-login navbar-login-session">
									<div class="row">
										<div class="col-lg-12">
											<p>
												<a href="#" class="btn btn-primary btn-block">My Profile</a>
												<a href="#" class="btn btn-danger btn-block">Change
													Password</a>
											</p>
										</div>
									</div>
								</div>
							</li>
-->
						</ul></li>
				</sec:authorize>
			</ul>
		</div>



	</div>
</nav>
<script>
	var beamer_config = {
		product_id : "HcFJTAfP44423" //DO NOT CHANGE: This is your product code on Beamer
	};
</script>
<script type="text/javascript" src="https://app.getbeamer.com/js/beamer-embed.js" defer="defer"></script>
					