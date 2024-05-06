# ✈️ HAPPYTRIP
사용자의 니즈에 맞는 항공권을 조회하고 예매할 수 있으며, </br>다른 사용자들과 소통을 위한 게시판과 항공사의 최신 정보를 알려주는 공지사항을 제공하는 웹 사이트입니다.


## 📆 프로젝트 기간
2024.04.01 ~ 2024.05.06

## 👥 역할 분담
백엔드 2명

| 안진원              | 이수정                    |
|------------------|------------------------|
|결제, 공지, 게시판, 마이페이지| 로그인/회원가입, 항공권 조회,  관리자 |

## ⚙️ 개발 환경
- 통합개발 환경(IDE): Intellij
- JDK 버전: 17
- 스프링 부트: 3.2.5
- 데이터베이스: MySQL
- 빌드 툴: Gradle
- 관리 툴: Github

## 🛠️ 프로젝트 기술 스택
- Frontend: HTML, CSS, JavaScript, JQuery, Thymleaf
- Backend: Spring Boot, Spring Security, Spring Data JPA
- Database: MySQL
- OpenApi: 항공API, 카카오페이 결제API, Bootstrap

## 📁 ERD
![ERD](https://github.com/sujeong17312/HAPPYTRIP/assets/169051636/defa664d-8350-4524-bad1-57bbb8298ebb)

---

## 💡 프로젝트 구현 기능

<details>
<summary>로그인, 회원가입 페이지</summary>

|로그인|
|---|
|<img width="1120" alt="로그인" src="https://github.com/sujeong17312/HAPPYTRIP/assets/169051636/a2d7bb4a-0702-4c5d-9e1f-165a3fd37f76">|

|회원가입|
|---|
|![회원가입](https://github.com/sujeong17312/HAPPYTRIP/assets/169051636/11bee48a-2cd0-4e86-9d6a-8334ab5615f7)|

</details>

<details>
<summary>마이페이지</summary>

|회원정보 수정|
|---|
|![회원정보 수정](https://github.com/sujeong17312/HAPPYTRIP/assets/169051636/07d61358-d0f7-4dfe-aa31-10fbf67d49e4)|

|회원탈퇴|
|---|
|<img width="1120" alt="회원탈퇴" src="https://github.com/sujeong17312/HAPPYTRIP/assets/169051636/963657fc-35b0-4f6f-a270-d556a922b6a8">|

|예약 내역|
|---|
|<img width="1120" alt="예약내역" src="https://github.com/sujeong17312/HAPPYTRIP/assets/169051636/e11efbbd-2746-4fe1-8f62-55f08700cf67">|

</details>

<details>
<summary>예매</summary>

|항공권 조회|
|---|
|<img width="1120" alt="항공권 조회" src="https://github.com/sujeong17312/HAPPYTRIP/assets/169051636/196a3da0-dea6-4039-9658-d13ce644ef66">|

|항공권 선택|
|---|
|<img width="1120" alt="항공권 선택" src="https://github.com/sujeong17312/HAPPYTRIP/assets/169051636/5f63965e-bc1e-46e3-8463-1535e53b809e">|


|항공권 결제|
|---|
|<img width="1120" alt="항공권 결제" src="https://github.com/sujeong17312/HAPPYTRIP/assets/169051636/0fbbd244-6afb-4960-a4bf-9815f19f1e8c">|

</details>

<details>
<summary>게시판</summary>

|글 CRUD, 페이징, 검색|
|---|
|<img width="1120" alt="사용자 게시판" src="https://github.com/sujeong17312/HAPPYTRIP/assets/169051636/7637b0f0-ff37-43fc-b568-ff528914b0ef">|

|댓글 CRUD|
|---|
|<img width="1120" alt="댓글" src="https://github.com/sujeong17312/HAPPYTRIP/assets/169051636/40815f92-2fb7-480e-8498-33e408b7433d">|

</details>

<details>
<summary>공지</summary>

|공지 조회, 페이징, 검색|
|---|
|<img width="1120" alt="공지" src="https://github.com/sujeong17312/HAPPYTRIP/assets/169051636/8f8f3c59-0724-48fd-b856-a183b0a36a7f">|

</details>

<details>
<summary>관리자</summary>

|회원 관리|
|-----|
|<img width="1120" alt="회원 관리" src="https://github.com/sujeong17312/HAPPYTRIP/assets/169051636/ee47ba0e-4e91-4901-b594-ca7adc499c04">|

|예약 관리|
|---|
|<img width="1120" alt="예약 관리" src="https://github.com/sujeong17312/HAPPYTRIP/assets/169051636/3a78c53a-b9af-47ff-9280-b12dc4268bd0">|

|게시판 관리|
|---|
|<img width="1120" alt="게시판 관리" src="https://github.com/sujeong17312/HAPPYTRIP/assets/169051636/63ef9c91-f006-44d5-97e9-007db87ef829">|

|공지 관리|
|---|
|<img width="1120" alt="공지 관리" src="https://github.com/sujeong17312/HAPPYTRIP/assets/169051636/73f65712-3913-44fd-936c-58d2d7d6261e">|

</details>