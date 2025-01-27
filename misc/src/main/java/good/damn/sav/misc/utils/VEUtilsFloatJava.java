package good.damn.sav.misc.utils;

public final class VEUtilsFloatJava {

    static {
        System.loadLibrary(
            "VEUtilsFloat"
        );
    }

    public static native byte getDigitalFraction(
        float fraction
    );

    public static native byte toDigitalFraction(
        float fraction,
        float divider
    );

}
