package de.fabmax.kool.app

import de.fabmax.kool.editor.api.EditorInfo
import de.fabmax.kool.editor.api.GameEntity
import de.fabmax.kool.editor.api.KoolBehavior
import de.fabmax.kool.editor.components.RigidActorComponent
import de.fabmax.kool.math.Mat4f
import de.fabmax.kool.math.MutableVec3f
import de.fabmax.kool.math.clamp
import de.fabmax.kool.physics.RigidDynamic
import kotlin.math.min
import kotlin.math.sqrt

class PlatformMoveController : KoolBehavior() {

    @EditorInfo(label = "Start anchor:", order = 1)
    var anchorA: GameEntity? = null

    @EditorInfo(label = "End anchor:", order = 2)
    var anchorB: GameEntity? = null

    @EditorInfo(label = "Trigger:", order = 3)
    var trigger: TriggerHandler? = null

    @EditorInfo(label = "Movement speed:", order = 4)
    var speed = 1f

    private val actorComponent: RigidActorComponent? by lazy { gameEntity.getComponent() }

    private var sign = 1f
    private val posA = MutableVec3f()
    private val posB = MutableVec3f()
    private var progress = 0f

    override fun onPhysicsUpdate(timeStep: Float) {
        val actor = actorComponent?.rigidActor as? RigidDynamic ?: return
        anchorA?.drawNode?.modelMatF?.getTranslation(posA) ?: return
        anchorB?.drawNode?.modelMatF?.getTranslation(posB) ?: return

        val delta = posB - posA
        val dist = delta.length()
        val dir = delta.normed()

        val triggerActivity = trigger?.activity ?: 0f
        val ramp = speedRamp(min(progress, dist - progress))
        progress += speed * ramp * triggerActivity * timeStep * sign

        if (progress >= dist) {
            progress = dist
            sign = -sign
        }
        if (progress <= 0f) {
            progress = 0f
            sign = -sign
        }

        val pose = Mat4f.translation(posA + dir * progress)
        actor.setKinematicTarget(pose)
    }

    private fun speedRamp(dist: Float): Float {
        val d = 2f
        if (dist > d) return 1f
        return sqrt(dist / d + 0.02f).clamp()
    }
}