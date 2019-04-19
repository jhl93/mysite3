<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	pageContext.setAttribute("newLine", "\n");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="${pageContext.request.contextPath}/assets/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/assets/css/mysite.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/assets/css/guestbook.css" rel="stylesheet" type="text/css">

<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/jquery/jquery-1.12.4.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/bootstrap/js/bootstrap.min.js"></script>
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
				<div id="guestbook">
					<h2>방명록</h2>
					<div id="addform">
						<table>
							<tr>
								<td>이름</td>
								<td><input id="name" type="text" name="name" value="${authUser.name}"></td>
								<td>비밀번호</td>
								<td><input id="password" type="password" name="password" value=""></td>
							</tr>
							<tr>
								<td colspan=4><textarea id="addContent" name="content" cols="75" rows="8"></textarea></td>
							</tr>
							<tr>
								<td colspan=4 align=right><input id="btnSubmit" type="submit" VALUE=" 확인 "></td>
							</tr>
						</table>
					</div>
					<div id="gbList">
						<input id="startNo" type="hidden" name="startNo" value="11">
					</div>
				</div>
				<!-- /guestbook -->
			</div>
			<!-- /c_box -->
		</div>
		<!-- /content -->
		<c:import url="/WEB-INF/views/includes/footer.jsp"></c:import>
		<!-- /footer -->
	</div>
	<!-- /container -->
	<!-- 삭제팝업(모달)창 -->
	<div class="modal fade" id="del-pop">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">방명록삭제</h4>
				</div>
				<div class="modal-body">
					<label>비밀번호</label> <input type="password" name="modalPassword" id="modalPassword"><br> <input type="text" name="modalNo" value="" id="modalNo"> <br>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
					<button type="button" class="btn btn-danger" id="btn_del">삭제</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->
</body>
<script type="text/javascript">

	// 첫화면 리스트 출력(5개)
	$(document).ready(function() {
		$.ajax({

			url : "${pageContext.request.contextPath }/api/gb/list",
			type : "post",
			/* contentType : "application/json", *//* JSON 형태로 보낼 때 */
			/* data : {email : email}, */

			dataType : "json",
			success : function(guestbookList) {
				/*성공시 처리해야될 코드 작성*/
				console.log(guestbookList);

				for (var i = 0; i < 10; i++) {
					render(guestbookList[i], "down");
				}

			},
			error : function(XHR, status, error) {
				console.error(status + " : " + error);
			}
		});
	});

	// 새 글 등록
	$("#btnSubmit").on("click", function() {
		var name = $("#name").val();
		var password = $("#password").val();
		var content = $("#addContent").val();

		$.ajax({

			url : "${pageContext.request.contextPath }/api/gb/add",
			type : "post",
			contentType : "application/json",
			data : JSON.stringify({
				name : name,
				password : password,
				content : content
			}),

			dataType : "json",
			success : function(guestbookVo) {
				/*성공시 처리해야될 코드 작성*/
				console.log("새글 등록");

				render(guestbookVo, "up");
				$("#name").val("");
				$("#password").val("");
				$("#addContent").val("");
				$("#startNo").val($("#startNo").val()*1 + 1);

			},
			error : function(XHR, status, error) {
				console.error(status + " : " + error);
			}
		});
	});

	// 삭제 버튼 클릭할 때
	$("#gbList").on("click", ".btnDel", function() {
		console.log("삭제버튼 클릭");
		var $this = $(this);
		var no = $this.data("no");
		console.log(no);

		$("#modalPassword").val("");
		$("#del-pop").modal();
		$("#modalNo").val(no);
	});

	// 삭제 팝업에서 삭제
	$("#btn_del").on("click", function() {
		console.log("팝업창 삭제 버튼 클릭");
		var password = $("#modalPassword").val();
		var no = $("#modalNo").val();

		console.log(password + " / " + no);

		$.ajax({

			url : "${pageContext.request.contextPath }/api/gb/delete",
			type : "post",
			/* contentType : "application/json",  */
			data : {
				password : password,
				no : no
			},

			dataType : "json",
			success : function(result) {
				/*성공시 처리해야될 코드 작성*/
				$("#del-pop").modal("hide");
				
				if (result == true) {
					console.log("삭제 성공");
					var tableClass = ".table" + no
					$(tableClass).remove();
					$("#startNo").val($("#startNo").val()*1 - 1);
				} else {
					console.log("삭제 실패");
				}
			},
			error : function(XHR, status, error) {
				console.error(status + " : " + error);
			}
		});
	});

	//스크롤이 화면 제일 아래에 왔을때
	$(window).scroll(function() {
		if ($(window).scrollTop() == $(document).height()- $(window).height()) {
			console.log("아래");
			
			var startNo = $("#startNo").val()*1;
			var endNo = startNo*1 + 2;
			
			$.ajax({

				url : "${pageContext.request.contextPath }/api/gb/scrollList",
				type : "post",
				/* contentType : "application/json",  */
				data : {
					startNo : startNo,
					endNo : endNo
				},

				dataType : "json",
				success : function(list) {
					/*성공시 처리해야될 코드 작성*/
					console.log("scroll list 출력");
					console.log(list);
					for (var i = 0; i < list.length; i++) {
						render(list[i], "down");
					}
					
					$("#startNo").val(endNo*1 + 1);
				},
				error : function(XHR, status, error) {
					console.error(status + " : " + error);
				}
			});
		}
	});

	// 데이터 그리기 함수
	function render(guestbookVo, updown) {
		var str = "";
		str += "<table width=510 border=1 class='table" + guestbookVo.no + "'>";
		str += "	<tr>";
		str += "		<td>" + guestbookVo.no + "</td>";
		str += "		<td>" + guestbookVo.name + "</td>";
		str += "		<td>" + guestbookVo.regDate + "</td>";
		str += "		<td><input class='btnDel' type='button' value='삭제' data-no=" + guestbookVo.no + "></td>";
		str += "	</tr>";
		str += "	<tr>";
		str += "		<td colspan=4>" + guestbookVo.content + "</td>";
		str += "	</tr>";
		str += "</table>";
		str += "<br class='table" + guestbookVo.no + "'>";

		if (updown == "up") {
			$("#gbList").prepend(str);
		} else if (updown == "down") {
			$("#gbList").append(str);
		} else {
			console.log("updown 오류");
		}
	}
</script>
</html>
