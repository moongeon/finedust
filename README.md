👀 fineDust(미세먼지)
=================

FineDust 앱은 현재위치기준 가장 가까운 관측소에서 얻은 미세먼지, 초미세먼지 정보를 알려주는 앱
패스트 캠퍼스(https://github.com/Fastcampus-Android-Lecture-Project-2021/aop-part4-chapter06) 강의를 듣고 약간의 수정 변경해서 다시 만들어 봤습니다.
피드백은 언제나 환영합니다.✨

변경 내용
------------
mvc  --> mvvm
Koin --> Dagger hilt
ViewBinding --> DataBinding

🙌소개
------------
사용된 구조

* MVVM 디자인 패턴
  * Model : 데이터와 데이터에 관련된 행위를 모두 합쳐 Model 이라 부름
  * View : 사용자에게 화면으로 보여지는 모든 구조, 레이아웃을 의미
  * ViewModel : View 의 추상화된 형태, View 에 보여져야하는 데이터와 명령들을 가지고 있음

사용된 라이브러리
* Android Jetpack : 훌륭한 Android 앱을 만들기 위한 구성요소, 도구 라이브러리 입니다.
  * Foundation : 핵심 시스템 기능, Kotlin 확장 및 multdex 및 자동 테스트 지원을 위한 구성요소입니다.
  * Architecture : 강력하고 테스트가능하며 유지 관리 가능한 앱을 설계하는데 필요한 라이브러리 모음입니다.
    * DataBinding : 관찰 가능한 데이터를 UI요소에 바인딩합니다.
    * Lifecycles : 수명 주기 이벤트에 자동으로 응답하는 UI를 만듭니다.
    * LiveData : 수명주기를 고려한 관찰가능한 데이터 개체를 빌드합니다.
    * Navigation : 앱 안의 여러가지 탐색을 구현하도록 도와줍니다.
    * ViewModel : 앱 회전 시 소멸되지 않는 UI 관련 데이터를 저장 합니다. 
* Coroutine : 비동기 작업들의 코드를 간소화하고 메모리 릭을 줄이기 위해 사용합니다. 
* Dagger hilt : 의존성 주입 라이브러리
* Retrofit2 : API 통신을 위해 구현된 OkHTTP의 HTTP 통신을 간편하게 만들어주는 라이브러리
* LocationManager : 시스템 위치 서비스에 대한 액세스를 제공받을 수 있습니다.(위치 권한 허용이 필요)


## ⚠️ 주의사항
`gradle.properties` 파일에 `카카오 REST API 키` 와 `공공 데이터 포털 일반 인증키(Encoding)` 를 추가해야합니다.




