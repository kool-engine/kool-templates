package de.fabmax.kool.app

import de.fabmax.kool.editor.api.EditorHidden
import de.fabmax.kool.editor.api.EditorInfo
import de.fabmax.kool.editor.api.KoolBehavior
import de.fabmax.kool.editor.components.MaterialReferenceComponent
import de.fabmax.kool.editor.components.RigidActorComponent
import de.fabmax.kool.math.MutableVec3f
import de.fabmax.kool.math.Vec3f
import de.fabmax.kool.math.deg
import de.fabmax.kool.math.expDecay
import de.fabmax.kool.physics.RigidActor
import de.fabmax.kool.physics.TriggerListener
import de.fabmax.kool.pipeline.RenderPass
import de.fabmax.kool.scene.TrsTransformF
import de.fabmax.kool.scene.set
import de.fabmax.kool.util.Time

class TriggerHandler : KoolBehavior() {

    @EditorInfo(label = "Material name:", order = 1)
    var materialNameFilter = ""

    @EditorInfo(label = "Idle speed:", order = 2)
    var rotationSpeedIdle = 90f

    @EditorInfo(label = "Active speed:", order = 3)
    var rotationSpeedActive = 360f

    @EditorHidden
    var activity = 0f

    private val actorComponent: RigidActorComponent? by lazy { gameEntity.getComponent() }

    private val rotationAxis = Vec3f(0f, 1f, 0f).normed()
    private val initPos = MutableVec3f()
    private val animatedTransform = TrsTransformF()
    private var triggerCount = 0

    private val triggerListener = object : TriggerListener {
        val RigidActor.isMatching: Boolean
            get() = materialNameFilter.isEmpty() || materialNameFilter == gameEntity?.getComponent<MaterialReferenceComponent>()?.material?.name

        override fun onActorEntered(trigger: RigidActor, actor: RigidActor) {
            if (actor.isMatching) {
                triggerCount++
            }
        }

        override fun onActorExited(trigger: RigidActor, actor: RigidActor) {
            if (actor.isMatching) {
                triggerCount = (triggerCount - 1).coerceAtLeast(0)
            }
        }
    }

    override fun onInit() {
        animatedTransform.set(gameEntity.transform.transform)
        gameEntity.transform.transform = animatedTransform
        actorComponent?.addTriggerListener(triggerListener)
        initPos.set(animatedTransform.translation)
    }

    override fun onUpdate(ev: RenderPass.UpdateEvent) {
        val targetActivity = if (triggerCount > 0) 1f else 0f
        activity = activity.expDecay(targetActivity, 5f)

        val rotSpeed = rotationSpeedActive * activity + rotationSpeedIdle * (1f - activity)
        animatedTransform.translation.set(initPos + Vec3f(0f, activity, 0f))
        animatedTransform.rotate(rotSpeed.deg * Time.deltaT, rotationAxis)
    }
}