<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="in" uri="/../../tags/input.tld"%>
<%@ taglib prefix="mod" uri="/../../tags/modelSubmit.tld"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:formatDate value="${model.begin}" pattern="dd/MM/yyyy" var="formattedBegin"/>
<fmt:formatDate value="${model.end}" pattern="dd/MM/yyyy" var="formattedEnd"/>

<form method="post" action="${param.contextPath}/app/${requestScope.entity}/${parameter}">	
	
	<in:Input name="resourceType" display="Type de la ressource" placeholder="Type de la ressource" value="${model.reserved.type.name}"/>
	
	<div class="form-group">
		<label for="resource">Ressource</label>
		<select class="form-control" id="resource" name="reserved" required>
			<option value="${requestScope.reservation.reserved.id}" selected>${model.reserved.name}</option>
			<c:forEach items="${resources}" var="resource">
				<option value="${resource.id}">${resource.name}</option>
			</c:forEach>
		</select>
	</div>
			
	<div class="form-group">
		<label for="reserver">Utilisateur</label>
		<select class="form-control" id="reserver" name="reserver" readonly>
			<option value="${requestScope.reservation.reserver.id}" selected>${model.reserver.firstName} ${requestScope.reservation.reserver.lastName}</option>
		</select>
	</div>
	
	<in:Input name="begin" display="D�but" placeholder="D�but" value="${formattedBegin}" additionalHtml="data-provide=\"datepicker\" data-date-format=\"dd/mm/yyyy\"" type="date"/>
	<in:Input name="end" display="Fin" placeholder="Fin" value="${formattedEnd}" additionalHtml="data-provide=\"datepicker\" data-date-format=\"dd/mm/yyyy\"" type="date"/>
	<mod:ModelSubmit model="${model}"/>	
</form>