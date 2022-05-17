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


### states

- The app uses states in ViewModel to queue different Events
- The states send UI events from ViewModel to UI
- Handles loading of the app and knows when there's an error
- Sealed classes flow freely in States