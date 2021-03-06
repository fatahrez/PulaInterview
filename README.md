<p align="left">
  <a href="#"><img alt="Android OS" src="https://img.shields.io/badge/OS-Android-3DDC84?style=flat-square&logo=android"></a>
  <a href="#"><img alt="Languages-Kotlin" src="https://flat.badgen.net/badge/Language/Kotlin?icon=https://raw.githubusercontent.com/binaryshrey/Awesome-Android-Open-Source-Projects/master/assets/Kotlin_Logo_icon_white.svg&color=f18e33"/></a>
  <a href="#"><img alt="PRs" src="https://img.shields.io/badge/PRs-Welcome-3DDC84?style=flat-square"></a>
</p>

# PulaInterview

This app is for Pula (https://www.pula-advisors.com/) Android position. It's a Survey App. Consumes Questions API. Showcases usecase for State and Jetpack Compose framework.

## Table of Contents

- [Functionalities](#functionalities)
- [Screenshots](#screenshots)
- [Approach](#approach)
- [How To Setup](#how-to-setup)
- [Libraries Used](#libraries-used)
- [Author Info](#author-info)

## Functionalities

- A user can login
- A user can View Survey questions
- A user can answer Questions
- A user can can send his/her photo
- The app sends a background service request every 15 minutes

## Screenshots
<img src="https://raw.githubusercontent.com/fatahrez/PulaInterview/master/screenshots/Screenshot_20220517_115926.png" width="300"/>.<img src="https://raw.githubusercontent.com/fatahrez/PulaInterview/master/screenshots/Screenshot_20220517_115956.png" width="300"/>.<img src="https://raw.githubusercontent.com/fatahrez/PulaInterview/master/screenshots/Screenshot_20220517_120030.png" width="300"/>.<img src="https://raw.githubusercontent.com/fatahrez/PulaInterview/master/screenshots/Screenshot_20220517_120038.png" width="300"/>.<img src="https://raw.githubusercontent.com/fatahrez/PulaInterview/master/screenshots/Screenshot_20220517_120206.png" width="300"/>.<img src="https://raw.githubusercontent.com/fatahrez/PulaInterview/master/screenshots/Screenshot_20220517_120050.png" width="300"/>

## Approach ##
### Architecture
This app has an architecture in place to allow the App to **Scale**, improve Quality and Robustness and Allow the App to Scale. This also makes the App to Scale

<img src="https://raw.githubusercontent.com/fatahrez/Pixar/development/screenshots/CleanArchitecture.jpeg" width="500"/>
This App uses Clean Architecture to ensure:

- Separation of **concern**
- Drive UI from Data Model
- Make functionality easily changeable or droppable
- Make Code easier to read
- Make use of good practices and Jetpack libraries(Architecture components)

#### Layers
<img src="https://raw.githubusercontent.com/fatahrez/Pixar/development/screenshots/googleclen.png" width="500"/>

**Domain Layer**

- Sits between the UI and Data layer
- Used in this project to encapsulate business logic
- Enables use-cases to be reused in multiple view model
- Defines the repository interface that drives the main functionality

**Data Layer**

- Contains the implementation of business logic(Repository Implementation)
- Gets data from the remote data source
- Cache's remote data to Local Room Database

**Presentation/UI layer**

- This layer is the layer that displays data to the user screen
- Contains view models that are lifecycle friendly and takes code away from our Activity/UI components
- Defines our architecture which is MVVM (Model View View-Model)
- Contains our states that handle logic like loading

**Dependency Injection**

- Used Hilt Dagger library for dependency injection
- Allows classes to define their dependencies without constructing them
- Also brings all the layers of the App together
- Helps UI layer to be driven from data layer


#### states

- The app uses states in ViewModel to queue different Events
- The states send UI events from ViewModel to UI
- Handles loading of the app and knows when there's an error
- Sealed classes flow freely in States

## How to Setup

Clone this repo

    $ git clone git@github.com:fatahrez/PulaInterview.git

or HTTPS

    $ git clone https://github.com/fatahrez/PulaInterview.git

Open the project with Android Studio and build using gradle. Feel free to run the App on your phone.


**Login**

Phone Number: Any phone number starting +2547....
password: 1234GYD%$

## Libraries used

- **Retrofit** - Android Network Client, Used to consume API from Pixabay API
- **Room** - SQLite ORM - used to save data to the phone's database for caching
- **Hilt Dagger** - Used for dependency Injection
- **Coroutines** - Used to execute code asynchronously
- **Jetpack Compose** - Used to write the declarative UI of the App
- **Material Design** - Give the App a theme and generally improve UI of the App
- **Coil Image** - Image Loading library
- **Lifecycle library** - Majorly to define the ViewModels of the app
- **Compose nDestination** - To navigate to different screens of the App
- **WorkManager** - Background periodic tasks
- **OkHttpLogging Interceptor** - Logging network requests
- **Mockk** - Mocking for testing

# Author Info

Twitter - [@TheBandanaDev](https://twitter.com/TheBandanaDev)

LinkedIn - [Abdulfatah](https://www.linkedin.com/in/abdulfatah-mohamed-83709a177/)


[Back To The Top](#PulaInterview)
