package good.damn.sav.misc.utils;

public final class VEUtilsByteArrayJava {

    static {
        System.loadLibrary("VEByteArrayUtilsCpp");
    }

    public static native void interpolate(
            byte[] v,
            byte[] vv,
            float t,
            byte[] to
    );
}
