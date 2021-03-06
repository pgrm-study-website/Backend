# Backend

## :smile: 프로젝트 소개

- [Plming 사이트](https://plming.netlify.app)
- [Plming Github 프로필](https://github.com/pgrm-study-website) 참고

</br>

## 🛠 기술 스택

- Spring Boot (API Server)
- Spring Security (Security)
- MySQL (RDB)
- JPA & QueryDSL (ORM)
- JUnit (Test)
- AWS (Infra)
- Travis CI (CI)

</br>

## 🔍 핵심 기능

### 1. 게시글 관련 핵심 기능 (<img src="https://avatars.githubusercontent.com/u/66582313?v=4" align="center" width="25" height="25"> [slchoi](https://github.com/SulimChoi))

- 게시글 CRUD 

  - Spring Data JPA를 활용해 게시글 CRUD API 구현
  - 사용 이유: 애플리케이션에서 SQL을 직접 다룰 경우 SQL과 엔티티 사이에 강한 의존 관계가 생기게 되어 필드를 하나 추가할 경우에도 CRUD 코드와 SQL 대부분을 변경해야 하는 문제가 발생한다. 이 문제를 해결하기 위해 Spring Data JPA를 사용해 CRUD 코드를 작성했다.

- 게시글 신청 CRUD

  - Spring Data JPA를 활용해 게시글 신청 CRUD API 구현


- 게시글 댓글 CRUD
  - Spring Data JPA를 활용해 게시글 댓글 CRUD API 구현


- 게시글 검색

  - QueryDSL를 활용해 게시글 검색 API 구현
  - 사용 이유: 코드를 쿼리로 작성하기 때문에 컴파일 시점에 문법 오류를 쉽게 확인할 수 있고, 자동 완성 등 IDE의 도움을 받을 수 있기 때문이다. 또한 동적인 쿼리 작성이 편리하고, 쿼리 작성 시 제약 조건 등을 메서드 추출을 통해 재사용할 수 있기 때문에 QueryDSL을 사용해 게시글 검색 API를 구현했다.


### 2. 사용자 관련 핵심 기능

- 이메일 로그인 + 소셜 로그인 (<img src="https://avatars.githubusercontent.com/u/80041449?v=4" align="center" width="25" height="25"> [JMsuper](https://github.com/JMsuper))

  - cors문제를 해결하기 위해 Spring Security사용
  - 이메일 로그인과 소셜 로그인의 API를 통합하여 제공하기 위해, Spring Security Oauth를 사용하지 않고 직접 Oauth 서버와 통신하여 소셜 로그인을 구현하였다. 
  - https통신 시 브라우저에 쿠키가 저장되지 않는 문제를 해결하기 위해, 쿠키에 'SameSite=None'과 'Secure' 속성을 추가하였다.
  - JWT 토큰을 활용하여 '인가'를 구현
  - 선택 이유 : AWS EC2 프리티어를 활용하여 서버를 구축하여, 세션을 활용하여 인증 및 인가를 구현할 경우 서버에 부담이 될 것이다. 서버에 부담이 덜 가도록 하기 위해, JWT토큰 방식을 활용하였다.

- 쪽지 기능 (<img src="https://avatars.githubusercontent.com/u/80041449?v=4" align="center" width="25" height="25"> [JMsuper](https://github.com/JMsuper))
  - 복잡한 쿼리를 생성하기 위해 QueryDSL을 사용
  
- 알림 기능 (<img src="https://avatars.githubusercontent.com/u/66582313?v=4" align="center" width="25" height="25"> [slchoi](https://github.com/SulimChoi))

  - DB에 알림을 저장하는 기능은 Spring Data JPA를 활용해 구현
  - 웹 통신 방법은 SSE 통신 방법을 활용
  - 선택 이유: SSE는 웹소켓과 달리 별도의 프로토콜을 사용하지 않고 HTTP 프로토콜만 사용하기 때문에 훨씬 가볍기 때문이다. 알림 기능은 양방향 통신이 필요하지 않으므로 웹 소켓 통신보다 가벼운 SSE 통신 방법을 선택하는 것이 더 좋을 것이라고 생각했기 때문에 SSE 통신 방법을 활용했다. 

### 3. API 문서
- Postman을 사용해 API 문서 작성
- [Plming API 문서 링크](https://documenter.getpostman.com/view/20114507/UVyxRu1s)

</br>

## 🎞 데모 영상

### 1. 게시글 기능 관련 데모 영상

|       메인 화면        |     게시글 목록      |
| :--------------------: | :------------------: |
| <img src="https://github.com/pgrm-study-website/.github/blob/main/images/메인%20화면.gif"/> | <img src="https://github.com/pgrm-study-website/.github/blob/main/images/게시글%20목록1.gif"/>|
|    **게시글 작성**     |   **게시글 수정**    |
|<img src="https://github.com/pgrm-study-website/.github/blob/main/images/게시글%20작성.gif"/>|<img src="https://github.com/pgrm-study-website/.github/blob/main/images/게시글%20수정.gif"/>|
| **게시글 키워드 검색** | **게시글 조건 검색** |
|<img src="https://github.com/pgrm-study-website/.github/blob/main/images/키워드%20검색.gif"/>|<img src="https://github.com/pgrm-study-website/.github/blob/main/images/조건%20검색.gif"/>|
|    **게시글 신청**     | **게시글 신청 취소** |
|<img src="https://github.com/pgrm-study-website/.github/blob/main/images/참여%20신청.gif"/>|<img src="https://github.com/pgrm-study-website/.github/blob/main/images/신청%20취소.gif"/>|
|  **게시글 신청 승인**  | **게시글 신청 거절** |
|<img src="https://github.com/pgrm-study-website/.github/blob/main/images/참여%20승인.gif"/>|<img src="https://github.com/pgrm-study-website/.github/blob/main/images/참여%20거절.gif"/>|
|     **댓글 달기**      |    **댓글 삭제**     |
|<img src="https://github.com/pgrm-study-website/.github/blob/main/images/댓글%20달기.gif"/>|<img src="https://github.com/pgrm-study-website/.github/blob/main/images/댓글%20삭제.gif"/>|
|    **대댓글 달기**     |   **대댓글 삭제**    |
|<img src="https://github.com/pgrm-study-website/.github/blob/main/images/대댓글%20달기.gif"/>|<img src="https://github.com/pgrm-study-website/.github/blob/main/images/대댓글%20삭제.gif"/>|


### 2. 사용자 기능 관련 데모 영상 

|     회원 가입     |   이메일 로그인   |
| :---------------: | :---------------: |
|                   |<img src="https://github.com/pgrm-study-website/.github/blob/main/images/로그인.gif"/>|
|  **구글 로그인**  | **카카오 로그인** |
|<img src="https://github.com/pgrm-study-website/.github/blob/main/images/구글%20로그인.gif"/>|<img src="https://github.com/pgrm-study-website/.github/blob/main/images/카카오%20로그인.gif"/>|
| **깃허브 로그인** |  **마이페이지**   |
|<img src="https://github.com/pgrm-study-website/.github/blob/main/images/깃허브%20로그인.gif"/>|<img src="https://github.com/pgrm-study-website/.github/blob/main/images/마이페이지.gif"/>|
|   **쪽지 확인**   |  **쪽지 보내기**  |
|<img src="https://github.com/pgrm-study-website/.github/blob/main/images/메시지%20확인.gif"/>|<img src="https://github.com/pgrm-study-website/.github/blob/main/images/쪽지%20보내기.gif"/>|
|   **알림 확인**   |  **회원 탈퇴**   |
|<img src="https://github.com/pgrm-study-website/.github/blob/main/images/카카오%20로그인.gif"/>|<img src="https://github.com/pgrm-study-website/.github/blob/main/images/회원탈퇴.gif"/>|

</br>

## 🏭 프로젝트 구조

![배포구조](https://github.com/pgrm-study-website/.github/blob/main/images/배포구조.PNG)

</br>

## 📝 ERD 정의

![ERD](https://github.com/pgrm-study-website/.github/blob/main/images/erd.png)



## 🏗 패키지 구조

```text
|-- auth           			// 인증
|   |-- Oauth
|   |   |-- dto
|   |   |-- entity
|   |   `-- service
|   |-- config          // 인증 관련 설정
|   |-- controller
|   `-- service
|-- board                   // 게시글 관련 기능
|   |-- board                   // 게시글 CRUD 기능
|   |   |-- controller
|   |   |-- dto
|   |   |-- entity
|   |   |-- repository
|   |   `-- service
|   |-- boardApply          		// 게시글 신청 기능                              
|   |   |-- controller
|   |   |-- dto
|   |   |-- entity
|   |   |-- repository
|   |   `-- service
|   |-- boardComment            // 게시글 댓글 기능 
|   |   |-- controller
|   |   |-- dto
|   |   |-- entity
|   |   |-- repository
|   |   `-- service
|   |-- boardSearch             // 게시글 검색 기능
|   |   |-- controller
|   |   |-- dto
|   |   |-- repository
|   |   `-- service
|   `-- boardTag                // 게시글 태그 기능
|       |-- entity
|       |-- repository
|       `-- service
|-- config                  // 공통적인 설정 (QueryDSL, Security, DB, Storage)
|-- event                   // Event 발생 코드
|-- exception               // 공통 예외 처리
|-- message                 // 쪽지 기능
|   |-- controller
|   |-- dto
|   |-- entity
|   |-- repository
|   `-- service
|-- notification            // 알림 기능
|   |-- common
|   |-- controller
|   |-- dto
|   |-- entity
|   |-- eventListener           // EventListener 코드
|   |-- exception               // 알림 관련 예외 처리
|   |-- repository
|   `-- service
|-- storage                 // 파일 저장 기능
|   |-- controller
|   `-- service
|-- tag                     // 기본 태그
|   `-- entity
`-- user                   // 사용자 관련 기능
    |-- configuration
    |-- controller
    |-- dto
    |-- entity
    `-- service
```

