package de.fabmax.kool.app

import de.fabmax.kool.editor.api.EditorInfo
import de.fabmax.kool.editor.api.KoolBehavior
import de.fabmax.kool.editor.components.TransformComponent
import de.fabmax.kool.editor.data.Vec4Data
import de.fabmax.kool.math.MutableMat3f
import de.fabmax.kool.math.MutableQuatF
import de.fabmax.kool.math.Vec3f
import de.fabmax.kool.math.deg
import de.fabmax.kool.util.Time

class SampleRotationAnimator : KoolBehavior() {

    @EditorInfo("Rotation speed:", -360.0, 360.0)
    var rotationSpeed = Vec3f(17f, 31f, 19f)

    @EditorInfo("Speed multiplier:", -10.0, 10.0)
    var speedMulti = 1f

    private lateinit var transform: TransformComponent

    override fun onInit() {
        transform = node.getComponent<TransformComponent>()!!
    }

    override fun onUpdate() {
        val transformData = transform.transformState.value

        val rot = MutableMat3f().rotate(transformData.rotation.toQuatF())
        rot.rotate(
            rotationSpeed.x.deg * Time.deltaT * speedMulti,
            rotationSpeed.y.deg * Time.deltaT * speedMulti,
            rotationSpeed.z.deg * Time.deltaT * speedMulti
        )
        val quat = rot.getRotation(MutableQuatF())
        transform.transformState.set(transformData.copy(rotation = Vec4Data(quat)))
    }
}