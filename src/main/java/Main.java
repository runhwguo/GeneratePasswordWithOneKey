import java.io.Console;
import java.util.Scanner;

public class Main {
    private static final String SPLIT_STRING = "\\s+";

    public static void main(String[] args) throws Exception {
        Util util = Util.getInstance();

        String menu = "1.随机生成密码\n" +
                "2.随机生成指定长度密码\n" +
                "3.对称加密字符串\n" +
                "4.对称解密字符串\n" +
                "5.对称加密文件目录\n" +
                "6.对称解密文件目录\n" +
                "7.退出\n";
        System.out.println(menu);
        Scanner scanner = new Scanner(System.in);

        System.out.println("请选择操作项：");
        int menuNum = scanner.nextInt();
        scanner.nextLine();
        if (menuNum >= 3 && menuNum <= 6) {
            Console console = System.console();
            if (console == null) {
                System.out.println("Console object is not available, should in Console");
                return;
            }
            char[] securityKey = console.readPassword("请输入密钥：");
            System.out.println(securityKey);
            util.setKey(new String(securityKey));
        }
        String input;
        switch (menuNum) {
            case 1:
                System.out.println(util.getPasswordRandomLen());
                break;
            case 2:
                System.out.println("请输入密码长度：");
                System.out.println(util.getPassword(scanner.nextInt()));
                break;
            case 3:
                System.out.println("请输入字符串：");
                System.out.println(util.encode(scanner.nextLine()));
                break;
            case 4:
                System.out.println("请输入字符串：");
                System.out.println(util.decode(scanner.nextLine()));
                break;
            case 5:
                System.out.println("请输入文件or目录：");
                input = scanner.nextLine();

                util.encode(input.trim().split(SPLIT_STRING), true);
                break;
            case 6:
                System.out.println("请输入文件or目录：");
                input = scanner.nextLine();
                util.decode(input.trim().split(SPLIT_STRING), true);
                break;
            case 7:
                break;
            default:
                System.out.println("没有该选项");
                break;
        }

        scanner.close();
    }
}
