import java.util.Scanner;

public class Main {
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
        String securityKey;
        if (menuNum >= 3 && menuNum <= 6) {
            System.out.println("请输入密钥：");
            securityKey = scanner.nextLine();
            util.setKey(securityKey);
        }
        switch (menuNum) {
            case 1:
                System.out.println(util.getPasswordRandomLen());
                break;
            case 2:
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
                System.out.println("请输入文件目录：");
                util.encode(scanner.nextLine(), true);
                break;
            case 6:
                System.out.println("请输入文件目录：");
                util.decode(scanner.nextLine(), true);
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
