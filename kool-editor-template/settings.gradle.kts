rootProject.name = "kool-editor-template"

// include kool-libs in source to the build (requires kool-libs to be checked out at that location)
if (File("../../kool").exists()) {
    includeBuild("../../kool")
}
