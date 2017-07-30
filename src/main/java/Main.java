import org.apache.commons.codec.DecoderException;

public class Main {
    public static void main(String[] args) throws DecoderException {
        String passwordRandom = Util.getPasswordRandomLen();
        System.out.println("随机生成的密码：\n"+passwordRandom);
        String encrypt = Util.encode(passwordRandom);
        System.out.println("加密后可传输存储的密码：\n"+encrypt);
        System.out.println("解密后的密码：\n"+Util.decode(encrypt));
    }
}
