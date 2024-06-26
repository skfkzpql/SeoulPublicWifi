# 위치 기반 공공 와이파이 정보 제공 웹서비스

이 프로젝트는 사용자의 위치를 기반으로 근처의 공공 와이파이 정보를 제공하는 웹서비스입니다.

## 사용된 기술

- JavaSE 1.8
- SQLite 데이터베이스
- Apache Tomcat 8.5
- 작업 환경: Eclipse

## 사용한 라이브러리

- okhttp3
- gson
- lombok
- sqlite-jdbc

## 구현된 기능

1. **API 정보 가져오기**: 공공 Open API를 사용하여 와이파이 정보를 가져옵니다.
2. **와이파이 정보 표시**: 사용자의 위치를 가져오거나 직접 입력하여 근처 와이파이 정보를 표시합니다. 최대 20개의 결과를 보여줍니다.
    - **와이파이 명 클릭**: 각 와이파이에 대한 자세한 정보를 확인할 수 있습니다.
3. **히스토리 관리**: 검색한 결과에 대한 히스토리를 생성하고, 개별적으로 삭제하거나 전체 히스토리를 삭제할 수 있습니다.
    - **히스토리 페이징**: 최대 10개의 항목을 한 페이지에 표시하고, 페이지별로 이동할 수 있는 페이징 기능을 사용할 수 있습니다.
    - **히스토리 재검색**: 검색을 눌러 해당 히스토리를 다시 검색할 수 있습니다.
4. **북마크 그룹 관리**: 북마크 그룹을 생성, 수정, 삭제하고 전체 그룹을 삭제할 수 있습니다.
5. **북마크 관리**: 와이파이 자세히 보기에서 북마크 그룹을 선택하여 북마크를 추가할 수 있습니다.
    - **북마크 보기**: 전체 북마크를 표시하고, 개별적으로 삭제하거나 전체 북마크를 삭제할 수 있습니다.


## 설치 및 실행

1. 이클립스에서 프로젝트를 열고 빌드합니다.
2. Tomcat 서버를 설치하고 프로젝트를 배포합니다.
3. SQLite 데이터베이스를 설정하고 프로젝트에 연결합니다.
4. 서버를 실행하고 브라우저에서 웹서비스에 접속합니다.
