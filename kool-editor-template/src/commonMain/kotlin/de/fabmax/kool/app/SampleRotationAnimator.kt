package de.fabmax.kool.app

import de.fabmax.kool.editor.api.EditorInfo
import de.fabmax.kool.editor.api.KoolBehavior
import de.fabmax.kool.editor.components.TransformComponent
import de.fabmax.kool.editor.data.Vec4Data
import de.fabmax.kool.math.MutableMat3d
import de.fabmax.kool.math.MutableQuatD
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
        val rot = MutableMat3d().rotate(transformData.rotation.toQuatD())
        rot.rotate(
            (rotationSpeed.x.toDouble() * speedMulti * Time.deltaT).deg,
            (rotationSpeed.y.toDouble() * speedMulti * Time.deltaT).deg,
            (rotationSpeed.z.toDouble() * speedMulti * Time.deltaT).deg
        )
        val quat = rot.getRotation(MutableQuatD())
        transform.transformState.set(transformData.copy(rotation = Vec4Data(quat)))
    }
}