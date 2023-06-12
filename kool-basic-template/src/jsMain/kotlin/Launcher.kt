import de.fabmax.kool.KoolApplication
import de.fabmax.kool.KoolConfig
import template.launchApp

/**
 * JS main function / app entry point: Creates a new KoolContext (with optional platform-specific configuration) and
 * forwards it to the common-code launcher.
 */
fun main() = KoolApplication(
    config = KoolConfig(
        canvasName = "glCanvas"
    )
) { ctx ->
    launchApp(ctx)
}