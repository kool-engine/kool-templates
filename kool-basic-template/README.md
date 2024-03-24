# kool-basic-template

Template project for creating a new multi-platform code-only application with kool.
The project contains the minimum set of dependencies and launcher code to get you started.

## How to start

Clone this repo and import the `kool-basic-template` directory into your favourite IDE (e.g. [IntelliJ](https://www.jetbrains.com/idea/)).
Ideally, the `build.gradle.kts` is recognized and imported automatically. The project already contains a minimal set
of example code:

- [`src/jvmMain/kotlin/Launcher.kt`](src/jvmMain/kotlin/Launcher.kt):
  JVM-specific context creation

- [`src/jsMain/kotlin/Launcher.kt`](src/jsMain/kotlin/Launcher.kt):
  Javascript-specific context creation

- [`src/commonMain/kotlin/template/CommonLauncher.kt`](src/commonMain/kotlin/template/CommonLauncher.kt):
  Main application entry point. This is where you should start writing your app. The example-code creates a spinning
  color cube.

For further demos and examples you should consult the main [kool repo](https://github.com/fabmax/kool). However,
unfortunately, documentation is still barely existing (but you are welcome to contribute some :smile:).

## How to run

You can start the project right away out of your IDE or via gradle from a terminal inside the `kool-basic-template`
directory:
```shell
./gradlew runApp
```

## How to package

In case you want to distribute your game, running it via gradle or out of the IDE isn't really an option. Therefore,
the task
```shell
./gradlew build
```
creates two (very basic) distributions for JVM and Javascript located under `dist/jvm` and `dist/js`. In order to
start the Javascript version you will need some sort of web-server. Double-clicking the `index.html` in the explorer
won't do the trick (because of browser security restrictions). However, IntelliJ comes with a built-in webserver, so
you can simply open the `index.html` in IntelliJ and click on your favourite browser icon in the upper right corner of
the editor.
