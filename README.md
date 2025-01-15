<img src="https://github.com/user-attachments/assets/2b488c1d-d228-49fb-9ee5-ac88b9bc55e5" width="90" height="90">

# MovieHub

MovieHub - It is an app to easily and quickly discover popular movies from the TMDB api database.

The goal of the project was to implement a simple and fast way for the user to search and get information about their favorite movies at the moment. In turn, implement a temporary Off-Line mode with several jetpack libraries such as DataStore for theme changing options, Room database for data storage, pagination, jetpack compose, material 3 and among other third-party libraries.

## Features

- Temporary caching, in case you are offline the data will be stored for a while.
- Save your favorite movies in a database for offline use.
- Change theme to light and dark or dynamic if you have a device with Android 12+.
- A search engine.

## Tech stack

- Kotlin
- Serialization
- Coroutines
- Kps
- Compose
- Hilt
- ViewModel
- Navigation
- Paging 3
- Data Store
- Splash Screen
- Retrofit
- OkHttp
- Coil

## Architecture

The pattern used for this project is MVVM (Model-View-ViewModel) which is a widely used architectural pattern in Android development that promotes separation of concerns, testability, and maintainability. It divides an application into three interconnected parts:

1. **Model**: Represents the data and business logic of the application. It's responsible for fetching, storing, and manipulating data. This might include data classes, repositories, and data sources like databases or network APIs.
2. **View**: In Compose, the View is represented by composable functions. These functions describe the UI declaratively, defining how it should look based on the current state. Instead of directly manipulating UI elements, composable functions recompose when the underlying data changes.
3. **ViewModel**: Acts as an intermediary between the Model and the View. It exposes data from the Model in a way that's easily consumable by the View. It also handles user interactions from the View and updates the Model accordingly. The ViewModel is lifecycle-aware, meaning it survives configuration changes like screen rotations.

![Mvvm arch](https://github.com/user-attachments/assets/011add8b-cd32-4ae7-b78e-60a2ca578a59)
