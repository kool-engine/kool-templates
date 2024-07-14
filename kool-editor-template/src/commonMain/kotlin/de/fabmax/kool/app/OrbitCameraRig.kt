package de.fabmax.kool.app

import de.fabmax.kool.editor.api.EditorInfo
import de.fabmax.kool.editor.api.EditorRange
import de.fabmax.kool.editor.api.KoolBehavior
import de.fabmax.kool.editor.api.sceneComponent
import de.fabmax.kool.editor.components.CameraComponent
import de.fabmax.kool.input.InputStack
import de.fabmax.kool.scene.OrbitInputTransform

class OrbitCameraRig : KoolBehavior() {

    @EditorInfo(label = "Camera:", order = 1)
    var camera: CameraComponent? = null

    @EditorInfo("Zoom:", order = 2)
    @EditorRange(minX = 0.1)
    var zoom = 50.0
        set(value) {
            field = value
            setupOrbitCam()
        }
    @EditorInfo("Min zoom:", order = 3)
    @EditorRange(minX = 0.1)
    var minZoom = 1.0
        set(value) {
            field = value
            setupOrbitCam()
        }
    @EditorInfo("Max zoom:", order = 4)
    @EditorRange(minX = 0.1)
    var maxZoom = 100.0
        set(value) {
            field = value
            setupOrbitCam()
        }

    @EditorInfo("Yaw:", order = 5)
    @EditorRange(minX = -180.0, maxX = 180.0)
    var initYaw = 20.0
        set(value) {
            field = value
            setupOrbitCam()
        }

    @EditorInfo("Pitch:", order = 6)
    @EditorRange(minX = -90.0, maxX = 90.0)
    var initPitch = -30.0
        set(value) {
            field = value
            setupOrbitCam()
        }
    @EditorInfo("Min pitch:", order = 7)
    @EditorRange(minX = -90.0, maxX = 90.0)
    var minPitch = -90.0
        set(value) {
            field = value
            setupOrbitCam()
        }
    @EditorInfo("Max pitch:", order = 8)
    @EditorRange(minX = -90.0, maxX = 90.0)
    var maxPitch = 90.0
        set(value) {
            field = value
            setupOrbitCam()
        }

    private var orbitCam: OrbitInputTransform? = null

    override fun onStart() {
        val camera = camera ?: return

        orbitCam = OrbitInputTransform(gameEntity.name).also { orbitCam ->
            orbitCam.isKeepingStandardTransform = true

            camera.attachCameraToNode(orbitCam)
            camera.camera.transform.setIdentity().translate(0f, 0f, 1f)
            scene.sceneComponent.sceneNode.addNode(orbitCam)

            InputStack.defaultInputHandler.pointerListeners += orbitCam
            orbitCam.onRelease { InputStack.defaultInputHandler.pointerListeners -= orbitCam }
        }
        setupOrbitCam()
    }

    private fun setupOrbitCam() {
        val orbitCam = this.orbitCam ?: return

        orbitCam.minZoom = minZoom
        orbitCam.maxZoom = maxZoom
        orbitCam.minHorizontalRot = minPitch
        orbitCam.maxHorizontalRot = maxPitch

        orbitCam.setZoom(zoom)
        orbitCam.setMouseRotation(initYaw, initPitch)
    }
}