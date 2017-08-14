import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

public class Util {
    private static String key;

    private static Util instance;

    private Util() {
    }

    public static Util getInstance() {
        if (instance == null) {
            instance = new Util();
        }
        return instance;
    }

    public void setKey(String k) {
        key = k;
    }

    private final Random random = new Random();

    private final String[] LEGAL_EXTENSION = new String[]{"js", "txt"};

    private enum SecurityOperation {
        ENCODE,
        DECODE
    }


    public String getPassword(int len) {
        int index;  //生成的随机数
        int count = 0; //生成的密码的长度
        // 密码字典
        char[] str = "ABCDEFGHIJKLMNPQRSTUVWXYZabcdefghijkmnpqrstuvwxyz23456789~!@#$%^&*()_+-=[]\\{}|;':\",./<>?".toCharArray();
        String password = "";
        while (count < len) {
            //生成 0 ~ 密码字典-1之间的随机数
            index = random.nextInt(str.length);
            password += str[index];
            ++count;
        }
        return password;
    }

    public String getPasswordRandomLen() {
        int max = 20,
                min = 10;
        int len = random.nextInt(max) % (max - min + 1) + min;
        return getPassword(len);
    }

    public String encode(String str) {
        return Base64.encodeBase64String(xorWithKey(str.getBytes(StandardCharsets.UTF_8), key.getBytes(StandardCharsets.UTF_8)));
    }

    public String decode(String str) {
        return new String(xorWithKey(Base64.decodeBase64(str), key.getBytes(StandardCharsets.UTF_8)));
    }

    private void securityOperation(String filePath, boolean cover, SecurityOperation operation) throws Exception {
        File file = FileUtils.getFile(filePath);
        Collection<File> files = null;
        if (file.isDirectory()) {
            files = FileUtils.listFiles(file, LEGAL_EXTENSION, true);
        } else if (file.isFile()) {
            files = Arrays.asList(file);
        }
        for (File f : files) {
            String fileString = FileUtils.readFileToString(f, StandardCharsets.UTF_8);
            final boolean isBase64 = Base64.isBase64(fileString);
            // 防止一个文件多次被编码
            if (isBase64 && operation == SecurityOperation.ENCODE) {
                System.out.println(f.getName() + "是Base64编码，忽略加密操作...");
            } else if (!isBase64 && operation == SecurityOperation.DECODE) {
                System.out.println(f.getName() + "是明文文件，忽略解密操作...");
            } else {
                String transformString;
                switch (operation) {
                    case DECODE:
                        transformString = decode(fileString);
                        break;
                    case ENCODE:
                        transformString = encode(fileString);
                        break;
                    default:
                        throw new Exception("not have this security operation");
                }
                if (cover) {
                    FileUtils.writeStringToFile(f, transformString, StandardCharsets.UTF_8);
                } else {
                    String bakFilePath = f.getAbsolutePath() + ".bak";
                    File bakFile = FileUtils.getFile(bakFilePath);
                    FileUtils.writeStringToFile(bakFile, transformString, StandardCharsets.UTF_8);
                }
            }
        }
    }

    public void decode(String[] filePaths, boolean cover) throws Exception {
        for (String filePath : filePaths) {
            securityOperation(filePath, cover, SecurityOperation.DECODE);
        }
    }

    public void encode(String[] filePaths, boolean cover) throws Exception {
        for (String filePath : filePaths) {
            securityOperation(filePath, cover, SecurityOperation.ENCODE);
        }
    }

    private byte[] xorWithKey(byte[] a, byte[] key) {
        byte[] out = new byte[a.length];
        for (int i = 0; i < a.length; i++) {
            out[i] = (byte) (a[i] ^ key[i % key.length]);
        }
        return out;
    }
}
