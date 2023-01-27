<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script>


//비밀번호 확인창의 값이 비밀번호 입력창의 값과 같은지 확인해주는 function
      function pwCheck() {
	
         var pw = document.getElementById('pw').value;

         if (document.getElementById('pw').value != '' && document.getElementById('pwCh').value != '') {
            if (document.getElementById('pw').value == document.getElementById('pwCh').value) {
               document.getElementById('check').innerHTML = '비밀번호가 일치합니다.'
               document.getElementById('check').style.color = 'blue';
               
               
            }
            else {
            	
               document.getElementById('check').innerHTML = '비밀번호가 일치하지 않습니다.';
               document.getElementById('check').style.color = 'red';
               
            }
         }
      }

//id와, 비밀번호 형식 체크 (정규식 사용)
      $(function(){
    	 $('#id').on('change',()=>{
    		 let idval = $('#id').val();
    		 let idvalcheck = /^[a-z]+[0-9]/g;//정규식 :  영문자+숫자
    		 if(!idvalcheck.test(idval)||idval.length<6){
    			 $('#idcheck').text('아이디는 영소문자+숫자 6자리이상으로 해주세요.');
    			 $('#idcheck').css({"color":"red"});
    			 $('#id').focus();//id창으로 focus이동
    			 $('#id').val('');//id창의 값 공백으로 초기화
    		 }
    	 });
    	 $('#pw').on('change',()=>{//id와 비슷한 원리
    		let pwval = $('#pw').val();
    		let pwvalcheck = /^[a-z]+[0-9]/g;
    		if(!pwvalcheck.test(pwval)||!((pwval.length>=6) && (pwval.length<=12))){
    			$('#pwcheck').text('비밀번호는 영소문자+숫자 6자리이상 12자리이하로 해주세요.');
    			$('#pwcheck').css({"color":"red"});
    			$('#pw').focus();
    			$('#pw').val('');
    		}else{
    			$('#pwcheck').text('사용가능한 비밀번호 입니다.');
    			$('#pwcheck').css({"color" :"blue"});
    			
    		}
    	 });
    	 
    	 });
  
//비동기 방식으로 id중복 체크
//id 입력 input창에 oninput설정 -> 입력할 때마다 비동기로 아이디 중복 체크하도록
      function checkId() {
  		var id = $('#id').val();
  		$.ajax({
  		url:'/member/idCheck',
  			type:'post', 
  			data:{chid:id}, //id의 값을 chid라는 param값으로 던져줌. 
  			success:function(cnt){
  				if(cnt==0 && id.length!=0){
  					$('#idcheck').text('사용가능한 아이디 입니다.');
  					$('#idcheck').css({"color" : "blue"});
  				}else if(id.length==0){
  					$('#idcheck').text("");
  				}
  				else{
  					$('#idcheck').text('중복된 아이디 입니다.');
  					$('#idcheck').css({"color" : "red"});
  				}
  			},
  			err:function(){
  				alert("에러입니다.");
  			}
  		});
  	}	
   </script>
<style>
* {
	text-align: center;
	margin: auto;
}

ul {
	list-style-type: none;
}

li {
	list-style-type: none;
}

/* .as li {
	display: inline;
} */
input {
	width: 500px;
	height: 40px;
}

input.c {
	height: 40px;
	width: 500px;
}

select {
	width: 100px;
	height: 30px;
	font-size: 15px;
}

input.h {
	height: 30px;
	width: 105px;
}

button.q {
	height: 60px;
	width: 200px;
	background-color: white;
	border: 1px solid black;
	border-radius: 5px;
}

h1 {
	font-weight: bold;
}
</style>
</head>
<body>
  <header>
    <%@ include file="header.jsp"%>
  </header>
  <main>
    <br> <br>
    <h1>회원 가입</h1>
    <br> <br>
    <form action="/member/add" method="post" id="register">
      <ul class="cols">
        <li class="col1">아이디</li>
        <li class="col2"><input type="text" id="id" onchange="idCheck()" name="userid" oninput="checkId()"><br> <span id="idcheck"></span></li>
      </ul>
      <br>

      <ul class="cols">
        <li class="col1">비밀번호</li>
        <li class="col2"><input type="password" id="pw" onchange="pwCheck()" name="userpw"><br> <span id="pwcheck"></span></li>
      </ul>
      <br>
      <ul class="cols">
        <li class="col1">비밀번호 확인</li>
        <li class="col2"><input type="password" id="pwCh" onchange="pwCheck()"><br> <span id="check"></span></li>
      </ul>
      <br>
      <ul class="cols">
        <li class="col1">이름</li>
        <li class="col2"><input type="text" name="username"></li>
      </ul>
      <br>
      <ul class="as">
        <li class="col1">생년월일</li>
        <li class="b"><input class="c" type="date" placeholder="년도(4자)" name="birth"></li>

      </ul>
      <br>

      <ul class="cols">
        <li class="col1">휴대전화</li>

        <li class="col2"><select name="tel1" id="">
            <option value="010">010</option>
            <option value="011">011</option>
            <option value="012">012</option>
            <option value="013">013</option>
            <option value="014">014</option>
            <option value="015">015</option>
            <option value="016">016</option>
            <option value="017">017</option>
            <option value="018">018</option>
            <option value="019">019</option>
        </select> - <input class="h" name="tel2" type="text" maxlength="4"> - <input class="h" name="tel3" type="text" maxlength="4"></li>


      </ul>
      <br> <br>

      <button class="q" type="submit">가입하기</button>
    </form>
  </main>
  <br>
  <br>
  <footer>
    <%@ include file="footer.jsp"%>
  </footer>
</body>
</html>