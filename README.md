# 프로젝트 소개
![image](https://user-images.githubusercontent.com/107654769/186507477-6a6108f7-359f-4cda-8d96-c7085106ad79.png)
<h2>에어비엔비 클론코딩</h2>
<li>개발 인원 : 프론트엔드 3명, 백엔드 3명</li>
<li>개발 기간 : 8/19 ~ 8/25</li>
<li>기술 스택: 
<img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=flat-square&logo=Spring Boot&logoColor=white"/>
<img src="https://img.shields.io/badge/Spring Security-6DB33F?style=flat-square&logo=Spring Security&logoColor=white"/>
<img src="https://img.shields.io/badge/Gradel-02303A?style=flat-square&logo=Gradle&logoColor=white"/>
<img src="https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=MySQL&logoColor=white"/>
</li>


<li>배포 환경 : 
<img src="https://img.shields.io/badge/Amazon EC2-FF9900?style=flat-square&logo=Amazon EC2&logoColor=white"/>
<img src="https://img.shields.io/badge/FileZilla-BF0000?style=flat-square&logo=FileZilla&logoColor=white"/>
</li>

<li>협업 도구 : 
<img src="https://img.shields.io/badge/Github-181717?style=flat-square&logo=Github&logoColor=white"/>
</li>

<h2><a href=https://www.notion.so/SA-5-b42691a27f1048768da8880bae9727c2>팀 노션</a></h2>

## 👨‍👧‍👦 Front 구성원

|  Name  |            Github            |        Role        |
|:------:|:----------------------------:|:------------------:|
| 👦 신원혁 | https://github.com/god1hyuk  |      카테고리,숙소       |
| 👦 문경록 |   https://github.com/rokga   |    로그인,회원가입,숙소     |
| 👦 안승현 |  https://github.com/zemiles  |   위시리스트,후기작성,필터    |


# 주요기능 및 소개
### **🗺** ERD 설계


### **🐋** 트러블 슈팅

### ***1.속도 개선<br>***

![](../Desktop/스크린샷 2022-08-25 오전 1.17.19.png)
<br> 주석 처리된 builder를 사용하여 메인페이지에 해당하는 게시글을 호출 하였을때 여러번의 for문을 이용하다보니
<br> Postman을 이용하여 검사를 했을때 17초까지 나오는 문제가 발생 하였다.

<br> **여러번 호출을 하지 않고 개선 하기 위해 Querydsl 사용하여 해당하는 컬럼만 조회하는걸로 리팩토링을 통해 조회 시간을 대폭 감소시킴.

![img_1.png](img_1.png)
<br> 


### 2. Pageable 사용 시 호출 중복 문제<br>
 ![img_2.png](img_2.png)
   <br>
<br> .distinct() 를 사용하여 중복 되는 값들을 해결 하려고 했지만 중복된 값들이 호출 되는 문제가 발생 함.
   <br><br>
![img_3.png](img_3.png)

<br> ** .distinct()를 사용하여 중복값이 해결 안되는것을 확인하여 또 다른 중복제거 방법인 .groupBy코드를 사용 하여 확인을 해보니 중복된 값들이  
<br> 나오지 않고 정상적으로 실행 되는것을 확인 하였습니다.

