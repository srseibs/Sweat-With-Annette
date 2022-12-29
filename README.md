# Sweat-With-Annette

<img src="/app/src/main/res/drawable/start_logo.png" width="20%">


This project is based on the 7 Minute Workout app that is one of many apps in the Udemy Course called "The Complete Android 12 & Kotlin Development Masterclass" by Denis Panjuta. This an excellent course which was created several years ago. 

I recently used this app from that course as a vehicle to practice what I have learned in the 2 years since I took the course. I added many new elements:
- _**MVVM Architecture**_ was used.
  * Repository is used to insulate ViewModels from the Database. 
  * ViewModels are used to insulate the Screens from the Repository.
  * Domain models are separate from the database entity models. Simple mappers are created to go between them.
- _**Jetpack Compose**_ was used for all screens
  * Animation is used in several places
  * **Compose BOM** is demonstrated in the build.gradle file.
- _**Material 3**_ design was used.
  * A custom color scheme is implemented and applied logically throughout the app.
- Single Activity was used, no fragments
- _**Custom Workout**_ manager/creator/editor
  * Drag-to-reorder in Lazy list
  * ViewModel-based validation of field edits
- All dependencies brought up to date (as of December 1, 2022)
- _**Navigation**_ is used to go between the screens.
- _**Dagger/Hilt**_ is used to:
  * Inject the ViewModels into the top level Compose Screens.
  * Inject the Repositories into the ViewModels.
  * Inject the Database and Dao into the Repositories.
- The following is implemented in the ViewModel to keep the concerns separated from UI:
  * TextToSpeech
  * MediaPlayer for sound effects
  * Timer (which will now Pause when deciding to abort a workout)
- _**Workout Sets**_ created to allow custom subsets of the master list of exercises.
- Created Unit tests for BMI Diagnoses and Instrumented tests for Room database

  


