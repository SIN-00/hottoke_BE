# _🏠HOTKETOK(핫케톡)🏠_

## 📑 서비스 소개
다세대 주택을 위한 하우징 토탈 케어 서비스

#### 🔥 Slogan
_다세대 주택의 아파트화를 꿈꾼다!_

#### 🔥 Headcopy
다세대 주택의 불편함이 없는 날까지 핫케톡이 책임지겠습니다.

#### 🔥 Sub copy
집에 수리할 게 생기면 [뚝딱]으로 간편하게 수리 신청!
다른 집과 소통하고 싶으면 [똑똑]으로 연락!
우리집 관리비 어떻게 사용되고 있는지 관리!
<br />
<br />

- - -


## 👩‍👩‍👧‍👦 팀명 : 오픈소스 3조 (신지환, 신민경, 황선우)

### R&R
|분야|이름|포지션|
|:------:|:---:|:---------:|
|백엔드|신지환|💻서비스 기획,서버 및 개발 담당|
|백엔드|신민경|💻서비스 기획, 명세 및 개발 담당|
|프론트엔드|황선우|💻서비스 기획, 디자인 및 개발 담당|
<br />
<br />

- - -

## 📑 서비스 목적 및 필요성

<aside>
📌 다세대 주택 거주자들이 가지는 불편함을 간편하고 빠르게 해결하고자 한다.

</aside>

### 목적 :  다세대 주택의 아파트화

- 다세대 주택은 아파트에 비해 받는 정보의 양이 적습니다.
- 내가 내고 있는 관리비가 어디에 사용되는 지도 알 수 없어요.
- 수리를 받을 때도 어떤 곳에서 받아야 하는 지 몰라요.
- 옆 집이 시끄러우면 얼굴을 붉히며 직접 대면할 수 밖에 없어요.

### 서비스 필요성 : 다세대 주택을 케어하고자 하는 이유

- 내용 추가 필요

