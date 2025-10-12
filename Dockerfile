# 1. 베이스 이미지 선택 (자바 21 버전이 설치된 환경에서 시작)
FROM openjdk:21-jdk-slim

# 2. 작업 디렉토리 설정 (컨테이너 내부의 /app 폴더)
WORKDIR /app

# 3. 빌드된 Jar 파일 복사 (컴퓨터의 target/*.jar 파일을 컨테이너의 /app/app.jar로 복사)
COPY target/*.jar app.jar

# 4. 노출할 포트 지정 (이 컨테이너는 8080 포트를 사용한다고 알림)
EXPOSE 8080

# 5. 컨테이너가 시작될 때 실행할 명령어 (애플리케이션 실행)
CMD ["java", "-jar", "app.jar"]