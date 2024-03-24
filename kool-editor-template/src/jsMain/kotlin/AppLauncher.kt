import de.fabmax.kool.KoolApplication
import de.fabmax.kool.KoolContext
import de.fabmax.kool.app.App
import de.fabmax.kool.editor.api.BehaviorLoader

fun main() = KoolApplication { ctx ->
    launchApp(ctx)
}

private fun launchApp(ctx: KoolContext) {
    BehaviorLoader.appBehaviorLoader = BehaviorBindings
    App().launchStandalone(ctx)
}