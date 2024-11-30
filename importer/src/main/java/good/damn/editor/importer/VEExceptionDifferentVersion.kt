package good.damn.editor.importer

class VEExceptionDifferentVersion(
    fileVersion: Int,
    importerVersion: Int
): Exception(
    "Import version=$importerVersion; File Version=$fileVersion; To avoid exception, it should FileVersion == importerVersion"
)