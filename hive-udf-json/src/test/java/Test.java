import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @ClassName: Test
 * @Description: TODO
 * @Author chengfei
 * @Date 2020/1/9 17:33
 * @Version 1.0
 **/
public class Test {
    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now.toString());
        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss")));        // 2016-07-11T14:27:20.169
        LocalDateTime dateTime1 = LocalDateTime.of(1990, 1, 1, 12, 3);
        LocalDateTime dateTime2 = LocalDateTime.of(2000, 2, 4, 8, 4, 20);
        System.out.println(dateTime1);  // 1990-01-01T12:03
        System.out.println(dateTime2);  // 2000-02-04T08:04:20
    }
}
