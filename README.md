# Varto News&Goods
Android client developed for a website [varto.esy.es](varto.esy.es).

<img src="https://github.com/vitaliystoyanov/Varto-android-application/raw/master/demo/demo.gif" width="320">

<a href="https://play.google.com/store/apps/details?id=es.esy.varto_novomyrgorod.varto">
<img alt="Get it on Google Play" src="http://steverichey.github.io/google-play-badge-svg/img/en_get.svg" />
</a>

## Requirements
To compile and run the project you'll need:

- [Android SDK](http://developer.android.com/sdk/index.html)
- [Android N (API 24)](http://developer.android.com/tools/revisions/platforms.html)
- Android SDK Tools
- Android SDK Build Tools `24.0.2`
- Android Support Repository

Building
--------

To build, install and run a debug version, run this from the root of the project:

```
./gradlew assembleDebug
```

Testing
-------

To run **unit** tests on your machine:

```
./gradlew test
```

To run **instrumentation** tests on connected devices:

```
./gradlew connectedAndroidTest
```
