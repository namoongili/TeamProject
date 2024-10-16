<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    


    <style>

        .login-wrap{
            width: 960px;
            border: 1px solid rgb(235, 223, 223);
            margin: 0 auto;
            box-sizing: border-box;

        }

        .line1{
            display: flex;
            background-color: rgb(#555555);
            padding: 10px;
            border-bottom: 1px solid rgb(235, 223, 223);
            
        
        }

        .line1 > div{
            width: 33.3%;
        }
        .line1 >div:nth-child(1){
            text-align: center;

            

           
        }
        .line1 >div:nth-child(2){
            margin-top: 15px;

            
        }
        .line1 >div:nth-child(3){
            text-align: right;

            margin-top: 40px;

            
        }

        .line2{
            display: flex;
        }



        .line2 > div {

            width: 50%;
            /*border:1px solid black;*/
             /* height: 500px;*/
        }


        .line2  .imgcol {

             background-color: rgb(145, 128, 161); 
            text-align: center;
        }

        .line2 .imgcol   img {
            width: 80%;
           
        }
        .imgcol2 {
            margin-top: 50px;
        }
        .imgcol3 {
            margin-top: 10px;
            margin-bottom: 60px;
        }

        .footer{
            padding: 10px;
        }


        .login-2{
           /* border:1px solid black;*/
            width: 90%;
            padding: 15px;
            margin: 0 auto;
        }


        .login3{
            display: flex;
           /* border:1px solid black;)*/
            margin: 5px;
        }


        .login3 >div{
            border: 1px solid rgb(235, 223, 223);
            padding-top: 10px;         
            padding-bottom: 10px;
            text-align: center;
            cursor: pointer;
            font-size: 14px;
        } 

        .login3 >div:nth-child(1){
            width: 15%;

            
        }

        .login3 >div:nth-child(2){
            width: 85%;
           
            line-height: 30px;
        }
        .icon {
            width: 30px;
            height: 30px;
        }
        
        .main-img2{
            width: 360px;
            height: 140px;
        }
        h2 {
            text-align: center;
        }


        .footer{
            text-align: center;
            font-size: 12px;
        }
      
    </style>
  
  <link rel="stylesheet" href="/BooKorn/css/style.css">

    <script>

        //회원일 때 
        function show1(){
            document.querySelector(".contents  .member").style.display="block";
            document.querySelector(".contents  .no-member").style.display="none";
        }
        
        function show2(){
            document.querySelector(".contents  .member").style.display="none";
            document.querySelector(".contents  .no-member").style.display="block";
        }


    </script>
 



 
</head>
<body>


    <div class="login-wrap">

        <div class="line1">
            <div>
                <img src="https://image.yes24.com/sysimage/renew/sLayout/logo_n.png" alt="">
            </div>
            <div>
                <h2>로그인</h2>
            </div>
            <div>
                <button>회원가입</button>
            </div>

        </div>


        <!-- -->

        <div class="line2"> 

            <!-- 로그인 칸-->
            <div>
                  

                                
                    <div class="wrap">
                        <!--  탭-->
                        <div class="tab-line"> 
                            <div  onclick="show1()">회원</div> 
                            <div  onclick="show2()">비회원주문</div> 
                        </div>


                        <!--내용-->

                        <div   class="contents">
                           
                            <div class="member">

                                <!---->
                                <form action="user" method="post">
	                                <div>
	                                    <div class="a"> 
	                                        <input type="text" id="username" name="username" placeholder="아이디" required>
	                                    </div>
	                        
	                                    <div class="a">
	                                        <input type="password" id="password" name="password" placeholder="비밀번호" required>
	                                    </div>
	                                    <div class="options">
	                                        <label><input type="checkbox" name="remember"> 로그인 상태 유지</label>
	                                        <label><input type="checkbox" name="remember"> 아이디 저장</label>
	                                    </div>
	                                    
	                                    <div>
	                                        <button type="submit" class="login-btn">로그인</button>
	                                    </div>
	                        
	                                    <div class="register-link">
	                                    <p><a href="#">아이디찾기</a> l <a href="#">비밀번호 찾기</a></p>
	                                    </div> 
	                                </div>
								</form>
                                <!--  -->
                                <div class="login-2">
                                    <div class="login3">
                                        <div> <img class="icon" src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT1OOUv_p7ZJHEwDZ4DiNW7hQ2IjphslzaD-C3pabVFCLdIMDl1-zx7LOrCl9nYn0sVr30&usqp=CAU" alt="naver"> </div>
                                        <div> 네이버 아이디로 로그인</div>
                                    </div>

                                    <div class="login3">
                                        <div> <img class="icon" src="https://www.talktobiz.co.kr/resources/images/ico/ico_kakao_chat.png" alt="kakao"> </div>
                                        <div> 카카오톡 아이디로 로그인</div>
                                    </div>

                                    <div class="login3">
                                        <div> <img class="icon" src="https://img.icons8.com/?size=512&id=118497&format=png" alt="facebook"> </div>
                                        <div> 페이스북 아이디로 로그인</div>
                                    </div>
                                    <div class="login3">
                                        <div> <img class="icon" src="https://cdn-icons-png.flaticon.com/512/545/545194.png" alt="phone"> </div>
                                        <div> 휴대폰 간편 로그인</div>
                                    </div>

                                    
                                </div>


                            </div>
                        

                            <div class="no-member">

                                <div class="a"> 
                                    <input type="text" id="username" name="username" placeholder="주문번호" required>
                                </div>
                    
                                <div class="a">
                                    <input type="password" id="password" name="password" placeholder="주문 비밀번호" required>
                                </div>
                                
                                <div>
                                    <button type="submit" class="login-btn">로그인</button>
                                </div>
                    
                                <div class="register-link">
                                <p><a href="#">주문번호 찾기</a> l <a href="#">주문 비밀번호 찾기</a></p>
                            </div> 
                            </div>

                        </div>

                    </div>


 

               
            </div>

            <!-- 이미지칸-->
            <div  class="imgcol">
                <img class="imgcol2" src="https://image.yes24.com/images/00_Event/2024/0617Gift/bn_1080x1080.jpg" >
                <img class="imgcol3" src= "http://adgirl.yes24.com/RealMedia/ads/Creatives/OasDefault/Login_Bottom/login_50929.jpg">
            </div>

            
        </div>


 
        <!-- -->
        <div>
            <img src="https://image.yes24.com/images/01_Banner/login/login_PC_240806.jpg">
        </div>



        <!---->
        <div class="footer">	
         
				Copyright © <strong>YES24 Corp.</strong> All Rights Reserved.           
		 
        </div>
    </div>
    
</body>
</html>