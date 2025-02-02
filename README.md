# LifeHub

LifeHub is a personal life management application built entirely with Jetpack Compose. It uses an MVVM architecture and modern Android development tools (Flow, Hilt, Room, and more) to help you organize your daily tasks, events, and ideas. The app supports features like API calls, local data persistence, and image loading, making it your all-in-one personal organizer.

## Features

- **Jetpack Compose UI:** Clean, declarative UI built using Compose.
- **MVVM Architecture:** Uses ViewModels, Flow, and state management for reactive UIs.
- **Dependency Injection:** Integrated with Hilt for easy and testable dependency management.
- **Local Storage:** Room is used for local database operations.
- **Asynchronous Operations:** Kotlin Coroutines handle asynchronous tasks.
- **Navigation:** Jetpack Navigation Compose manages in-app navigation.
- **Image Loading:** Coil is used for efficient image loading.
- **Logging:** Timber is integrated for advanced logging and debugging.

## Core Dependencies

The project relies on the following key libraries:

- **AndroidX & Core:**  
  - `androidx.core:core-ktx`  
  - `androidx.lifecycle:lifecycle-runtime-ktx`
- **Jetpack Compose:** Managed via the Compose BOM for consistent versions, including `ui`, `material3`, `ui-tooling-preview`, etc.
- **Coroutines:** `org.jetbrains.kotlinx:kotlinx-coroutines-android` for asynchronous programming.
- **Navigation:** `androidx.navigation:navigation-compose` for screen-to-screen navigation.
- **Lifecycle & ViewModel:** `androidx.lifecycle:lifecycle-viewmodel-compose` for integrating ViewModels with Compose.
- **Hilt:**  
  - `com.google.dagger:hilt-android` for dependency injection.  
  - `com.google.dagger:hilt-android-compiler` (via KSP) for code generation.
- **Room:**  
  - `androidx.room:room-runtime` and `androidx.room:room-ktx` for local database access.  
  - `androidx.room:room-compiler` (via KSP) for annotation processing.
- **Coil:** `io.coil-kt:coil-compose` for image loading.
- **Timber:** `com.jakewharton.timber:timber` for logging.

For complete dependency management, a version catalog (`libs.versions.toml`) is used to centralize and manage versions.

## Getting Started

### Prerequisites

- **Android Studio Arctic Fox or later.**
- **JDK 17.** 
- **Git** for version control.

### Setup

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/your-username/LifeHub.git
   cd LifeHub

