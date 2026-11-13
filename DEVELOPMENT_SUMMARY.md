# 📘 SoApp Architectural & Unit Testing Master Reference Guide (Modern Android 2026)

ยินดีต้อนรับสู่ **SoApp Reference Project**! เอกสารฉบับนี้ถูกออกแบบมาเพื่อเป็น **คู่มือแม่แบบโครงสร้างแอปพลิเคชัน (Master Template & Best Practices Guide)** สำหรับศึกษาการทำ Modern Android Development ในปี 2026 โดยใช้ **Clean Architecture (Feature-First)**, **MVVM + Unidirectional Data Flow (UDF)**, **Jetpack Compose (Material 3)**, และ **Comprehensive Unit Testing (JVM Fast Tests)**

---

## 🏗️ 1. สถาปัตยกรรมระบบ (Architectural Overview)

การจัดวางโค้ดในโปรเจกต์นี้ยึดหลัก **Clean Architecture** แบ่งแยกโฟลเดอร์ตาม **Feature (Feature-First Architecture)** ซึ่งเป็นมาตรฐานสูงสุดที่แอปพลิเคชันระดับ Production เลือกใช้ เพื่อให้รองรับการขยายทีมและขยายฟีเจอร์ในอนาคตได้โดยไม่เกิดความสับสน

```text
com.example.soapp/
├── core/                                 # 📦 Core Layer: โมเดลและเครื่องมือที่ใช้ร่วมกันทั้งแอป
│   ├── model/                            # Data Classes (Post, UserProfile, SubscriptionPlan, PaymentResult)
│   └── session/                          # UserSessionManager (State แชร์ระดับ App)
│
├── feature/                              # 🚀 Feature-First Packages
│   ├── feed/                             # 1️⃣ Feature: Social Feed
│   │   ├── domain/                       # Pure Kotlin Business Logic (Entities, Repository Interfaces, UseCases)
│   │   ├── data/                         # Data Fetching & Repository Implementation (Mock/API/Room)
│   │   └── presentation/                 # UI Layer (Compose Screens, ViewModel, UiState, UiEvents)
│   │
│   ├── profile/                          # 2️⃣ Feature: User Profile & Stats
│   │   ├── domain/
│   │   ├── data/
│   │   └── presentation/
│   │
│   └── subscription/                     # 3️⃣ Feature: Subscription & Billing Sandbox
│       ├── domain/
│       ├── data/
│       └── presentation/
│
├── navigation/                           # 🧭 Navigation Layer: MainScreen & Bottom Bar State
└── MainActivity.kt                       # 📱 Activity Entry Point
```

---

## 🧠 2. ทำความเข้าใจหลักการสำคัญ (Core Concepts Explained)

### 2.1 Clean Architecture (3 Layers Principle)
1. **Domain Layer (หัวใจหลักของ Business Logic)**:
   * เขียนด้วย **Pure Kotlin 100%** ห้ามนำเข้า `import android.*`
   * ประกอบด้วย **UseCases** (หน้าที่เดี่ยว เช่น `GetFeedPostsUseCase`, `PurchaseSubscriptionUseCase`) และ **Repository Interfaces**
   * **ข้อดี:** สามารถเขียน Unit Test ทดสอบได้เร็วระดับมิลลิวินาที โดยไม่ต้องเปิด Android Emulator

2. **Data Layer (ส่วนจัดการข้อมูล)**:
   * ทำหน้าที่ดึงและแปลงข้อมูล (Data Sources -> Repositories -> Mappers)
   * ซ่อนที่มาของข้อมูล (Network API / Database / Memory Cache) ไม่ให้ Domain และ UI รู้จัก

3. **Presentation Layer (ส่วนแสดงผล & UI)**:
   * สร้างด้วย **Jetpack Compose (Material 3)** + **ViewModel**
   * ใช้องค์ประกอบ **Unidirectional Data Flow (UDF)**:
     * **UiState (StateFlow):** ไหลจาก ViewModel ลงไปยัง Compose UI
     * **UiEvent (User Intent):** ไหลจาก Compose UI กลับขึ้นไปยัง ViewModel

