<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ include file="template/localHeader.jsp"%>

<b class="boxHeader"><openmrs:message code="App.add" /></b>
<div class="box">
	<form id="appAddForm" action="" method="post"
		enctype="multipart/form-data">
		<input type="file" name="appFile" size="40" /> 
		<input type="hidden" name="action" value="upload" /> 
		<input type="submit" value='<openmrs:message code="App.upload"/>' />
	</form>
</div>

<p>Installed apps:</p>
<ul>
	<c:forEach var="app" items="${appList}">
		<li>${app}</li>
	</c:forEach>
</ul>

<%@ include file="/WEB-INF/template/footer.jsp"%>