# 🛸 Rick and Morty Character Browser

<p align="center">
  <img src="https://upload.wikimedia.org/wikipedia/commons/b/b1/Rick_and_Morty.svg" alt="Rick and Morty Logo" width="400"/>
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Kotlin-Multiplatform-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white" alt="Kotlin Multiplatform"/>
  <img src="https://img.shields.io/badge/Compose-Multiplatform-4285F4?style=for-the-badge&logo=jetpack-compose&logoColor=white" alt="Compose Multiplatform"/>
  <img src="https://img.shields.io/badge/Android-34A853?style=for-the-badge&logo=android&logoColor=white" alt="Android"/>
  <img src="https://img.shields.io/badge/iOS-000000?style=for-the-badge&logo=apple&logoColor=white" alt="iOS"/>
</p>

---

## 📱 About

A **Compose Multiplatform** application that lets you explore characters from the Rick and Morty universe. Built with **Clean Architecture** and **MVVM** pattern, targeting both **Android** and **iOS** platforms.

The app fetches data from the official [Rick and Morty API](https://rickandmortyapi.com/api) and provides a smooth, native experience on both platforms.

## ✨ Features

| Feature | Description |
|---------|-------------|
| 🏠 **Character List** | Browse all characters with infinite scroll pagination |
| 🔍 **Search** | Find characters by name with debounced search |
| ❤️ **Favorites** | Save your favorite characters locally |
| 📄 **Character Details** | View full character information with large image |
| 🔄 **Offline Support** | Favorites are stored locally using Room database |

## 🖼️ Screenshots

> 📸 Add your screenshots here! Place them in a `screenshots/` folder and reference them:
>
> ```markdown
> | Home | Search | Favorites | Details |
> |------|--------|-----------|---------|
> | ![Home](screenshots/home.png) | ![Search](screenshots/search.png) | ![Favorites](screenshots/favorites.png) | ![Details](screenshots/details.png) |
> ```

## 🏗️ Architecture

The project follows **Clean Architecture** principles with clear separation of concerns:

```
📁 commonMain/
├── 📁 data/           # Data layer
│   ├── 📁 remote/     # API service, DTOs
│   ├── 📁 local/      # Room database, DAOs, Entities
│   ├── 📁 repository/ # Repository implementations
│   └── 📁 mapper/     # DTO to Domain mappers
├── 📁 domain/         # Domain layer (pure Kotlin)
│   ├── 📁 model/      # Domain models
│   ├── 📁 repository/ # Repository interfaces
│   └── 📁 usecase/    # Business logic use cases
├── 📁 presentation/   # Presentation layer
│   ├── 📁 screens/    # UI screens with ViewModels
│   ├── 📁 components/ # Reusable Compose components
│   ├── 📁 navigation/ # Navigation setup
│   └── 📁 theme/      # Material 3 theming
└── 📁 di/             # Koin dependency injection modules
```

## 🛠️ Tech Stack

| Category | Technology |
|----------|------------|
| 🎨 **UI Framework** | Compose Multiplatform |
| 🌐 **Networking** | Ktor 3.0 |
| 💉 **Dependency Injection** | Koin 4.0 |
| 📦 **Serialization** | kotlinx.serialization |
| 🖼️ **Image Loading** | Coil 3.0 |
| 🧭 **Navigation** | Navigation Compose (JetBrains) |
| 💾 **Local Database** | Room 2.8 |
| ⚡ **Async** | Kotlin Coroutines & Flow |

## 📋 Project Structure

```
composeApp/
├── src/
│   ├── commonMain/     # Shared code for all platforms
│   ├── androidMain/    # Android-specific implementations
│   └── iosMain/        # iOS-specific implementations
iosApp/                 # iOS application entry point
```

## 🚀 Getting Started

### Prerequisites

- Android Studio Ladybug or newer (with Kotlin Multiplatform plugin)
- Xcode 15+ (for iOS development)
- JDK 17+

### Build and Run Android

```powershell
# Windows
.\gradlew.bat :composeApp:assembleDebug

# macOS/Linux
./gradlew :composeApp:assembleDebug
```

Or use the run configuration in Android Studio.

### Build and Run iOS

1. Open the `iosApp/` directory in Xcode
2. Select your target device/simulator
3. Click Run (⌘R)

Or use the iOS run configuration in Android Studio with the Kotlin Multiplatform plugin.

## 🔌 API Reference

This app uses the [Rick and Morty API](https://rickandmortyapi.com/):

| Endpoint | Description |
|----------|-------------|
| `GET /character` | Get all characters (paginated) |
| `GET /character/{id}` | Get character by ID |
| `GET /character/?name={name}` | Search characters by name |

## 📦 Dependencies

All dependencies are managed in `gradle/libs.versions.toml`:

- **Kotlin**: 2.1.0
- **Compose Multiplatform**: Latest stable
- **Ktor**: 3.0.3
- **Koin**: 4.0.2
- **Coil**: 3.0.4
- **Room**: 2.8.0-alpha11
- **Navigation Compose**: 2.8.7

## 🤖 GitHub Copilot Integration

This project includes comprehensive **GitHub Copilot instructions** to help AI assistants understand the codebase and generate consistent, high-quality code.

<p align="center">
  <img src="https://img.shields.io/badge/GitHub%20Copilot-Ready-8B5CF6?style=for-the-badge&logo=github&logoColor=white" alt="Copilot Ready"/>
</p>

📋 **[View Copilot Instructions](.github/copilot-instructions.md)**

The instructions cover:
- ✅ **Project Architecture** - Clean Architecture with MVVM pattern
- ✅ **Coding Conventions** - Naming, patterns, and best practices
- ✅ **UI State Pattern** - Sealed interfaces for all UI states
- ✅ **State Hoisting** - Stateless composable screens
- ✅ **Dependency Injection** - Koin module structure
- ✅ **API Integration** - Ktor networking patterns
- ✅ **Common Mistakes** - What to avoid in the codebase

> 💡 **Tip**: When using GitHub Copilot or other AI assistants in this project, they will automatically follow these guidelines to generate code that matches the project's architecture and conventions.

## 🤝 Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. **Read the [Copilot Instructions](.github/copilot-instructions.md)** to understand coding conventions
4. Commit your changes (`git commit -m 'Add some amazing feature'`)
5. Push to the branch (`git push origin feature/amazing-feature`)
6. Open a Pull Request

## 📄 License

This project is open source and available under the [MIT License](LICENSE).

## 📚 Learn More

- [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)
- [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/)
- [Rick and Morty API Documentation](https://rickandmortyapi.com/documentation)

---

<p align="center">
  Made with ❤️ using Kotlin Multiplatform
</p>

