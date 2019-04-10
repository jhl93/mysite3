<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

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
					<h2>게시판-수정</h2>
					
					<form id="mform" class="board-form" method="post" action="${pageContext.request.contextPath}/board/modify">
						<input type="hidden" name="no" value="${boardvo.no}" >
				
						<table class="tbl-ex">
							<tr>
								<th colspan="2">글수정</th>
							</tr>
							<tr>
								<td class="label">제목</td>
								<td><input type="text" name="title" value="${boardvo.title}"></td>
							</tr>
							<tr>
								<td class="label">내용</td>
								<td>
									<textarea form="mform" id="content" name="content">${boardvo.content}</textarea>
								</td>
							</tr>
						</table>
				
						<div class="bottom">
							<a href="${pageContext.request.contextPath}/board/list">취소</a>
							<input type="submit" value="수정">
						</div>
					</form>	
					
				</div><!-- /board -->
			</div><!-- /c_box -->
		</div><!-- /content -->
			
		<c:import url="/WEB-INF/views/includes/footer.jsp"></c:import>
		<!-- /footer -->
	</div><!-- /container -->
</body>
</html>


