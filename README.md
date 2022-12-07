# Sweat-With-Annette

This project is based on the 7 Minute Workout app that is part by Udemy Course called "The Complete Android 12 & Kotlin Development Masterclass" by Denis Panjuta. The course is an excellent course which was created several years ago. 

I recently used this app from that course as a vehicle to practice what I have learned in the 2 years since I took the cours. I added many new elements:
- MVVM Architecture was used.
  * Repository is used to insulate ViewModels from the Database. 
  * ViewModels are used to insulate the Screens from the Repository.
  * Domain models are separate from the database entity models. Simple mappers are created to go between them.
- Jetpack Compose was used for all screens
  * Animation is used in several places
- Material 3 design was used.
  * A custom color scheme is implemented and applied logically throughout the app.
- Single Activity was used, no fragments
- All dependencies brought up to date (as of December 1, 2022)
- Navigation is used to go between the screens.
- Dagger/Hilt is used to:
  * Inject the ViewModels into the top level Compose Screens.
  * Inject the Repositories into the ViewModels.
  * Inject the Database and Dao into the Repositories.
  


