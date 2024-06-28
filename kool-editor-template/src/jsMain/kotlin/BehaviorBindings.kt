import de.fabmax.kool.app.*
import de.fabmax.kool.editor.AppBehavior
import de.fabmax.kool.editor.BehaviorProperty
import de.fabmax.kool.editor.BehaviorPropertyType
import de.fabmax.kool.editor.api.BehaviorLoader
import de.fabmax.kool.editor.api.KoolBehavior
import de.fabmax.kool.math.Vec4d
import kotlin.reflect.KClass
import kotlin.reflect.typeOf

// GENERATED FILE! Do not edit manually ////////////////////////////

object BehaviorBindings : BehaviorLoader.AppBehaviorLoader {
    override fun newInstance(behaviorClassName: String): KoolBehavior {
        return when (behaviorClassName) {
            "de.fabmax.kool.app.CharacterCameraRig" -> CharacterCameraRig()
            "de.fabmax.kool.app.OrbitCameraRig" -> OrbitCameraRig()
            "de.fabmax.kool.app.PlatformMoveController" -> PlatformMoveController()
            "de.fabmax.kool.app.PlayerAnimationController" -> PlayerAnimationController()
            "de.fabmax.kool.app.TriggerHandler" -> TriggerHandler()
            else -> error("$behaviorClassName not mapped.")
        }
    }

    override fun getProperty(behavior: KoolBehavior, propertyName: String): Any? {
        return when (behavior) {
            is CharacterCameraRig -> getCharacterCameraRigProperty(behavior, propertyName)
            is OrbitCameraRig -> getOrbitCameraRigProperty(behavior, propertyName)
            is PlatformMoveController -> getPlatformMoveControllerProperty(behavior, propertyName)
            is PlayerAnimationController -> getPlayerAnimationControllerProperty(behavior, propertyName)
            is TriggerHandler -> getTriggerHandlerProperty(behavior, propertyName)
            else -> error("Unknown behavior class: ${behavior::class}")
        }
    }

    override fun setProperty(behavior: KoolBehavior, propertyName: String, value: Any?) {
        when (behavior) {
            is CharacterCameraRig -> setCharacterCameraRigProperty(behavior, propertyName, value)
            is OrbitCameraRig -> setOrbitCameraRigProperty(behavior, propertyName, value)
            is PlatformMoveController -> setPlatformMoveControllerProperty(behavior, propertyName, value)
            is PlayerAnimationController -> setPlayerAnimationControllerProperty(behavior, propertyName, value)
            is TriggerHandler -> setTriggerHandlerProperty(behavior, propertyName, value)
            else -> error("Unknown behavior class: ${behavior::class}")
        }
    }

    private fun getCharacterCameraRigProperty(behavior: CharacterCameraRig, propertyName: String): Any? {
        return when (propertyName) {
            "trackedCharacter" -> behavior.trackedCharacter
            "pivotPoint" -> behavior.pivotPoint
            "isObstacleAware" -> behavior.isObstacleAware
            else -> error("Unknown parameter $propertyName for behavior class ${behavior::class}")
        }
    }

    private fun getOrbitCameraRigProperty(behavior: OrbitCameraRig, propertyName: String): Any {
        return when (propertyName) {
            "zoom" -> behavior.zoom
            "minZoom" -> behavior.minZoom
            "maxZoom" -> behavior.maxZoom
            "initYaw" -> behavior.initYaw
            "initPitch" -> behavior.initPitch
            "minPitch" -> behavior.minPitch
            "maxPitch" -> behavior.maxPitch
            else -> error("Unknown parameter $propertyName for behavior class ${behavior::class}")
        }
    }

    private fun getPlatformMoveControllerProperty(behavior: PlatformMoveController, propertyName: String): Any? {
        return when (propertyName) {
            "anchorA" -> behavior.anchorA
            "anchorB" -> behavior.anchorB
            "trigger" -> behavior.trigger
            "speed" -> behavior.speed
            else -> error("Unknown parameter $propertyName for behavior class ${behavior::class}")
        }
    }

