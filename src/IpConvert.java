/**
 * 将ip转换成long，将long转成ip <br>
 *
 *     因为每个点分隔格式的 IP 地址是以 256 位方式表示的数字： 192.168.0.1 意指 1*256^0 + 0*256^1 + 168*256^2 + 192*256^3 = 3232235521
 *
 * Created by lingshu on 15/10/10.
 */
public class IpConvert {
    public static int ipToInt(final String addr) {
        final String[] addressBytes = addr.split("\\.");
        int length = addressBytes.length;
        if (length < 3) {
            return 0;
        }
        int ip = 0;
        try {
            for (int i = 0; i < 4; i++) {
                ip <<= 8;
                ip |= Integer.parseInt(addressBytes[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ip;
    }

    public static String intToIp(int i) {
        return ((i >> 24 ) & 0xFF) + "." +
                ((i >> 16 ) & 0xFF) + "." +
                ((i >>  8 ) & 0xFF) + "." +
                ( i        & 0xFF);
    }

    public static void main(String[] args){
        String ip = "255.155.155.155";
        System.out.println(ipToInt(ip));
        System.out.println(intToIp(ipToInt(ip)));
    }
}
