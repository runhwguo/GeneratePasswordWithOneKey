import org.apache.commons.codec.binary.Base64;

import java.nio.charset.StandardCharsets;
import java.util.Random;

public class Util {
    public static final String KEY = "211228";

    private static final Random random = new Random();


    public static String getPassword(int len) {
        int index;  //生成的随机数
        int count = 0; //生成的密码的长度
        // 密码字典
        char[] str = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~!@#$%^&*()_+-=[]\\{}|;':\",./<>?".toCharArray();
        String password = "";
        while (count < len) {
            //生成 0 ~ 密码字典-1之间的随机数
            index = random.nextInt(str.length);
            password += str[index];
            ++count;
        }
        return password;
    }

    public static String getPasswordRandomLen() {
        int max = 20,
                min = 10;
        int len = random.nextInt(max) % (max - min + 1) + min;
        return getPassword(len);
    }

    public static String encode(String s) {
        return Base64.encodeBase64String(xorWithKey(s.getBytes(StandardCharsets.UTF_8), KEY.getBytes(StandardCharsets.UTF_8)));
    }

    public static String decode(String s) {
        return new String(xorWithKey(Base64.decodeBase64(s), KEY.getBytes(StandardCharsets.UTF_8)));
    }

    private static byte[] xorWithKey(byte[] a, byte[] key) {
        byte[] out = new byte[a.length];
        for (int i = 0; i < a.length; i++) {
            out[i] = (byte) (a[i] ^ key[i % key.length]);
        }
        return out;
    }
}
