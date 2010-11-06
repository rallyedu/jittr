<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>

<layout:default>
    <jsp:attribute name="content">
        <h1>Friends</h1>
        <c:forEach var="friend" items="${friends}">
            <div class="friend">
                <a href="<c:out value="http://www.twitter.com/${friend.screenName}"/>"><img src="<c:out value="${friend.profileImageURL}"/>" height="48" width="48" alt="<c:out value="${friend.screenName}"/>"/></a>
            </div>
        </c:forEach>
    </jsp:attribute>
</layout:default>