    private fun getPlayerAnimationControllerProperty(behavior: PlayerAnimationController, propertyName: String): Any? {
        return when (propertyName) {
            "character" -> behavior.character
            else -> error("Unknown parameter $propertyName for behavior class ${behavior::class}")
        }
    }

    private fun getTriggerHandlerProperty(behavior: TriggerHandler, propertyName: String): Any {
        return when (propertyName) {
            "materialNameFilter" -> behavior.materialNameFilter
            "rotationSpeedIdle" -> behavior.rotationSpeedIdle
            "rotationSpeedActive" -> behavior.rotationSpeedActive
            else -> error("Unknown parameter $propertyName for behavior class ${behavior::class}")
        }
    }

    private fun setCharacterCameraRigProperty(behavior: CharacterCameraRig, propertyName: String, value: Any?) {
        when (propertyName) {
            "trackedCharacter" -> behavior.trackedCharacter = value as de.fabmax.kool.editor.components.CharacterControllerComponent?
            "pivotPoint" -> behavior.pivotPoint = value as de.fabmax.kool.math.Vec3f
            "isObstacleAware" -> behavior.isObstacleAware = value as Boolean
            else -> error("Unknown parameter $propertyName for behavior class ${behavior::class}")
        }
    }

    private fun setOrbitCameraRigProperty(behavior: OrbitCameraRig, propertyName: String, value: Any?) {
        when (propertyName) {
            "zoom" -> behavior.zoom = value as Double
            "minZoom" -> behavior.minZoom = value as Double
            "maxZoom" -> behavior.maxZoom = value as Double
            "initYaw" -> behavior.initYaw = value as Double
            "initPitch" -> behavior.initPitch = value as Double
            "minPitch" -> behavior.minPitch = value as Double
            "maxPitch" -> behavior.maxPitch = value as Double
            else -> error("Unknown parameter $propertyName for behavior class ${behavior::class}")
        }
    }

    private fun setPlatformMoveControllerProperty(behavior: PlatformMoveController, propertyName: String, value: Any?) {
        when (propertyName) {
            "anchorA" -> behavior.anchorA = value as de.fabmax.kool.editor.api.GameEntity?
            "anchorB" -> behavior.anchorB = value as de.fabmax.kool.editor.api.GameEntity?
            "trigger" -> behavior.trigger = value as de.fabmax.kool.app.TriggerHandler?
            "speed" -> behavior.speed = value as Float
            else -> error("Unknown parameter $propertyName for behavior class ${behavior::class}")
        }
    }

    private fun setPlayerAnimationControllerProperty(behavior: PlayerAnimationController, propertyName: String, value: Any?) {
        when (propertyName) {
            "character" -> behavior.character = value as de.fabmax.kool.editor.components.CharacterControllerComponent?
            else -> error("Unknown parameter $propertyName for behavior class ${behavior::class}")
        }
    }

    private fun setTriggerHandlerProperty(behavior: TriggerHandler, propertyName: String, value: Any?) {
        when (propertyName) {
            "materialNameFilter" -> behavior.materialNameFilter = value as String
            "rotationSpeedIdle" -> behavior.rotationSpeedIdle = value as Float
            "rotationSpeedActive" -> behavior.rotationSpeedActive = value as Float
            else -> error("Unknown parameter $propertyName for behavior class ${behavior::class}")
        }
    }

