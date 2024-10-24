package good.damn.sav.misc.utils;

public final class VEUtilsIntJava {

    public static byte[] int32(
      final int n
    ) {
        return new byte[] {
          (byte) ((n >> 24) & 0xff),
          (byte) ((n >> 16) & 0xff),
          (byte) ((n >> 8) & 0xff),
          (byte) (n & 0xff),
        };
    }

    public static int int32(
      final byte[] data
    ) {
        return int32(
          data,
          0
        );
    }
    public static int int32(
      final byte[] data,
      final int offset
    ) {
        return (data[offset] & 0xff) << 24 |
          (data[offset+1] & 0xff) << 16 |
          (data[offset+2] & 0xff) << 8 |
          (data[offset+3] & 0xff);
    }

}
