# EGS Android Challenge

Solution to EGS Android Challenge app: MovieDB client which makes network requests through **Bound Service**.

## Release

The Apk file could be downloaded at [Release](https://github.com/hoang06kx1/android-moviedb-mvvm-binding-service/releases/tag/1.0)


## Requirements

This app is built with [Android Studio Electric Eel | 2022.1.1 Patch 1](https://developer.android.com/studio).

Set your API KEY from movie DB website into **local.properties** file
```
API_KEY="d80bdd0cf05a741c59b655820aa0ab2c"
```
then build the app.

Or run below command

```
./gradlew assembleDebug
```

## Features and integrations

This sample application use following libraries and features:

- [Jetpack Navigation](https://developer.android.com/guide/navigation): One Activity - multiple Fragments design
- [Pagination 3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview): Endless pagination
- [Coil](https://coil-kt.github.io/coil/): Image loader
- [Koin](https://insert-koin.io/): Dependency Injection (It's more like a service locator instead)
- MVVM pattern
- Kotlin Coroutine & Flow
- Retrofit
- OkHttp



The app is broadly built using an MVVM-ish architecture. One Activity - multiple Fragments design is used for the View layer by leveraging Jetpack Navigation, AndroidX ViewModels are used for the ViewModel layer and Data layer contains MovieRepository which eventually fetch remote data by calling to a **Bound Service**. Since the app is simple and has no favor for reusability so Domain Layer i.e., UseCases is ignored.

Breaking it down, every screens must bind to ApiAndroidService first to make any API requests, then unbind when destroyed to optimize memory usage and prevent memory leak.

ApiAndroidService itself acts as a mediator which plays nothing than a connection point for data fetching from remote, hence it belongs to Data layer and behind MovieRepository/MoviePagingSource. **MovieApiServiceImpl** and **ApiAndroidService** use Decorator pattern to achive this.


UI State is preserved through **Configuration change**, but for **Process Death** some screen will be reloaded (Movie Detail Screen for example).


## About me

Just an ordinary humorous developer living somewhere in Vietnam (GMT+7) (He/him).

Contact me at [hoang06kx1@gmaill.com](mailto:hoang06kx1@gmail.com)
