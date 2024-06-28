package de.fabmax.kool.app

import de.fabmax.kool.editor.api.EditorInfo
import de.fabmax.kool.editor.api.KoolBehavior
import de.fabmax.kool.editor.api.sceneEntity
import de.fabmax.kool.editor.components.CharacterControllerComponent
import de.fabmax.kool.editor.components.PhysicsWorldComponent
import de.fabmax.kool.input.InputStack
import de.fabmax.kool.input.KeyboardInput
import de.fabmax.kool.math.Vec3f
import de.fabmax.kool.physics.util.CharacterTrackingCamRig
import de.fabmax.kool.scene.Camera

class CharacterCameraRig : KoolBehavior() {

    @EditorInfo(label = "Character:", order = 1)
    var trackedCharacter: CharacterControllerComponent? = null

    @EditorInfo(label = "Pivot point:", order = 2)
    var pivotPoint = Vec3f(0f, 0.75f, 0f)
        set(value) {
            field = value
            tracker?.pivotPoint?.set(value)
        }

    @EditorInfo(label = "Obstacle aware camera:", order = 3)
    var isObstacleAware = true
        set(value) {
            field = value
            tracker?.updateObstacleAware()
        }

    private var tracker: CharacterTrackingCamRig? = null

    private val capturePointerHandler = InputStack.InputHandler("Character camera rig").apply {
        addKeyListener(KeyboardInput.KEY_ESC, "Stop capturing pointer") {
            tracker?.isCursorLocked = false
        }
        pointerListeners += { ev, _ ->
            tracker?.let { charCam ->
                if (!charCam.isCursorLocked &&
                    !ev.primaryPointer.isConsumed() &&
                    ev.primaryPointer.isLeftButtonClicked &&
                    ev.primaryPointer.leftButtonRepeatedClickCount == 2
                ) {
                    charCam.isCursorLocked = true
                }
            }
        }
    }

    override fun onStart() {
        val character = trackedCharacter ?: return
        val controller = character.charController ?: return

        InputStack.pushTop(capturePointerHandler)

        tracker = CharacterTrackingCamRig().apply {
            trackedPose = controller.actor.transform
            pivotPoint.set(this@CharacterCameraRig.pivotPoint)
            updateObstacleAware()
            gameEntity.replaceDrawNode(this)
            transform.setIdentity()
            children.find { it is Camera }?.transform?.setIdentity()
            zoom = 6f

            onUpdate {
                character.referenceFrontDirection = frontAngle
            }

            onRelease {
                InputStack.remove(capturePointerHandler)
            }
        }
    }

    private fun CharacterTrackingCamRig.updateObstacleAware() {
        if (isObstacleAware) {
            gameEntity.sceneEntity.getComponent<PhysicsWorldComponent>()?.physicsWorld?.let {
                setupCollisionAwareCamZoom(it)
            }
        } else {
            zoomModifier = { it }
        }
    }
}