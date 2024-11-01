package good.damn.sav.misc.utils;

public final class VEUtilsByteArrayJava {

    public static void interpolate(
            byte[] v,
            byte[] vv,
            float t,
            byte[] to
    ) {
        if (!(v.length == vv.length || v.length == to.length)) {
            return;
        }

        for (int i = 0; i < v.length; i++) {
            int valFrom = v[i] & 0xff;
            int valTo = vv[i] & 0xff;
            to[i] = (byte) (
                valFrom + (valTo - valFrom) * t
            );
        }
    }

}