---

## 🧪 3. คู่มือการทำ Unit Test (JVM Unit Testing Guide)

ในโปรเจกต์นี้มีชุด **Unit Tests รวม 9 Test Cases** ที่ผ่านการทดสอบแบบ **Green 100%** (`:app:testDebugUnitTest`)

```bash
# คำสั่งรัน Unit Tests ผ่าน Gradle
./gradlew :app:testDebugUnitTest
```

### 3.1 เทคโนโลยีที่ใช้ในการทดสอบ
* **JUnit 4**: Framework หลักในการเขียน `@Test`, `assertEquals`, `assertTrue`
* **Kotlinx Coroutines Test (`runTest`)**: ช่วยจำลองและทดสอบฟังก์ชัน `suspend` โดยไม่ต้องรอนาน
* **Turbine (`app.cash.turbine`)**: ไลบรารีระดับพรีเมียมสำหรับทดสอบ **StateFlow / Flow emissions**
* **Fake Repositories Pattern**: การใช้ Fake แทน Mock ช่วยให้โค้ดเทสอ่านง่าย บำรุงรักษาง่าย และไม่เปราะบางเมื่อrefactor โค้ด

### 3.2 รายชื่อไฟล์ Unit Tests ในโปรเจกต์

| ไฟล์ Unit Test | ส่วนที่ทำการทดสอบ | สิ่งที่ทดสอบ |
| :--- | :--- | :--- |
| [GetFeedPostsUseCaseTest.kt](file:///Users/apisit/AndroidStudioProjects/SoApp/app/src/test/java/com/example/soapp/feature/feed/domain/usecase/GetFeedPostsUseCaseTest.kt) | Domain UseCase | ทดสอบการคืนค่าลิสต์โพสต์สำเร็จ และทดสอบการจัดการ Exception |
| [FeedViewModelTest.kt](file:///Users/apisit/AndroidStudioProjects/SoApp/app/src/test/java/com/example/soapp/feature/feed/presentation/FeedViewModelTest.kt) | ViewModel & UDF | ทดสอบ `MainDispatcherRule`, การเปลี่ยน state เป็น `Success`, และการกด Like โพสต์ |
| [PurchaseSubscriptionUseCaseTest.kt](file:///Users/apisit/AndroidStudioProjects/SoApp/app/src/test/java/com/example/soapp/feature/subscription/domain/usecase/PurchaseSubscriptionUseCaseTest.kt) | Sandbox Billing UseCase | ทดสอบผลลัพธ์การชำระเงินประเภท `SUCCESS` และ `DECLINED` |
| [SubscriptionViewModelTest.kt](file:///Users/apisit/AndroidStudioProjects/SoApp/app/src/test/java/com/example/soapp/feature/subscription/presentation/SubscriptionViewModelTest.kt) | Subscription ViewModel | ทดสอบการเปลี่ยนสถานะเป็นสมาชิก Pro และการกดชำระเงิน/ยกเลิกสมาชิก |
| [MainDispatcherRule.kt](file:///Users/apisit/AndroidStudioProjects/SoApp/app/src/test/java/com/example/soapp/util/MainDispatcherRule.kt) | Test Helper Rule | JUnit Rule สำหรับแทนที่ `Dispatchers.Main` บน JVM Unit Test |

---

## 📁 4. ดัชนีไฟล์ทั้งหมดในโปรเจกต์ (File Index)

### 4.1 Core Components
* [Post.kt](file:///Users/apisit/AndroidStudioProjects/SoApp/app/src/main/java/com/example/soapp/core/model/Post.kt) - Data class ของโพสต์โซเชียล
* [UserProfile.kt](file:///Users/apisit/AndroidStudioProjects/SoApp/app/src/main/java/com/example/soapp/core/model/UserProfile.kt) - Data class ของโปรไฟล์ผู้ใช้ + สถานะ Pro
* [SubscriptionPlan.kt](file:///Users/apisit/AndroidStudioProjects/SoApp/app/src/main/java/com/example/soapp/core/model/SubscriptionPlan.kt) - Data class ของแพ็กเกจสมาชิก
* [PaymentResult.kt](file:///Users/apisit/AndroidStudioProjects/SoApp/app/src/main/java/com/example/soapp/core/model/PaymentResult.kt) - Enum ตัวเลือกทดสอบชำระเงิน Sandbox
* [UserSessionManager.kt](file:///Users/apisit/AndroidStudioProjects/SoApp/app/src/main/java/com/example/soapp/core/session/UserSessionManager.kt) - Singleton แชร์สถานะสมาชิก Pro ระหว่างหน้าจอ

### 4.2 Feature: Social Feed (`feature/feed`)
* [FeedRepository.kt](file:///Users/apisit/AndroidStudioProjects/SoApp/app/src/main/java/com/example/soapp/feature/feed/domain/repository/FeedRepository.kt) - Domain Repository Contract
* [GetFeedPostsUseCase.kt](file:///Users/apisit/AndroidStudioProjects/SoApp/app/src/main/java/com/example/soapp/feature/feed/domain/usecase/GetFeedPostsUseCase.kt) - UseCase ดึงฟีด
* [ToggleLikePostUseCase.kt](file:///Users/apisit/AndroidStudioProjects/SoApp/app/src/main/java/com/example/soapp/feature/feed/domain/usecase/ToggleLikePostUseCase.kt) - UseCase กด Like โพสต์
* [MockFeedDataSource.kt](file:///Users/apisit/AndroidStudioProjects/SoApp/app/src/main/java/com/example/soapp/feature/feed/data/datasource/MockFeedDataSource.kt) - Fake Data Source จำลองโพสต์
* [FeedRepositoryImpl.kt](file:///Users/apisit/AndroidStudioProjects/SoApp/app/src/main/java/com/example/soapp/feature/feed/data/repository/FeedRepositoryImpl.kt) - Repository Implementation
* [FeedUiState.kt](file:///Users/apisit/AndroidStudioProjects/SoApp/app/src/main/java/com/example/soapp/feature/feed/presentation/FeedUiState.kt) - Sealed interface ควบคุมสถานะ UI
* [FeedUiEvent.kt](file:///Users/apisit/AndroidStudioProjects/SoApp/app/src/main/java/com/example/soapp/feature/feed/presentation/FeedUiEvent.kt) - Sealed interface รับคำสั่งผู้ใช้
* [FeedViewModel.kt](file:///Users/apisit/AndroidStudioProjects/SoApp/app/src/main/java/com/example/soapp/feature/feed/presentation/FeedViewModel.kt) - ViewModel จัดการ StateFlow
* [FeedScreen.kt](file:///Users/apisit/AndroidStudioProjects/SoApp/app/src/main/java/com/example/soapp/feature/feed/presentation/FeedScreen.kt) - Jetpack Compose UI

### 4.3 Feature: User Profile (`feature/profile`)
* [ProfileRepository.kt](file:///Users/apisit/AndroidStudioProjects/SoApp/app/src/main/java/com/example/soapp/feature/profile/domain/repository/ProfileRepository.kt) & [GetUserProfileUseCase.kt](file:///Users/apisit/AndroidStudioProjects/SoApp/app/src/main/java/com/example/soapp/feature/profile/domain/usecase/GetUserProfileUseCase.kt)
* [MockProfileDataSource.kt](file:///Users/apisit/AndroidStudioProjects/SoApp/app/src/main/java/com/example/soapp/feature/profile/data/datasource/MockProfileDataSource.kt) & [ProfileRepositoryImpl.kt](file:///Users/apisit/AndroidStudioProjects/SoApp/app/src/main/java/com/example/soapp/feature/profile/data/repository/ProfileRepositoryImpl.kt)
* [ProfileUiState.kt](file:///Users/apisit/AndroidStudioProjects/SoApp/app/src/main/java/com/example/soapp/feature/profile/presentation/ProfileUiState.kt), [ProfileViewModel.kt](file:///Users/apisit/AndroidStudioProjects/SoApp/app/src/main/java/com/example/soapp/feature/profile/presentation/ProfileViewModel.kt), [ProfileScreen.kt](file:///Users/apisit/AndroidStudioProjects/SoApp/app/src/main/java/com/example/soapp/feature/profile/presentation/ProfileScreen.kt)

### 4.4 Feature: SoApp Pro Subscription & Billing Sandbox (`feature/subscription`)
* [SubscriptionRepository.kt](file:///Users/apisit/AndroidStudioProjects/SoApp/app/src/main/java/com/example/soapp/feature/subscription/domain/repository/SubscriptionRepository.kt) & UseCases ([GetSubscriptionPlansUseCase.kt](file:///Users/apisit/AndroidStudioProjects/SoApp/app/src/main/java/com/example/soapp/feature/subscription/domain/usecase/GetSubscriptionPlansUseCase.kt), [PurchaseSubscriptionUseCase.kt](file:///Users/apisit/AndroidStudioProjects/SoApp/app/src/main/java/com/example/soapp/feature/subscription/domain/usecase/PurchaseSubscriptionUseCase.kt), [CancelSubscriptionUseCase.kt](file:///Users/apisit/AndroidStudioProjects/SoApp/app/src/main/java/com/example/soapp/feature/subscription/domain/usecase/CancelSubscriptionUseCase.kt))
* [MockSubscriptionDataSource.kt](file:///Users/apisit/AndroidStudioProjects/SoApp/app/src/main/java/com/example/soapp/feature/subscription/data/datasource/MockSubscriptionDataSource.kt) & [SubscriptionRepositoryImpl.kt](file:///Users/apisit/AndroidStudioProjects/SoApp/app/src/main/java/com/example/soapp/feature/subscription/data/repository/SubscriptionRepositoryImpl.kt)
* [SubscriptionUiState.kt](file:///Users/apisit/AndroidStudioProjects/SoApp/app/src/main/java/com/example/soapp/feature/subscription/presentation/SubscriptionUiState.kt), [SubscriptionUiEvent.kt](file:///Users/apisit/AndroidStudioProjects/SoApp/app/src/main/java/com/example/soapp/feature/subscription/presentation/SubscriptionUiEvent.kt), [SubscriptionViewModel.kt](file:///Users/apisit/AndroidStudioProjects/SoApp/app/src/main/java/com/example/soapp/feature/subscription/presentation/SubscriptionViewModel.kt), [SubscriptionScreen.kt](file:///Users/apisit/AndroidStudioProjects/SoApp/app/src/main/java/com/example/soapp/feature/subscription/presentation/SubscriptionScreen.kt)

---

## 🚀 5. คำแนะนำสำหรับการนำไปเป็นต้นแบบทำแอปอื่นๆ

หากคุณต้องการนำโครงสร้างนี้ไปสร้างแอปพลิเคชันใหม่:
1. **คัดลอกโครงสร้างแพ็กเกจ `core/`**: ใช้โมเดลและ Result wrapper ร่วมกัน
2. **สร้างแพ็กเกจใหม่ภายใต้ `feature/ชื่อฟีเจอร์`**: แบ่งเป็น `domain`, `data`, `presentation` เสมอ
3. **เขียน UseCase สำหรับทุกๆ 1 Business Action**: เพื่อให้อ่านง่ายและเขียน Unit Test สะดวก
4. **ออกแบบ UI ด้วย Compose + StateFlow**: รักษาความบริสุทธิ์ของ UDF (UI แสดงผลจาก State และส่ง Event ขึ้น ViewModel)
