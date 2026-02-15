# Training - NoteApp

A professional Android application built with Jetpack Compose for creating and managing notes. This project demonstrates modern Android development practices, including **ViewModel** for UI state, Room for local persistence, Hilt for dependency injection, and declarative UI with Compose. It was developed in the same course as the MovieApp and is my first project using ViewModel concepts.

## Features

* **Note List**: A scrollable list of notes with title, description, and formatted entry date.
* **Add Notes**: Input fields for title and description with validation; tap "Save" to persist notes locally.
* **Delete Notes**: Tap a note in the list to remove it from the database.
* **Persistent Storage**: Notes are stored with Room and survive app restarts.
* **Reactive UI**: The list updates automatically when notes are added or removed, using Kotlin Flow and `collectAsState`.
* **Material 3 Design**: Fully implemented using M3 components, including Scaffold, TopAppBar, and styled cards with rounded corners.

## Tech Stack

* **Language**: Kotlin
* **UI Framework**: Jetpack Compose
* **State & Architecture**: ViewModel, Kotlin Flow, `collectAsState`
* **Persistence**: Room (SQLite)
* **Dependency Injection**: Hilt
* **Theme**: Material 3 (M3)

## Project Structure

The project follows a modular package structure for better maintainability:

```text
com.example.training_noteapp
├── components       # Reusable UI (NoteInputText, NoteButton)
├── data             # Room database, DAO, Converters (UUID/Date)
├── di               # Hilt AppModule (database, DAO)
├── model            # Note entity (Room + domain)
├── repository       # NoteRepository (single source of truth)
├── screen           # NoteScreen, NoteRow, NoteViewModel
├── ui/theme         # Color, Theme, Type
└── MainActivity.kt  # Entry point, Theme, ViewModel wiring
```

## Getting Started

### Prerequisites
* Android Studio Ladybug (2024.2.1) or newer.
* JDK 17+.
* Android SDK Level 34+.

## Key Components

### ViewModel & State
The app uses a **ViewModel** (`NoteViewModel`) to hold UI state and business logic. The note list is exposed as a `StateFlow<List<Note>>` and collected in the UI with `collectAsState()`, so the list recomposes when data changes. Add, update, and delete operations are dispatched via the ViewModel and run on the background using `viewModelScope` and coroutines.

### Repository & Room
`NoteRepository` wraps the Room DAO and exposes a `Flow<List<Note>>` for all notes. The ViewModel subscribes to this flow in `init`, so any change in the database (add, update, delete) automatically updates the in-memory list and the UI.

### Dependency Injection
Hilt is used to provide the Room database, DAO, and repository. The ViewModel is annotated with `@HiltViewModel` and injected into the screen via `viewModel<NoteViewModel>()`.

### Responsive UI
* **NoteInputText**: Reusable outlined text field with label and optional IME action (e.g. Done).
* **NoteButton**: Styled button used for "Save".
* **NoteRow**: Card-style surface with rounded corners; displays title, description, and formatted date and handles click-to-delete.

## Training & Credits

This project was developed as part of the learning journey in the **[Jetpack Compose Masterclass](https://www.udemy.com/course/kotling-android-jetpack-compose-/?couponCode=CP250105G1)** on Udemy.

### Installation
1. **Clone the repository:**
   ```bash
   git clone https://github.com/yourusername/Training-NoteApp.git
   ```
2. **Open in Android Studio:**  
   Select "Open" and navigate to the project folder.
3. **Sync Gradle:**  
   Wait for the project to download the necessary dependencies (Room, Hilt, Compose, Coroutines).
4. **Run:**  
   Click the Run button or press Shift + F10 on your emulator or physical device.
