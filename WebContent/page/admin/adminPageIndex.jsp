<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="../admin/adminheader.jsp"></jsp:include>
<section class="setop">
	<div class="mypage">
	    <p id="pabouttitle">관리자 페이지</p>
		    <button class="outline-button" onclick="location.href='<%=request.getContextPath()%>/page/admin/admboardread';" >공지사항</button>
		    <button class="outline-button" onclick="location.href='<%=request.getContextPath()%>/page/indexpage/user';" >회원관리</button>
    		<button class="outline-button" onclick="location.href='<%=request.getContextPath()%>/page/indexpage/point';">등급관리</button>
    		<br>
    		<button class="outline-button">게시글관리</button>
    		<button class="outline-button">인기분석</button>
    </div>
</section>
<jsp:include page="../indexPage/footer.jsp"></jsp:include>