<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="${pageContext.request.contextPath }/assets/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/assets/css/mysite.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/assets/css/gallery.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.12.4.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/bootstrap/js/bootstrap.min.js"></script>
<title>Mysite</title>
</head>
<body>
	<div id="container">
		<!-- header -->
		<c:import url="/WEB-INF/views/includes/header.jsp"></c:import>
		<!-- navigation -->
		<c:import url="/WEB-INF/views/includes/navigation.jsp"></c:import>
		<div id="content">
			<div id="c_box">
				<div id="gallery">
					<h2>갤러리</h2>
					<c:if test="${not empty sessionScope.authUser}">
					<input id="btnImgPop" class="btn btn-primary" type="button" value="이미지등록">
					</c:if>
					<c:if test="${empty sessionScope.authUser}">
					<br><br>
					</c:if>
					<ul id="galleryList">
					</ul>
				</div>
				<!-- /gallery -->
			</div>
			<!-- /c_box -->
		</div>
		<!-- /content -->
		<!-- footer -->
		<c:import url="/WEB-INF/views/includes/footer.jsp"></c:import>
	</div>
	<!-- /container -->
	<!-- 이미지등록 팝업(모달)창 -->
	<div class="modal fade" id="delPop">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">이미지등록</h4>
				</div>
				<form id="uploadForm" enctype="multipart/form-data">
					<div class="modal-body">
						<div class="formgroup">
							<label>코멘트작성</label><br> <input type="text" name="comments" id="comments"><br>
						</div>
						<div class="formgroup">
							<label>이미지선택</label><br> <input type="file" name="file" value="" id="file"> <br>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
						<button type="button" class="btn btn-primary" id="btnImgAdd">등록</button>
					</div>
				</form>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->
	<!-- 이미지보기 팝업(모달)창 -->
	<div class="modal fade" id="viewPop">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">이미지보기</h4>
				</div>
				<div class="modal-body">
					<div class="formgroup">
						<img id="popimg" src="">
					</div>
					<div id="popcomment" class="formgroup">
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
					<button type="button" class="btn btn-danger" id="btnDel" data-no="">삭제</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->
</body>
<script type="text/javascript">
	/* 이미지등록 팝업(모달)창 띄우기*/
	$("#btnImgPop").on("click", function() {
		$("#comments").val("");
		$("#file").val("");
		$("#delPop").modal();

	});

	/* 이미지보기 팝업(모달)창 띄우기*/
	$("#galleryList").on("click", ".view", function() {
		console.log("이미지 보기");
		var $this = $(this);
		var no = $this.data("no");
		console.log(no);
		
		$("#popcomment").empty();
		$("#btnDel").data("no", "");
		
		$.ajax({

			url : "${pageContext.request.contextPath }/gallery/getOne",
			type : "post",
			contentType : "application/json",
			data : JSON.stringify({no : no}),

			dataType : "json",
			success : function(galleryVo) {
				/*성공시 처리해야될 코드 작성*/
				console.log("이미지 크게 보기");
				$("#popimg").attr("src", "${pageContext.request.contextPath }/upload/" + galleryVo.saveName);
				$("#popcomment").append("<label>코멘트</label><br> " + galleryVo.comments);
				$("#btnDel").data("no", galleryVo.no);
				$("#viewPop").modal();

			},
			error : function(XHR, status, error) {
				console.error(status + " : " + error);
			}
		});
		
	});

	/* 이미지 등록*/
	$("#btnImgAdd").on("click", function() {
		var form = new FormData(document.getElementById("uploadForm"));
		console.log(form);

		$.ajax({

			url : "${pageContext.request.contextPath }/gallery/upload",
			type : "post",
			contentType : false,
			processData : false,
			data : form,

			dataType : "json",
			success : function(galleryVo) {
				/*성공시 처리해야될 코드 작성*/
				console.log("이미지 등록 성공");
				$("#delPop").modal("hide");
				
				render(galleryVo, "down");

			},
			error : function(XHR, status, error) {
				console.error(status + " : " + error);
			}
		});

	});

	/* 이미지 리스트 출력*/
	$(document).ready(function() {
		$.ajax({

			url : "${pageContext.request.contextPath }/gallery/getlist",
			type : "post",
			/* contentType : "application/json", *//* JSON 형태로 보낼 때 */
			/* data : {email : email}, */

			dataType : "json",
			success : function(galleryList) {
				/*성공시 처리해야될 코드 작성*/
				console.log(galleryList);

				for (var i = 0; i < galleryList.length; i++) {
					console.log("리스트 출력");
					render(galleryList[i], "down");
				}

			},
			error : function(XHR, status, error) {
				console.error(status + " : " + error);
			}
		});
	});
	
	/* 이미지 삭제*/
	$("#btnDel").on("click", function(){
		var $this = $(this);
		var no = $this.data("no");
		console.log(no);
		
		$.ajax({

			url : "${pageContext.request.contextPath }/gallery/delete",
			type : "post",
			/* contentType : "application/json", */
			data : {no : no},

			dataType : "json",
			success : function(no) {
				/*성공시 처리해야될 코드 작성*/
				console.log(no);

				$("#viewPop").modal("hide");
				$("#img" + no).remove();
				
				if(no == 0){
					alert("삭제 권한이 없습니다.");
				}

			},
			error : function(XHR, status, error) {
				console.error(status + " : " + error);
			}
		});
	});

	/* 화면에 이미지 그리기*/
	function render(galleryVo, updown) {
		console.log("render 실행");
		var str = "";
		str += "<li id='img" + galleryVo.no + "'>";
		str += "		<div class='view' data-no='" + galleryVo.no + "'>";
		str += "			<img src='${pageContext.request.contextPath }/upload/" + galleryVo.saveName +"'>";
		str += "		</div>";
		str += "</li>";

		if (updown == "up") {
			$("#galleryList").prepend(str);
		} else if (updown == "down") {
			$("#galleryList").append(str);
		} else {
			console.log("updown 오류");
		}
	}
</script>
</html>