<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	pageContext.setAttribute("space", "\n");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="${pageContext.request.contextPath}/assets/css/mysite.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/assets/css/board.css" rel="stylesheet" type="text/css">
<title>Mysite</title>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"></c:import>
		<!-- header -->
		<c:import url="/WEB-INF/views/includes/navigation.jsp"></c:import>
		<!-- /navigation -->
		<div id="content">
			<div id="c_box">
				<div id="board">
					<h2>게시판-리스트</h2>
					<form action="${pageContext.request.contextPath}/rboard/list" method="get">
						<input type="text" id="kwd" name="kwd" value=""> <input type="submit" value="찾기">
					</form>
					<table class="tbl-ex">
						<tr>
							<th>번호</th>
							<th>제목</th>
							<th>글쓴이</th>
							<th>조회수</th>
							<th>작성일</th>
							<th>&nbsp;</th>
						</tr>
						<c:forEach items="${map.list}" var="vo">
							<tr>
								<td>${vo.no}</td>
								<td style="text-align: left"><c:choose>
										<c:when test="${'delete' eq vo.state }">
											<c:if test="${vo.depth ne 0 }">
												<c:forEach begin="0" end="${vo.depth }" step="1">
													&nbsp;
												</c:forEach>
												<img src="${pageContext.request.contextPath}/assets/images/reply.png">
											</c:if>
											[삭제된 글입니다.]
										</c:when>
										<c:otherwise>
											<a href="${pageContext.request.contextPath}/rboard/read?no=${vo.no}&crtPage=${crtPage}&kwd=${map.kwd}"> <c:if test="${vo.depth ne 0 }">
													<c:forEach begin="0" end="${vo.depth }" step="1">
														&nbsp;
													</c:forEach>
													<img src="${pageContext.request.contextPath}/assets/images/reply.png">
												</c:if> ${vo.title}
											</a>
										</c:otherwise>
									</c:choose></td>
								<td>${vo.name}</td>
								<td>${vo.hit}</td>
								<td>${vo.regDate}</td>
								<td><c:if test="${vo.userNo == sessionScope.authUser.no && 'delete' ne vo.state}">
										<a href="${pageContext.request.contextPath}/rboard/delete?no=${vo.no}&crtPage=${crtPage}&kwd=${map.kwd}" class="del">삭제</a>
									</c:if></td>
							</tr>
						</c:forEach>
					</table>
					<div class="pager">
						<ul>
							<c:if test="${map.prev eq true }">
								<li><a href="${pageContext.request.contextPath}/rboard/list?crtPage=${map.startPageBtnNo-1}&kwd=${map.kwd}">◀</a></li>
							</c:if>
							<c:forEach begin="${map.startPageBtnNo }" end="${map.endPageBtnNo }" step="1" var="page">
								<c:choose>
									<c:when test="${param.crtPage eq page }">
										<li class="selected"><a href="${pageContext.request.contextPath}/rboard/list?crtPage=${page}&kwd=${map.kwd}">${page}</a></li>
									</c:when>
									<c:otherwise>
										<li><a href="${pageContext.request.contextPath}/rboard/list?crtPage=${page}&kwd=${map.kwd}">${page}</a></li>
									</c:otherwise>
								</c:choose>
							</c:forEach>
							<c:if test="${map.next eq true }">
								<li><a href="${pageContext.request.contextPath}/rboard/list?crtPage=${map.endPageBtnNo+1}&kwd=${map.kwd}">▶</a></li>
							</c:if>
						</ul>
					</div>
					<div class="bottom">
						<c:if test="${not empty sessionScope.authUser }">
							<a href="${pageContext.request.contextPath}/rboard/wform?crtPage=${crtPage}&kwd=${map.kwd}" id="new-book">글쓰기</a>
						</c:if>
					</div>
				</div>
				<!-- /board -->
			</div>
			<!-- /c_box -->
		</div>
		<!-- /content -->
		<c:import url="/WEB-INF/views/includes/footer.jsp"></c:import>
		<!-- /footer -->
	</div>
	<!-- /container -->
</body>
</html>
