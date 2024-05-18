import de.fabmax.kool.KoolApplication
import de.fabmax.kool.KoolConfigJvm
import de.fabmax.kool.editor.KoolEditor

/**
 * Editor launcher main function. It is recommended to run this by executing the gradle task ":runEditor". However,
 * it is also possible to manually start this from your IDE or the shell (make sure the working directory is set
 * to the .editor directory).
 */
fun main() = KoolApplication(
    KoolConfigJvm(
        windowTitle = "Kool Editor",
        showWindowOnStart = false
    )
) {
    // by default editor working dir is in $projectDir/.editor -> project root path is the parent directory
    KoolEditor("..", ctx)
}