    val behaviorClasses = mapOf<KClass<*>, AppBehavior>(
        CharacterCameraRig::class to AppBehavior(
            simpleName = "CharacterCameraRig",
            qualifiedName = "de.fabmax.kool.app.CharacterCameraRig",
            properties = listOf(
                BehaviorProperty("trackedCharacter", BehaviorPropertyType.COMPONENT, typeOf<de.fabmax.kool.editor.components.CharacterControllerComponent?>(), "Character:"),
                BehaviorProperty("pivotPoint", BehaviorPropertyType.STD, typeOf<de.fabmax.kool.math.Vec3f>(), "Pivot point:"),
                BehaviorProperty("isObstacleAware", BehaviorPropertyType.STD, typeOf<Boolean>(), "Obstacle aware camera:"),
            )
        ),
        OrbitCameraRig::class to AppBehavior(
            simpleName = "OrbitCameraRig",
            qualifiedName = "de.fabmax.kool.app.OrbitCameraRig",
            properties = listOf(
                BehaviorProperty("zoom", BehaviorPropertyType.STD, typeOf<Double>(), "Zoom:", min = Vec4d(0.1, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY), max = Vec4d(0.1, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY)),
                BehaviorProperty("minZoom", BehaviorPropertyType.STD, typeOf<Double>(), "Min zoom:", min = Vec4d(0.1, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY), max = Vec4d(0.1, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY)),
                BehaviorProperty("maxZoom", BehaviorPropertyType.STD, typeOf<Double>(), "Max zoom:", min = Vec4d(0.1, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY), max = Vec4d(0.1, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY)),
                BehaviorProperty("initYaw", BehaviorPropertyType.STD, typeOf<Double>(), "Yaw:", min = Vec4d(-180.0, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY), max = Vec4d(-180.0, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY)),
                BehaviorProperty("initPitch", BehaviorPropertyType.STD, typeOf<Double>(), "Pitch:", min = Vec4d(-90.0, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY), max = Vec4d(-90.0, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY)),
                BehaviorProperty("minPitch", BehaviorPropertyType.STD, typeOf<Double>(), "Min pitch:", min = Vec4d(-90.0, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY), max = Vec4d(-90.0, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY)),
                BehaviorProperty("maxPitch", BehaviorPropertyType.STD, typeOf<Double>(), "Max pitch:", min = Vec4d(-90.0, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY), max = Vec4d(-90.0, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY)),
            )
        ),
        PlatformMoveController::class to AppBehavior(
            simpleName = "PlatformMoveController",
            qualifiedName = "de.fabmax.kool.app.PlatformMoveController",
            properties = listOf(
                BehaviorProperty("anchorA", BehaviorPropertyType.STD, typeOf<de.fabmax.kool.editor.api.GameEntity?>(), "Start anchor:"),
                BehaviorProperty("anchorB", BehaviorPropertyType.STD, typeOf<de.fabmax.kool.editor.api.GameEntity?>(), "End anchor:"),
                BehaviorProperty("trigger", BehaviorPropertyType.BEHAVIOR, typeOf<de.fabmax.kool.app.TriggerHandler?>(), "Trigger:"),
                BehaviorProperty("speed", BehaviorPropertyType.STD, typeOf<Float>(), "Movement speed:"),
            )
        ),
        PlayerAnimationController::class to AppBehavior(
            simpleName = "PlayerAnimationController",
            qualifiedName = "de.fabmax.kool.app.PlayerAnimationController",
            properties = listOf(
                BehaviorProperty("character", BehaviorPropertyType.COMPONENT, typeOf<de.fabmax.kool.editor.components.CharacterControllerComponent?>(), "Character"),
            )
        ),
        TriggerHandler::class to AppBehavior(
            simpleName = "TriggerHandler",
            qualifiedName = "de.fabmax.kool.app.TriggerHandler",
            properties = listOf(
                BehaviorProperty("materialNameFilter", BehaviorPropertyType.STD, typeOf<String>(), "Material name:"),
                BehaviorProperty("rotationSpeedIdle", BehaviorPropertyType.STD, typeOf<Float>(), "Idle speed:"),
                BehaviorProperty("rotationSpeedActive", BehaviorPropertyType.STD, typeOf<Float>(), "Active speed:"),
            )
        ),
    )
}
