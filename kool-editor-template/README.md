# kool-editor-template

Template project for creating a new editor-based application with kool.
The editor is still in an early state and not super-useful yet. Even worse, I can't yet
guarantee project model compatibility during updates. So any editor-made scene setup
might not be usable with future versions anymore.

So you definitely should not use this for any productive projects and consider it to be
more of a demo / test project.

## How to start

Clone this repo and import the `kool-editor-template` directory into your favourite IDE (e.g. [IntelliJ](https://www.jetbrains.com/idea/)).
Ideally, the `build.gradle.kts` is recognized and imported automatically.

First you will want to start the editor. You can do this from within IntelliJ by opening the
gradle panel on the right side and start the `runEditor` task (`kool-editor-template -> Tasks -> editor -> runEditor`).

Alternatively you can start the editor from a terminal:

```shell
./gradlew runEditor
```

The project already contains some example content, which should show up in the editor. You
can now edit the scene graphically in the editor and edit code in your IDE. Code changes
should be detected by the editor and hot-reloaded.

Moreover, classes extending `KoolBehavior` will be recognized by the editor and can be
added to objects as custom components (quite similar to Unity's Scripts). The sample code
contains the class [`SampleRotationAnimator`](src/commonMain/kotlin/de/fabmax/kool/app/SampleRotationAnimator.kt),
which gives a simple example of such a custom behavior class. Notice that public members (like `rotationSpeed` and
`speedMulti` in this example) show up in the editor UI and can be manipulated at runtime.
