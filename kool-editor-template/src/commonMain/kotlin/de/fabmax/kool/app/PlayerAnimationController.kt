package de.fabmax.kool.app

import de.fabmax.kool.editor.api.KoolBehavior
import de.fabmax.kool.editor.components.CharacterControllerComponent
import de.fabmax.kool.editor.components.MeshComponent
import de.fabmax.kool.math.MutableQuatF
import de.fabmax.kool.math.QuatF
import de.fabmax.kool.math.Vec3f
import de.fabmax.kool.math.clamp
import de.fabmax.kool.pipeline.RenderPass
import de.fabmax.kool.scene.Model
import de.fabmax.kool.scene.TrsTransformF
import kotlin.math.abs

class PlayerAnimationController : KoolBehavior() {

    var character: CharacterControllerComponent? = null

    private var initOrientation = QuatF.IDENTITY
    private val meshComponent by lazy { gameEntity.getComponent<MeshComponent>() }

    override fun onStart() {
        val rot = MutableQuatF()
        transform.decompose(rotation = rot)
        initOrientation = rot
    }

    override fun onUpdate(ev: RenderPass.UpdateEvent) {
        val char = character ?: return
        val model = meshComponent?.sceneNode as? Model ?: return

        val transform = transform as TrsTransformF
        transform.rotation.set(initOrientation).rotate(char.moveHeading, Vec3f.Y_AXIS)
        transform.markDirty()

        updateAnimation(model, char)
    }

    private fun updateAnimation(model: Model, character: CharacterControllerComponent) {
        // determine which animation to use based on speed
        val walkSpeed = character.data.walkSpeed
        val runSpeed = character.data.runSpeed

        if (abs(character.moveSpeed) <= walkSpeed) {
            val w = (abs(character.moveSpeed) / walkSpeed)
            model.setAnimationWeight(WALK_ANIMATION, w)
            model.setAnimationWeight(IDLE_ANIMATION, 1f - w)
            model.setAnimationWeight(RUN_ANIMATION, 0f)

        } else {
            val w = ((abs(character.moveSpeed) - walkSpeed) / (runSpeed - walkSpeed)).clamp(0f, 1f)
            model.setAnimationWeight(RUN_ANIMATION, w)
            model.setAnimationWeight(WALK_ANIMATION, 1f - w)
            model.setAnimationWeight(IDLE_ANIMATION, 0f)
        }
    }

    companion object {
        // hard coded model animation indices, depend on the actual model
        private const val IDLE_ANIMATION = 0
        private const val RUN_ANIMATION = 1
        private const val WALK_ANIMATION = 2
    }
}