<!-- ### 페르소나 설정 :  -->
![Slide 16_9 - 15](https://github.com/user-attachments/assets/c21adee9-8680-4621-8472-26636c854be9)


<!-- ### 서비스 핵심 가치 :  -->
- 사진 추가

##### ✅ Reminder :  </br>
##### ✅ Self-Understanding :  </br>
##### ✅ Interaction :  </br>


### 💗 핫케톡의 목표

|핵심 가치|비전|미션|
|:--:|:--:|:--:|
|노래를 통해 사용자의 감정을 자극한다|사용자가 자신의 감정을 이해할 수 있게 한다.|사용자가 자신을 이해하고 이를 삶에 적용할 수 있게 한다.|
<br />
<br />

- - -


<br />
<br />


### 2. 데스크 리서치(1) : 음악 스트리밍 서비스 시장 조사

### 2-1. 단순 스트리밍 서비스와의 차이점

- 단순히 노래를 검색하고 기록하는 것이 아닌, **‘회상’을 할 수 있는 장치**이다.
- 암묵적 기억이 불러져 오는 프로세스를 반영한 서비스다.
- 현재 음원 스트리밍 서비스에서 ‘잊고 있었던 곡’을 접근하게 해주는 기능은 없고, 이미 들은 곡을 기반으로 아티스트, 노래 성향, 장르 등을 반영하여 추천해주는 성향이 강하다.
- 장소/위치를 기반으로 음원을 추천해주는 서비스는 없다.
- 현재 음원 스트리밍 서비스는 하나의 일회적인 서비스로 소비되고 있음다. 즉, 유저는 ‘음원 재생’을 위한 기능을 위해 서비스를 이용하는 것이지 자신의 정보를 기입하고 공유하고 소속감을 느끼게 하는 장치가 부족함.
- 음악을 통해 자신의 감정을 기록하고, 같은 느낌을 공감하는 사람들간의 연결 고리를 만들어 준다.
<br />
<br />

### 3. 데스크 리서치(2) : 유튜브 플레이리스트 채널 크롤링

<!-- ![image](https://user-images.githubusercontent.com/62995958/195970735-a6016fc4-620b-4333-8231-13f6fbd4e780.png) -->
<img src="https://user-images.githubusercontent.com/62995958/195970735-a6016fc4-620b-4333-8231-13f6fbd4e780.png" width=70% height=500>

> 상황 : 사용자들은 노래를 들었을 때 ,직접 경험한 추억 뿐만 아니라 자신이 겪지 않은 추억도 자신이 경험한 것 처럼 느낀다.

이를 검증하기 위해, 유튜브 플레이리스트로 유명한 '민플리, 때껄룩, 네고막을책임져도될까' 3가지 채널의 제목과 조회수를 크롤링했다. </br>
크롤링을 통해, 제목에서 볼 수 있는 trigger 요소들(기분, 시간, 계절, 장소, 지역 등)을 분석한 후, 해당 요소들을 기능으로 구현할 수 있도록 기획했다. </br>
이미 핵심 기능으로 구현된 기분, 시간 및 계절, 장소 및 지역, 추천을 제외하고 '콘텐츠'를 통한 회상을 추가 기능으로 구현하고자 했다. </br>


|참고자료|설명|
|:---------------------------------:|:-----:|
|![Group 9](https://user-images.githubusercontent.com/62995958/195971031-5adb5685-676c-4d94-b568-b9c2c7281c4d.png)|유튜브 플레이리스트 댓글로 자신이 경험한 일이 아닌, 상상을 적은 댓글과 7.8천의 좋아요 수|


💡 OST가 아닌 전혀 다른 장르의 노래를 들어도 영화나, 드라마에 대한 분위기를 상상하는 사람이 많다.

💡 또한, 노래에 대해 자신이 격지 않은 상황에 대한 이야기를 ‘망상’처럼 풀어내는 경향을 보인다.

💡 노래에 대한 특수적인 분위기를 통해 사용자에게 새로운 감정을 불러일으킬 수 있다. 따라서 특정 노래에 대한 분위기나 가사를 통해 느끼는 감정으로 사용자가 자신의 감정을 이해할 수 있도록 유도한다.

💡 노래에 대한 특수적인 분위기와 타인의 기록을 이용해서 사용자는 자신의 감정을 이해할 수 있도록 한다. 

→ “음악 기록 공유”에 대한 필요성

- - -

## 📑 서비스 타겟층 분석

> _음악을 통해 감정과 기억을 환기 시키고자 하는 회고 절정 시기의 1030세대_


**초기 타겟층 :**

주 3회 이상, 음악을 듣기 위해 앱 서비스를 사용하고, 노래를 통해 기억을 회상하고 기록하는 앱 서비스를 사용하는 데에 거부감이 없는 MZ세대

**확장 타겟층 :**

주 3회 이상, 음악을 듣기 위해 앱 서비스를 이용하는 사용자
<br />
<br />


- - -
## 📑 핵심 기능 정의


> 📌 한 사람의 플레이리스트는 그 사람의 인생이 될 수도 있다.
> 우연에 의존 되었던 노래의 회상을 [브링]서비스를 통해 필연으로 만들어 사용자의 회상을 이끌어낸다.


|기능 이름|액션|내용|
| --- | --- | --- |
| 브링레코드 | 글쓰기 기능 | 사용자가 음악과 함께 지역, 사진, 시간대, 그리고 메모를 적어 업로드할 수 있는 기능 |
| 브링메모리 | 푸시 알림 기능 | 추천, 히스토리가 각각 장소 및 시간을 기반으로 추천되어, 이 장소에서 들었던 음악, 시간 등을 알림으로 받을 수 있는 기능 |
| 브링어스 | 지역, 시간, 감정 기반 탐색 기능 | 브링어스 안에는 지도와 시간 탭으로 구성되어, 지도 탭의 경우는 지역별로 많이 재생된 음악을 탐색할 수 있고, 시간 탭은 시간대, 계절별로 많이 재생된 음악을 탐색할 수 있는 기능, 감정 탭은 자신의 감정 태그로 음악을 탐색할 수 있는 기능 |

<br />
<br />
## 📑 추후 확장 기능

|기능 이름|액션|내용|
| --- | --- | --- |
| 브링콘텐츠 | OTT 콘텐츠 탐색 기능 | 드라마, 영화 등을 보고 생각나는 음악을 탐색할 수도 있고, 반대로 음악에 어울리는 콘텐츠들을 추천받을 수도 있는 기능 |
| 브링스토리 | 콘텐츠 추천 기능 | 현재 가장 많이 기록된 노래, 지금 핫한 사연 등 사람들이 올린 ‘레코드’를 탐색하고 공감하며 공유할 수 있는 기능 |
| 브링프렌즈 | 팔로워, 팔로잉 기능 | 브링어스, 브링콘텐츠, 브링스토리를 통해 접한 유저를 팔로잉/팔로워하고, 추천 코드를 통해 실제 친구도 초대할 수 있는 기능 |

- - -
## 📑 Business Model 설계

|구성 요소|내용|
|:--:|:---------------:|

- - -


- - -
## 📑 기술 스택
#### 💻 BackEnd
![Java](https://img.shields.io/badge/Java-3776AB?style=flat-square&logo=mysql&logoColor=white)
![Springboot](https://img.shields.io/badge/springboot-6DB33F?style=flat-square&logo=springboot&logoColor=white)
![IntelliJ](https://img.shields.io/badge/IntelliJ%20IDEA-000000?style=flat-square&logo=IntelliJ%20IDEA&logoColor=white)
![JPA](https://img.shields.io/badge/JPA-%23ED8B00.svg?style=flat-square&logo=jpa&logoColor=white)
![mysql](https://img.shields.io/badge/Mysql-4479A1?style=flat-square&logo=mysql&logoColor=white)
![RDS](https://img.shields.io/badge/Amazon%20RDS-527FFF?style=flat-square&logo=Amazon%20RDS&logoColor=white)
![gradle](https://img.shields.io/badge/gradle-02303A?style=flat-square&logo=gradle&logoColor=white)


#### 💻 FrontEnd
![Swift](https://img.shields.io/badge/Swift-F05138?style=flat-square&logo=Swift&logoColor=white)
![Xcode](https://img.shields.io/badge/Xcode-147EFB.svg?style=flat-square&logo=Xcode&logoColor=white)
#### 📡 배포
<img src="https://img.shields.io/badge/AWS EC2-232F3E?style=flat-square&logo=amazon%20aws&logoColor=white"/> <img src="https://img.shields.io/badge/Jenkins-D24939?style=flat-square&logo=Jenkins&logoColor=white"/></a>
#### 👨‍👩‍👧‍👦 협업툴
![GitHub](https://img.shields.io/badge/github-%23121011.svg?style=flat-square&logo=github&logoColor=white)
<img src="https://img.shields.io/badge/Notion-000000?style=flat-square&logo=Notion&logoColor=white"/>
<img src="https://img.shields.io/badge/Slack-4A154B?style=for-the-badge&logo=slack&logoColor=white">
</a>
#### 🎨 Design
![Figma](https://img.shields.io/badge/Figma-F24E1E.svg?style=flat-square&logo=Figma&logoColor=white)

<br />
<br />

### 📌 기술 스택 선정 이유
### Back
- Github Actions를 이용해 Springboot CI/CD를 자동화하였습니다.
- Github Webhook을 이용해 push시 서버 배포까지 자동화하였습니다.
- AWS EC2, RDS를이용해 서버, DB, 객체를 클라우드로 24시간 관리/배포가 가능하도록 하였습니다.
- Github를 통해 버전관리와 분산관리를 진행했습니다.
### Front
- API 통신을 위해서는 URLSession, URLRequest를 사용하고 추가적으로 Alamofire 라이브러리를 통해 구현할 예정입니다.
- 빠르고 효율적인 개발을 위해 MVC 패턴을 적용할 예정입니다.
### Design
- 자세한 화면정의가 가능하고 협업이 용이한 figma를 사용하였습니다.
- Blender를 활용하여 3d 디자인을 제작할 예정입니다.

<br />
<br />

## 📑 커밋 컨벤션
### 📌 Branch
- "main"에는 배포 했을 시에만(서버 연동), 모든 PR & Merge는 "develop" 에서 이루어진다.
- 기능의 이름우로 브랜치를 따 개인 작업 진행 -> "develop" 브랜치로 PR

### 📌 Commit message
- "[기능] 제목 - 부연설명" 형식으로 이루어진다.
- “[기능] 제목” → 필수
- "- 부연설명" → 선택
- 15자 이상 X

### 📌 [기능]
- init : 초기 세팅
- build : 외부 의존성 설치나 빌드 시스템에 영향을 주는 변경사항
- ci: CI 환경설정과 스크립트에 변화가 있는 경우
- docs: 문서, 주석 관련
- feat: 새로운 기능 추가
- fix: 버그 픽스
- refactor: 코드 리팩토링 관련
- chore: 버그 픽스 또는 기능 추가에 해당되지 않는 '자잘한' 변경사항 (rename, remove 등)
- test: 테스트 관련 수정

<br />
<br />

- - -

## 📑 소프트웨어 아키텍처

![Frame 4](https://github.com/user-attachments/assets/a641a811-40ab-456d-915a-a3abaa797843)


<br />
<br />

- - -

## 📑 주요 기능 명세서

