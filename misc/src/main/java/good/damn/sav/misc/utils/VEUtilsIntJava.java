package good.damn.sav.misc.utils;

public final class VEUtilsIntJava {

    static {
        System.loadLibrary(
            "VEUtilsIntCpp"
        );
    }

    public static int int32(
         final byte[] data
    ) {
        return int32(
            data,
          0
        );
    }

    public static native byte[] int32(
        final int n
    );

    public static native int int32(
        final byte[] data,
        int offset
    );
}
