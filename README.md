# 📌 프로젝트 목표
- 전역 이후 개발에 감을 다시 잡기 위해 진행
- Spring Security, JPA 실전 적용
- 실용적인 CRUD 및 보안 흐름 구현
- 이후에는 로그인 연동, 상품별 통계 차트 등을 구현해볼 예정

# 🛒 FlipMarket

Spring Boot 기반의 **상품 사고팔기 서비스**입니다.  
사용자는 상품을 등록하고, 다른 사용자의 상품을 검색해 구매할 수 있습니다.

---

## 🚀 사용 기술 (Tech Stack)

- **Backend**: Java 24, Spring Boot, Spring Security, JPA (Hibernate)
- **Database**: MySQL
- **Build Tool**: Gradle
- **Authentication**: 로그인/회원가입, 비밀번호 암호화

---

## 🧩 주요 기능

- 회원가입 / 로그인 / 로그아웃 / 정보 수정
- 상품 등록 / 수정 / 삭제 (이미지 업로드 구현 완료)
- 상품 목록 조회 / 검색
- 상품별 통계 조회 (추가 예정)

---

## 🛠️ 프로젝트 실행 방법

```bash
# 1. 프로젝트 클론
git clone https://github.com/your-id/your-project-name.git

# 2. application.yml 또는 application.properties 설정
# (DB 정보 및 시크릿 키 등)

# 3. 실행
./gradlew bootRun
