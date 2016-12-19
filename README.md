# Varto News&Goods ![API](https://img.shields.io/badge/API-16%2B-brightgreen.svg?style=flat)
[varto.esy.es](varto.esy.es) is online shop that provides news and goods. I worked on design
and architecture of Android application. I gained hands-on experience in implementing MVP architecture, data access layer with SQLite database and custom views. Application code is covered with local-instrumented unit tests.

<img src="https://github.com/vitaliystoyanov/Varto-android-application/raw/master/demo/demo.gif" width="320">

<a href="https://play.google.com/store/apps/details?id=es.esy.varto_novomyrgorod.varto">
<img alt="Get it on Google Play" src="http://steverichey.github.io/google-play-badge-svg/img/en_get.svg" height="76px"/>
</a>

## Libraries
* [OkHttp](http://square.github.io/okhttp/)
* [Mosby](http://hannesdorfmann.com/mosby/)
* [Google Support Libraries](http://developer.android.com/tools/support-library/index.html)

## Testing Libraries
* [JUnit](http://junit.org/junit4/)
* [Robolectric](http://robolectric.org/)

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

