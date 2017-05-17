package silverlion.com.house.commous;

import android.support.annotation.NonNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式工具，用来校验账号和密码等是否符合基本规则
 */
public class RegexUtils {

    public static final int VERIFY_SUCCESS = 0;

    private static final int VERIFY_LENGTH_ERROR = 1;

    private static final int VERIFY_TYPE_ERROR = 2;


    /**
     * 账号为中文，字母或数字，长度为4~20，一个中文算2个长度
     *
     * @param username 账号
     * @return {@link #VERIFY_SUCCESS}, {@link #VERIFY_TYPE_ERROR}, {@link #VERIFY_LENGTH_ERROR}
     */
    public static int verifyUsername(@NonNull String username) {

        int length = countLength(username);

        if (length < 4 || length > 20) {
            return VERIFY_LENGTH_ERROR;
        }

        String regex = "^[a-zA-Z0-9\u4E00-\u9FA5]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(username);

        if (!matcher.matches()) return VERIFY_TYPE_ERROR;
        return VERIFY_SUCCESS;
    }

    /**
     * 长度在6~18之间，只能包含字符、数字和下划线
     *
     * @param password 密码
     * @return {@link #VERIFY_SUCCESS}, {@link #VERIFY_TYPE_ERROR}, {@link #VERIFY_LENGTH_ERROR}
     */
    public static int verifyPassword(@NonNull String password) {

        int length = password.length();
        if (length < 6 || length > 12) {
            return VERIFY_LENGTH_ERROR;
        }

        String regex = "^\\w+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);

        if (!matcher.matches()) return VERIFY_TYPE_ERROR;
        return VERIFY_SUCCESS;
    }

    /**
     * 将字符串中所有的非标准字符（双字节字符）替换成两个标准字符（**）。
     * 然后再获取长度。
     */
    private static int countLength(@NonNull  String string) {
        string = string.replaceAll("[^\\x00-\\xff]", "**");
        return string.length();
    }

    // 判断是否为手机号
    public static boolean isPhone(String inputText) {
        Pattern p = Pattern
                .compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(inputText);
        return m.matches();
    }

    // 判断格式是否为email
    public static  boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

}
