# ZEMOGA MOBILE TEST

## About the project
  * [Kotlin](https://kotlinlang.org/docs/home.html) - has being used to develop this application.
  * [Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) - for async programming.
  * [ViewBinding](https://developer.android.com/topic/libraries/view-binding) - was implemented to take advantages of the safe vinculation of the view and its binding.
  * [Androidx](https://developer.android.com/jetpack/androidx) - the new implementation of the Support Libs.
  * [Gson](https://github.com/square/retrofit/tree/master/retrofit-converters/gson) - to deserealize the data provided by the services.
  * [Retrofit2](https://square.github.io/retrofit/) - a network framework that facilitates the way of how the requests are made.
  * [Hilt](https://developer.android.com/training/dependency-injection/hilt-android?hl=en-419) - The 'new' Library for Dependency Injection, recommended by Google - Android Docummentation. 
  * [Android Navigation Component](https://developer.android.com/guide/navigation/navigation-getting-started) - To delegate the navigation of the application.
  * [Room](https://developer.android.com/training/data-storage/room) - To persist and 'cache' the data locally.
  


## APP ARCHITECTURE

Clean Architecture is the architecture that has being implemented in order to express the separation of concerns of each implementation _(as a main idea)_.

### Project Structure
```
../data
    ../local            // where the local data persistance implementation is loated
    ../repository       // The implementation of repositories are located here
    ../remote           // Everything related with the requests to the API service
    ../entities         // The entities that belongs to the data-layer are defined here. Such as: Entities and Responses
    ../mappers          // Mappers that prepares the data to be retrieved to upper layers
../domain
    ../usecases         // The definition of use cases are defined here
    ../repository       // Here are the abstractions of the data-layer 
    ../entities         // The entities that belongs to the domain-layer are defined here.
../ui
    ../featureName      // Each feature will have its own package 
        ../models       // All the models related to the feature will be defined here
        ../viewmodels   // ViewModels are defined here
            ../viewstates   // viewStates represents a state of the view to organize the view logic
        ../mappers      // Mappers that prepares the upcoming data from lower layers to be binded in the view
        ../adapters     // Adapters 
../di                   // The Dependency Injection Module is defined here
```

About the author:
- *name*: Joel Colmenares
- *email*: jkhin.cb@gmail.com



