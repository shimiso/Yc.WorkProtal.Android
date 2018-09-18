package com.yuecheng.workportal.utils;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import com.yuecheng.workportal.MainApplication;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    private static final String TAG = "StringUtil";
    private StringUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 判断字符串是否为null或长度为0
     *
     * @param s 待校验字符串
     * @return {@code true}: 空<br> {@code false}: 不为空
     */
    public static boolean isEmpty(CharSequence s) {
        return s == null || s.length() == 0;
    }

    /**
     * 判断字符串是否为null或全为空格
     *
     * @param s 待校验字符串
     * @return {@code true}: null或全空格<br> {@code false}: 不为null且不全空格
     */
    public static boolean isSpace(String s) {
        return (s == null || s.trim().length() == 0);
    }

    /**
     * 判断两字符串是否相等
     *
     * @param a 待校验字符串a
     * @param b 待校验字符串b
     * @return {@code true}: 相等<br>{@code false}: 不相等
     */
    public static boolean equals(CharSequence a, CharSequence b) {
        if (a == b) return true;
        int length;
        if (a != null && b != null && (length = a.length()) == b.length()) {
            if (a instanceof String && b instanceof String) {
                return a.equals(b);
            } else {
                for (int i = 0; i < length; i++) {
                    if (a.charAt(i) != b.charAt(i)) return false;
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 判断两字符串忽略大小写是否相等
     *
     * @param a 待校验字符串a
     * @param b 待校验字符串b
     * @return {@code true}: 相等<br>{@code false}: 不相等
     */
    public static boolean equalsIgnoreCase(String a, String b) {
        return (a == b) || (b != null) && (a.length() == b.length()) && a.regionMatches(true, 0, b, 0, b.length());
    }

    /**
     * null转为长度为0的字符串
     *
     * @param s 待转字符串
     * @return s为null转为长度为0字符串，否则不改变
     */
    public static String null2Length0(String s) {
        return s == null ? "" : s;
    }

    /**
     * 返回字符串长度
     *
     * @param s 字符串
     * @return null返回0，其他返回自身长度
     */
    public static int length(CharSequence s) {
        return s == null ? 0 : s.length();
    }

    /**
     * 首字母大写
     *
     * @param s 待转字符串
     * @return 首字母大写字符串
     */
    public static String upperFirstLetter(String s) {
        if (isEmpty(s) || !Character.isLowerCase(s.charAt(0))) return s;
        return String.valueOf((char) (s.charAt(0) - 32)) + s.substring(1);
    }

    /**
     * 首字母小写
     *
     * @param s 待转字符串
     * @return 首字母小写字符串
     */
    public static String lowerFirstLetter(String s) {
        if (isEmpty(s) || !Character.isUpperCase(s.charAt(0))) return s;
        return String.valueOf((char) (s.charAt(0) + 32)) + s.substring(1);
    }

    /**
     * 反转字符串
     *
     * @param s 待反转字符串
     * @return 反转字符串
     */
    public static String reverse(String s) {
        int len = length(s);
        if (len <= 1) return s;
        int mid = len >> 1;
        char[] chars = s.toCharArray();
        char c;
        for (int i = 0; i < mid; ++i) {
            c = chars[i];
            chars[i] = chars[len - i - 1];
            chars[len - i - 1] = c;
        }
        return new String(chars);
    }

    /**
     * 转化为半角字符
     *
     * @param s 待转字符串
     * @return 半角字符串
     */
    public static String toDBC(String s) {
        if (isEmpty(s)) return s;
        char[] chars = s.toCharArray();
        for (int i = 0, len = chars.length; i < len; i++) {
            if (chars[i] == 12288) {
                chars[i] = ' ';
            } else if (65281 <= chars[i] && chars[i] <= 65374) {
                chars[i] = (char) (chars[i] - 65248);
            } else {
                chars[i] = chars[i];
            }
        }
        return new String(chars);
    }

    /**
     * 转化为全角字符
     *
     * @param s 待转字符串
     * @return 全角字符串
     */
    public static String toSBC(String s) {
        if (isEmpty(s)) return s;
        char[] chars = s.toCharArray();
        for (int i = 0, len = chars.length; i < len; i++) {
            if (chars[i] == ' ') {
                chars[i] = (char) 12288;
            } else if (33 <= chars[i] && chars[i] <= 126) {
                chars[i] = (char) (chars[i] + 65248);
            } else {
                chars[i] = chars[i];
            }
        }
        return new String(chars);
    }


    /**
     * 获取 343 类型中间带*号的字符串
     *
     * @param nativeString
     *            原始字符串
     * @return 返回带*号的隐式字符串
     */
    public static String getThreeForThreeString(String nativeString) {
        if (nativeString.length() < 6) {
            // 长度小于6直接返回，避免越界
            return nativeString;
        }
        StringBuffer sbShow = new StringBuffer(nativeString.subSequence(0, 3));
        sbShow.append("*****");
        sbShow.append(nativeString.substring(nativeString.length() - 3, nativeString.length()));
        return sbShow.toString();
    }


    /**
     * @Title: getThreeFourThreeString
     * @Description: 隐藏11位手机号的中间四位
     * @param @param nativeString
     * @param @return
     * @return String
     */
    public static String getThreeFourThreeString(String nativeString) {
        if (nativeString == null) {
            return "-";
        }
        if (nativeString.length() < 7) {
            // 长度小于8直接返回，避免越界
            return nativeString;
        }
        StringBuffer sbShow = new StringBuffer(nativeString.subSequence(0, 3));
        sbShow.append("****");
        sbShow.append(nativeString.substring(nativeString.length() - 4, nativeString.length()));
        return sbShow.toString();
    }

    /**
     * 去掉字符串首尾的空格 判断字符串是否为空，为空直接返回，不为空去掉空格后返回
     *
     *
     * @param str
     * @return
     */
    public static String trim(String str) {
        if (isNullOrEmpty(str))
            return str;
        return str.trim();
    }

    /**
     * 去除HTML字串中的控制字符及不可视字符
     *
     * @param str
     *            HTML字串
     * @return 返回的字串
     */
    public static String escapeHTML(String str) {
        int length = str.length();
        int newLength = length;
        boolean someCharacterEscaped = false;
        for (int i = 0; i < length; i++) {
            char c = str.charAt(i);
            int cint = 0xffff & c;
            if (cint < 32)
                switch (c) {
                    case 11:
                    default:
                        newLength--;
                        someCharacterEscaped = true;
                        break;

                    case '\t':
                    case '\n':
                    case '\f':
                    case '\r':
                        break;
                }
            else
                switch (c) {
                    case '"':
                        newLength += 5;
                        someCharacterEscaped = true;
                        break;

                    case '&':
                    case '\'':
                        newLength += 4;
                        someCharacterEscaped = true;
                        break;

                    case '<':
                    case '>':
                        newLength += 3;
                        someCharacterEscaped = true;
                        break;
                }
        }
        if (!someCharacterEscaped)
            return str;

        StringBuffer sb = new StringBuffer(newLength);
        for (int i = 0; i < length; i++) {
            char c = str.charAt(i);
            int cint = 0xffff & c;
            if (cint < 32)
                switch (c) {
                    case '\t':
                    case '\n':
                    case '\f':
                    case '\r':
                        sb.append(c);
                        break;
                }
            else
                switch (c) {
                    case '"':
                        sb.append("&quot;");
                        break;

                    case '\'':
                        sb.append("&apos;");
                        break;

                    case '&':
                        sb.append("&amp;");
                        break;

                    case '<':
                        sb.append("&lt;");
                        break;

                    case '>':
                        sb.append("&gt;");
                        break;

                    default:
                        sb.append(c);
                        break;
                }
        }
        return sb.toString();
    }

    /**
     * 去除SQL字串中的控制字符
     *
     * @param str
     *            SQL字串
     * @return 返回的字串
     */
    public static String escapeSQL(String str) {
        int length = str.length();
        int newLength = length;
        for (int i = 0; i < length; ) {
            char c = str.charAt(i);
            switch (c) {
                case 0:
                case '"':
                case '\'':
                case '\\':
                    newLength++;
                default:
                    i++;
                    break;
            }
        }
        if (length == newLength)
            return str;

        StringBuffer sb = new StringBuffer(newLength);
        for (int i = 0; i < length; i++) {
            char c = str.charAt(i);
            switch (c) {
                case '\\':
                    sb.append("\\\\");
                    break;

                case '"':
                    sb.append("\\\"");
                    break;

                case '\'':
                    sb.append("\\'");
                    break;

                case 0:
                    sb.append("\\0");
                    break;

                default:
                    sb.append(c);
                    break;
            }
        }
        return sb.toString();
    }

    /**
     * 将一个字符串分解成几个段
     *
     * @param str
     *            字符串
     * @param segLen
     *            每段限长
     * @param segNum
     *            分解段数
     * @return 分解后的字串组
     */
    public static String[] split(String str, int segLen, int segNum) {
        String[] result = new String[segNum];
        if (str == null || str.length() == 0)
            return result;

        byte[] strByte;
        try {
            strByte = str.getBytes("GBK");
        } catch (UnsupportedEncodingException ex) {
            strByte = str.getBytes();
        }
        int pos = 0;
        for (int i = 0; i < segNum; i++) {
            int actLen = ((strByte.length - pos) < segLen) ? (strByte.length - pos) : segLen;
            byte[] b = new byte[actLen];
            System.arraycopy(strByte, pos, b, 0, actLen);
            result[i] = new String(b);
            pos += actLen;
            if (pos >= strByte.length)
                break;
        }
        return result;
    }

    /**
     * 将一个字符串分解成几个段，每段都能正常转换为IBM935字符集
     *
     * @param str
     *            字符串
     * @param segLen
     *            每段限长
     * @param segNum
     *            分解段数
     * @return 分解后的字串组
     */
    public static String[] split4Cp935(String str, int segLen, int segNum) {

        String[] result = new String[segNum];

        byte[] strByte;
        try {
            strByte = str.getBytes("GBK");
        } catch (UnsupportedEncodingException ex) {
            strByte = str.getBytes();
        }
        int strLen = strByte.length;
        byte[] tmpByte = new byte[2 * strLen + segLen];

        int head;
        int flag;
        int count;

        int strCount = 0;
        int segCount = 0;
        int lastStrCount = 0;

        for (flag = 0, head = 0, count = 0; strCount < strLen && segCount < segNum; count++) {
            if ((strByte[strCount] & 0x80) != 0) {
                head = head == 1 ? 0 : 1;
                if (flag == 0) {
                    tmpByte[count] = (byte) ' ';
                    flag = 1;
                    count++;
                }

                if ((count == ((count / segLen) + 1) * segLen - 2) && (head == 1)) {
                    tmpByte[count] = (byte) ' ';
                    tmpByte[count + 1] = (byte) ' ';
                    result[segCount] = str.substring(lastStrCount, strCount);
                    lastStrCount = strCount;
                    segCount++;
                    tmpByte[count + 2] = (byte) ' ';
                    flag = 1;
                    count = count + 3;
                } else if (((segLen * ((count + 1) / segLen) - 1) == count) && (head == 1)) {
                    tmpByte[count] = (byte) ' ';
                    byte[] tmp = new byte[strCount - lastStrCount];
                    System.arraycopy(strByte, lastStrCount, tmp, 0, tmp.length);
                    result[segCount] = new String(tmp);
                    lastStrCount = strCount;
                    segCount++;
                    tmpByte[count + 1] = (byte) ' ';
                    count = count + 2;
                    flag = 1;
                } else if (((segLen * (count / segLen)) == count) && (head == 1)) {
                    byte[] tmp = new byte[strCount - lastStrCount];
                    System.arraycopy(strByte, lastStrCount, tmp, 0, tmp.length);
                    result[segCount] = new String(tmp);
                    lastStrCount = strCount;
                    segCount++;
                    tmpByte[count] = (byte) ' ';
                    count++;
                    flag = 1;
                }
            } else {
                if (flag == 1) {
                    tmpByte[count] = (byte) ' ';
                    count++;
                    flag = 0;
                    if (count == (count / segLen) * segLen) {
                        byte[] tmp = new byte[strCount - lastStrCount];
                        System.arraycopy(strByte, lastStrCount, tmp, 0, tmp.length);
                        result[segCount] = new String(tmp);
                        lastStrCount = strCount;
                        segCount++;
                    } else {
                        if ((segLen * ((count + 1) / segLen) - 1) == count) {
                            byte[] tmp = new byte[strCount - lastStrCount + 1];
                            System.arraycopy(strByte, lastStrCount, tmp, 0, tmp.length);
                            result[segCount] = new String(tmp);
                            lastStrCount = strCount + 1;
                            segCount++;
                        }
                    }
                } else {
                    if ((segLen * ((count + 1) / segLen) - 1) == count) {
                        byte[] tmp = new byte[strCount - lastStrCount + 1];
                        System.arraycopy(strByte, lastStrCount, tmp, 0, tmp.length);
                        result[segCount] = new String(tmp);
                        lastStrCount = strCount + 1;
                        segCount++;
                    }
                }

            }
            tmpByte[count] = strByte[strCount];
            strCount++;
            if (strCount >= strLen && segCount < segNum) {
                byte[] tmp = new byte[strCount - lastStrCount];
                System.arraycopy(strByte, lastStrCount, tmp, 0, tmp.length);
                result[segCount] = new String(tmp);
                lastStrCount = strCount;
                segCount++;
            }

            if ((count + 1) % segLen == 0) {
                flag = 0;
            }
        }

        return result;
    }

    /**
     * 取得字符串字节长度,使用缺省字符集
     *
     * @param str
     * @return length
     */
    public static int getByteLength(String str) {
        return str.getBytes().length;
    }

    /**
     * 截取最长为length字节的字符串
     *
     * @param str
     * @param length
     * @return str
     */
    public static String subString(String str, String encode, int length) {
        if (str == null || length < 0)
            return null;
        int zhl = 0, enl = 0;
        if (zhLen.containsKey(encode)) {
            zhl = ((Integer) zhLen.get(encode)).intValue();
            enl = ((Integer) enLen.get(encode)).intValue();
        } else {
            try {
                zhl = zh.getBytes(encode).length;
            } catch (UnsupportedEncodingException e) {
                zhl = zh.getBytes().length;
            }
            try {
                enl = en.getBytes(encode).length;
            } catch (UnsupportedEncodingException e) {
                enl = en.getBytes().length;
            }
        }

        int len = 0;
        int strlen = str.length();
        int i;
        for (i = 0; i < strlen; i++) {
            if (str.charAt(i) < 0x80)
                len += enl;
            else
                len += zhl;
            if (len > length)
                break;

        }

        return str.substring(0, i);
    }

    /**
     * 将带分隔符的字符串转换成list
     */
    public static ArrayList splitString(String str, String separator) {
        return new ArrayList(Arrays.asList(str.split(separator)));
    }

    public static String xml2String(byte[] xml) {
        if (xml == null)
            return null;
        if (xml.length == 0)
            return "";
        try {
            if (xml.length > 20) {
                int max = Math.min(100, xml.length);
                int i = 0;
                while (i < max && xml[i] != '<')
                    i++;
                int j = i;
                while (j < max && xml[j] != '>')
                    j++;
                if (j - i > 15) {
                    String head = new String(xml, i, j - i + 1, "ISO-8859-1");
                    i = head.indexOf("encoding=");
                    if (i != -1) {
                        i += "encoding=".length();
                        char q = head.charAt(i);
                        j = head.indexOf(q, i + 1);
                        if (j != -1) {
                            head = head.substring(i + 1, j);
                            return new String(xml, head);
                        }
                    }
                }
            }
        } catch (Exception t) {
        }
        return new String(xml);
    }

    /**
     * 得到以head开头,tail结尾的子串
     * @param buffer
     * @param head
     * @param tail
     * @return
     */
    public static String subString(String buffer, String head, String tail) {
        if (buffer == null || head == null || tail == null)
            return buffer;
        int startNum = buffer.indexOf(head);
        int endNum = buffer.lastIndexOf(tail);

        startNum = startNum >= 0 ? startNum : 0;
        endNum = endNum >= 0 ? endNum + tail.length() : buffer.length();
        return buffer.substring(startNum, endNum);
    }

    /**
     * 判断对象或对象数组中每一个对象是否为空: 对象为null，字符序列长度为0，集合类、Map为empty
     *
     * @param obj
     * @return
     */
    public static boolean isNullOrEmpty(Object obj) {
        if (obj == null)
            return true;

        if (obj instanceof CharSequence){
            String str = obj.toString();
            if(str.trim().equals(""))
                return true;

            if(((CharSequence) obj).length() == 0)
                return true;
        }

        if (obj instanceof Collection)
            return ((Collection<?>) obj).isEmpty();

        if (obj instanceof Map)
            return ((Map<?, ?>) obj).isEmpty();

        if (obj instanceof Object[]) {
            Object[] object = (Object[]) obj;
            boolean empty = true;
            for (int i = 0; i < object.length; i++)
                if (!isNullOrEmpty(object[i])) {
                    empty = false;
                    break;
                }
            return empty;
        }
        return false;
    }

    /**
     * 检测字符串是否为空或无内容
     *
     * @param srcString
     * @return
     */
    public static boolean isEmpty(String srcString) {
        if (srcString == null || srcString.trim().equals("")|| srcString.trim().toLowerCase().equals("null")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断是否为数字
     * @param str
     * @return
     */
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    /**
     * 处理空字符串
     * @param srcString
     * @return
     */
    public static String doEmpty(String srcString) {
        if (srcString == null || srcString.trim().equals("")|| srcString.trim().toLowerCase().equals("null")) {
            return "";
        } else {
            return srcString;
        }

    }
    /**
     * 检测字符串是否为空或无内容
     *
     * @param srcString
     * @return
     */
    public static boolean isEmptyOrZero(String srcString) {
        if (srcString == null || srcString.trim().equals("")||srcString.trim().equals("0")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 将用separator分隔的String转化为List，如果str中没有separator则返回的List中只有 str一项
     *
     * @param str
     * @param separator
     * @return
     */
    public static List str2List(String str, String separator) {
        List result = new ArrayList();
        if (str.indexOf(separator) < 0) {
            result.add(str);
        } else {
            String[] strArray = str.split(separator);

            for (int i = 0; i < strArray.length; i++) {
                result.add(strArray[i]);
            }
        }

        return result;
    }

    /**
     * 去除字符串中空白字符
     *
     * @param str
     * @return
     */
    public static String filterSBCCase(String str) {
        char[] ch = str.toCharArray();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < ch.length; i++) {
            if (!Character.isWhitespace(ch[i])) {
                sb.append(String.valueOf(ch[i]));
            }
        }
        return sb.toString();
    }

    /**
     * 字符串是否全数字（无符号、小数点）
     *
     * @param str
     * @return
     */
    public static boolean isDigit(String str) {
        char c;
        for (int i = 0; i < str.length(); i++) {
            c = str.charAt(i);
            if (c < '0' || c > '9')
                return false;
        }
        return true;
    }

    /**
     * 获取文件后缀扩展名
     * @param filename
     * @return
     */
    public static String getFileNameNoEx(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }

    /**
     * 字符串是否全数字或英文字母（无符号、小数点）
     *
     * @param str
     * @return
     */
    public static boolean isAlphaDigit(String str) {
        char c;
        for (int i = 0; i < str.length(); i++) {
            c = str.charAt(i);
            if (c < '0' || c > '9' && c < 'A' || c > 'Z' && c < 'a' || c > 'z')
                return false;
        }
        return true;
    }

    /**
     * get substring in bytes length
     *
     * @param orgString
     *            original string
     * @param lengthInBytes
     *            bytes length
     * @return substring
     */
    public static final String subStringInBytes(String orgString, int startPos, int lengthInBytes) {

        if (orgString == null)
            return null;

        byte[] orgBytes;
        try {
            orgBytes = orgString.getBytes("GBK");
        } catch (UnsupportedEncodingException ex) {
            orgBytes = orgString.getBytes();
        }
        if (startPos < 0 || startPos > orgBytes.length)
            return null;
        else if (lengthInBytes < startPos)
            return null;

        byte[] newBytes;
        int newLength = orgBytes.length - startPos;
        if (lengthInBytes < newLength)
            newLength = lengthInBytes;

        newBytes = new byte[newLength];
        System.arraycopy(orgBytes, startPos, newBytes, 0, newLength);

        return new String(newBytes);
    }

    /**
     * 按千位分割格式格式化数字
     *
     * @param text
     *            原数字
     * @param scale
     *            小数点保留位数
     * @return
     */
    public static String parseStringPattern(String text, int scale) {
        if (text == null || "".equals(text) || "null".equals(text)) {
            return "_";
        }
        if (text.contains(",") || text.contains("，")) {
            return text;
        }
        String temp = "###,###,###,###,###,###,###,##0";
        if (scale > 0)
            temp += ".";
        for (int i = 0; i < scale; i++)
            temp += "0";
        DecimalFormat format = new DecimalFormat(temp);
        Double d = Double.valueOf(text);
        return format.format(d).toString();
    }

    /**
     * 按千位分割格式格式化数字
     *
     * @param text
     *            原数字
     * @param scale
     *            小数点保留位数
     * @return
     */
    public static String parseStringPattern2(String text, int scale) {
        if (text == null || "".equals(text) || "null".equals(text)) {
            return parseStringPattern2("0", scale);
        }
        if (text.contains(",") || text.contains("，")) {
            return text;
        }
        String temp = "###,###,###,###,###,###,###,##0";
        if (scale > 0)
            temp += ".";
        for (int i = 0; i < scale; i++)
            temp += "0";
        DecimalFormat format = new DecimalFormat(temp);
        Double d = Double.valueOf(text);
        return format.format(d).toString();
    }

    /**
     * 截取小数点后面的位数
     *
     * @param str
     *            需要转换的数据
     * @param number
     *            保留小数点后面的位数
     * @return
     */
    public static String splitStringwith2point(String str, int number) {
        if (StringUtils.isEmpty(str)) {
            return str;
        }
        if (str.contains(".")) {
            StringBuffer sb = new StringBuffer();
            String[] strings = str.split("\\.");
            String before = strings[0];
            String after = strings[1];
            if (after.length() >= number) {
                String strAfter = after.substring(0, number);
                sb.append(before).append(".").append(strAfter);
            } else {
                sb.append(before).append(".").append(after);
            }
            return sb.toString();
        } else {
            return str;
        }
    }

    /**
     * 转为String[]
     *
     * @param object
     * @return
     */
    public static String[] getStringArray(Object object) {
        if (object instanceof String[])
            return (String[]) object;
        if (object instanceof String) {
            String tmpStrs[] = new String[1];
            tmpStrs[0] = (String) object;
            return tmpStrs;
        }
        return null;
    }

    public static String ADEncoding(String str) {
        if (str == null || str.length() == 0 || isDigit(str) && !str.startsWith("99"))
            return str;
        StringBuffer buf = new StringBuffer("99");
        char c;
        for (int i = 0; i < str.length(); i++) {
            c = str.charAt(i);
            if (c > '\u00ff')
                buf.append("99");
            else if (c > '~')
                buf.append("98");
            else if (c == '\t')
                buf.append("95");
            else if (c == '\r' || c == '\n')
                buf.append("96");
            else if (c < ' ')
                buf.append("97");
            else if (c >= '0' && c <= '9')
                buf.append('0').append(c);
            else if (c >= 'A' && c <= 'Z')
                buf.append((char) ((c - 'A' + 10) / 10 + '0')).append(
                        (char) ((c - 'A' + 10) % 10 + '0'));
            else if (c >= 'a' && c <= 'z')
                buf.append((char) ((c - 'a' + 40) / 10 + '0')).append(
                        (char) ((c - 'a' + 40) % 10 + '0'));
            else if (c < '0')
                buf.append((char) ((c - ' ' + 66) / 10 + '0')).append(
                        (char) ((c - ' ' + 66) % 10 + '0'));
            else if (c < 'A')
                buf.append((char) ((c - ':' + 82) / 10 + '0')).append(
                        (char) ((c - ':' + 82) % 10 + '0'));
            else if (c < 'a')
                buf.append((char) ((c - '[' + 89) / 10 + '0')).append(
                        (char) ((c - '[' + 89) % 10 + '0'));
            else
                // if (c >= '{')
                buf.append((char) ((c - '{' + 36) / 10 + '0')).append(
                        (char) ((c - '{' + 36) % 10 + '0'));
        }
        return buf.toString();
    }

    public static String ADDecoding(String str) {
        if (str == null || str.length() < 4 || !str.startsWith("99") || (str.length() & 1) == 1)
            return str;
        StringBuffer buf = new StringBuffer();
        for (int i = 2; i < str.length(); i += 2) {
            int n = (str.charAt(i) - '0') * 10 + str.charAt(i + 1) - '0';
            if (n < 10)
                buf.append(n);
            else if (n < 36)
                buf.append((char) (n - 10 + 'A'));
            else if (n < 40)
                buf.append((char) (n - 36 + '{'));
            else if (n < 66)
                buf.append((char) (n - 40 + 'a'));
            else if (n < 82)
                buf.append((char) (n - 66 + ' '));
            else if (n < 89)
                buf.append((char) (n - 82 + ':'));
            else if (n < 95)
                buf.append((char) (n - 89 + '['));
            else if (n == 95)
                buf.append('\t');
            else if (n == 96)
                buf.append('\n');
            else if (n == 97)
                buf.append('\u00a9');
            else if (n == 98)
                buf.append('\u00c5');
            else
                // if (n == 99)
                buf.append('\u2592');
        }
        return buf.toString();
    }

    private static Map zhLen = new HashMap(), enLen = new HashMap();

    private static final String zh = "汉";

    private static final String en = "A";

    /**
     * 取得类的简单名
     *
     * @param obj
     * @return 类的简单名
     */
    public static String getSimpleName(Object obj) {
        if (obj == null)
            return null;
        String name = obj.getClass().getName();
        if (name.toLowerCase().indexOf("$proxy") >= 0) {
            name = obj.toString();
            int idx = name.lastIndexOf("@");
            if (idx > 0)
                name = name.substring(0, idx);
        }
        return name.substring(name.lastIndexOf(".") + 1);
    }

    /**
     * 截字串
     *
     * @param str
     * @param length
     * @return
     */
    public static String subString(String str, int length) {
        if (str == null) {
            return "";
        }
        int end = str.length();
        if (length > end) {
            end = length;
        }
        return str.substring(0, end);
    }

    /**
     * 获取字符串的长度, 如果为null则长度为0
     *
     * @param str
     * @return
     */
    public static int length(String str) {
        if (isNullOrEmpty(str))
            return 0;
        else
            return str.trim().length();

    }

    public static boolean isBase64(byte[] data) {
        char[] chars = new String(data).toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] >= '0' && chars[i] <= '9' || chars[i] >= 'A' && chars[i] <= 'Z'
                    || chars[i] >= 'a' && chars[i] <= 'z' || chars[i] == '+' || chars[i] == '/'
                    || chars[i] == '=') {
                LogUtils.d(TAG, "isBase64 true");
            } else {
                return false;
            }

        }
        return true;
    }

    /**
     * <p>
     * 方法shortClassName()。
     * </p>
     *
     * @since Aug 20, 2008
     * @param obj
     * @return
     */
    public static String shortClassName(Object obj) {
        if (obj == null) {
            return "null";
        }

        Class klass = obj instanceof Class ? (Class) obj : obj.getClass();
        String fullname = klass.getName();
        int packageNameLength = klass.getPackage().getName().length();
        return packageNameLength > 0 ? fullname.substring(packageNameLength + 1) : fullname;
    }

    public static String CRLF = System.getProperty("line.separator");

    public static String ellipsisText(String longText, int maxLength) {
        if (longText == null)
            return "null";
        int index = longText.indexOf(CRLF);
        String firstLine = index >= 0 ? longText.substring(0, index) : longText;
        return firstLine.length() > maxLength ? firstLine.substring(0, maxLength) + "..."
                : firstLine;
    }

    public static String stringOf(Exception ex) {
        if (ex != null) {
            StringBuffer sb = new StringBuffer();
            sb.append("Class: " + shortClassName(ex));
            sb.append("Conversation: " + ellipsisText(ex.getMessage(), 32));
            return sb.toString();
        } else {
            return "null";
        }
    }

    public static String stringOf(Date date) {
        return stringOf(TimeZone.getDefault(), date);
    }

    public static String stringOf(TimeZone timeZone, Date date) {
        if (date == null) {
            return "null";
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        if (timeZone != null) {
            formatter.setTimeZone(timeZone);
        }
        return formatter.format(date);
    }

    /**
     * 返回srcStr字符串的前truncByteLen字节长度的子串。<br>
     *
     * 如果截取的末字节是半个汉字，则略去该字节，要求返回完整的汉字。<br>
     *
     * (1)srcStr == null || truncByteLen < 0 返回""<br>
     * (2)truncByteLen > srcStr的实际字节长度，则原样返回srcStr
     *
     * @param srcStr
     *            待截取字符串
     * @param encode
     *            编码方式
     * @param truncByteLen
     *            截取字节数
     * @return 截取后的字符串
     *
     *         e.g. srcStr="ab你好", truncByteLen=3, 返回"ab"; truncByteLen=4,
     *         返回"ab你"
     *
     *         Written Date: 2008-9－17
     *
     */
    public static String truncateStr(String srcStr, String encode, int truncByteLen) {
        if (srcStr == null || truncByteLen < 0)
            return "";

        int strLen = srcStr.length();
        String tempStr = "";

        try {
            if (truncByteLen <= strLen)
                tempStr = srcStr.substring(0, truncByteLen);// truncByteLen=0,则返回""
            else
                tempStr = srcStr;
        } catch (IndexOutOfBoundsException iobe) {
            return "";
        }

        int tempByteLen;
        try {
            tempByteLen = tempStr.getBytes(encode).length;
        } catch (UnsupportedEncodingException e) {
            tempByteLen = tempStr.getBytes().length;
        }

        while (tempByteLen > truncByteLen) {
            tempStr = tempStr.substring(0, tempStr.length() - 1);
            try {
                tempByteLen = tempStr.getBytes(encode).length;
            } catch (UnsupportedEncodingException e) {
                tempByteLen = tempStr.getBytes().length;
            }
        }
        return tempStr;
    }

    public static Locale toLocale(String localeStr) {
        if (localeStr == null)
            return null;
        String[] part = localeStr.split("_");
        if (part.length == 1)
            return new Locale(part[0].toLowerCase());
        if (part.length == 2)
            return new Locale(part[0].toLowerCase(), part[1].toUpperCase());
        if (part.length == 3)
            return new Locale(part[0].toLowerCase(), part[1].toUpperCase(), part[2].toUpperCase());
        return new Locale(localeStr);
    }

    /**
     * 使用“,”对原有描述进行分隔，对所有为空的内容替换为“-”，重新组合后返回
     *
     * 特殊注意：当传入值为null时，返回”“ 当传入值为”“时，返回”-“ 当传入值只包含”,“时，返回”-,-“
     *
     * @param args
     * @return fillStr
     */
    public static String fillNullArgs(String args) {

        String fillStr = "";

        // 参数为空指针返回空串
        if (args == null) {
            return fillStr;
        }

        // 无限次匹配，保留末尾空数组
        String[] strArgs = args.split(",", -1);

        // 替换空元素
        for (int i = 0; i < strArgs.length; i++) {
            if (StringUtils.isNullOrEmpty(strArgs[i])) {
                strArgs[i] = "-";
            }
            fillStr = fillStr + strArgs[i] + ",";
        }

        // 去掉最后的”,“
        if (!StringUtils.isNullOrEmpty(fillStr)
                && (fillStr.substring(fillStr.length() - 1, fillStr.length()).equals(","))) {
            fillStr = fillStr.substring(0, fillStr.length() - 1);
        }

        return fillStr;
    }

    /**
     * 验证时间（小时分钟）是否合法 0000-2359
     *
     * @param time
     * @return
     */
    public static boolean checkTime(String time) {
        boolean flag = true;

        if (null == time || "".equals(time))
            flag = false;
        /** 必须为4位数字 **/
        if (time != null && 4 != time.length())
            flag = false;
        /** 必须是数字 **/
        if (time != null && !isDigit(time))
            flag = false;

        if (flag) {
            int num1 = Integer.parseInt(time.substring(0, 2));
            int num2 = Integer.parseInt(time.substring(2, 4));
            if (num1 > 23)
                flag = false;
            if (num2 > 59)
                flag = false;
        }

        return flag;

    }

    public static String changeCode(String s, String enc, String dec) {
        try {
            if (s != null) {
                s = new String(s.getBytes("ISO8859_1"), "UTF-8");
            }
        } catch (Exception e) {
            return s;
        }
        return s;
    }

    public static String iso2utf8(String s) {
        return changeCode(s, "ISO-8859-1", "UTF-8");
    }

    /**
     * 把字符串　转成　ｂｏｏｌｅａｎ　类型
     *
     * @param str
     * @return
     */
    public static boolean parseStrToBoolean(String str) {
        if (str == null) {
            return false;
        }
        if (str.equals("Y")) {
            return true;
        }
        if (str.equals("YY")) {
            return true;
        }
        if (str.endsWith("N")) {
            return false;
        }
        if (str.equals("true")) {
            return true;
        }
        if (str.equals("false")) {
            return false;
        }

        return false;

    }

    /***
     * 将一个数据转化为百分数
     *
     * @param number
     *            :非百分号数据
     * @return
     */
    public static String dealNumber(String number) {
        String date = null;
        float f = Float.valueOf(number);
        if (f <= 0) {
            date = "0%";
        } else {

            if (number.contains(".")) {
                int beforeLen = number.indexOf(".");
                // 整数部分
                String before = number.substring(0, beforeLen);
                // 小数部分
                String num = number.substring(beforeLen + 1, number.length());
                if (StringUtils.isEmpty(num)) {
                    StringBuilder sb = new StringBuilder(before);
                    sb.append("00");
                    sb.append("%");
                    date = sb.toString().trim();
                } else {
                    if (num.length() > 2) {
                        String be = num.substring(0, 2);
                        String af = num.substring(2, num.length());
                        StringBuilder sb = new StringBuilder(before);
                        sb.append(be);
                        sb.append(".");
                        sb.append(af);
                        sb.append("%");
                        date = sb.toString().trim();
                    } else if (num.length() == 2) {
                        String be = num.substring(0, 2);
                        StringBuilder sb = new StringBuilder(before);
                        sb.append(be);
                        sb.append("%");
                        date = sb.toString().trim();
                    } else if (num.length() == 1) {
                        String be = num.substring(0, 1);
                        StringBuilder sb = new StringBuilder(before);
                        sb.append(be);
                        sb.append("0");
                        sb.append("%");
                        date = sb.toString().trim();
                    }
                }
            } else {
                StringBuilder sb = new StringBuilder(number);
                sb.append("00");
                sb.append("%");
                date = sb.toString().trim();
            }
        }
        return date;
    }

    /**
     * 保留整数位
     *
     * @param: number：需要去掉小数位的数据
     */
    public static String deleateNumber(String number) {
        String date = null;
        if (number.contains(".")) {
            int len = number.indexOf(".");
            date = number.substring(0, len);
        } else {
            date = number;
        }
        return date;
    }

    /***
     * 半角转换为全角
     *
     * @param input
     * @return
     */
    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    /**
     * * 去除特殊字符或将所有中文标号替换为英文标号
     *
     * @param str
     * @return
     */
    public static String stringFilter(String str) {
        str = str.replaceAll("【", "[").replaceAll("】", "]").replaceAll("！", "!")
                .replaceAll("：", ":");// 替换中文标号
        String regEx = "[『』]"; // 清除掉特殊字符
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    /**
     * @Title: getISOString
     * @Description: 获取转换成ISO8859-1的字符串
     * @param nativeString
     * @return
     */
    public static String getISOString(String nativeString) {
        String checkNickNameStr = "";
        try {
            // 转化编码，能够测出中英文混合长度
            checkNickNameStr = new String(nativeString.getBytes("GBK"), "ISO8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return checkNickNameStr;
    }

    /**
     *将字符串中的"<br />"标签替换成 换行符"\n"
     *
     * @param content
     * @return
     */

    public static String replayBrToGanN(String content) {
        if (content == null) {
            return "";
        }
        content = content.replace("<br />", "\n");
        return content;
    }

    /**
     * 是否是正确的电话号码（规则以1开头的11位数字就是正确的手机号码）
     * @param phoneNum
     * @return
     */
    public static boolean isRightPhone(String phoneNum) {
        if (isNullOrEmpty(phoneNum)) {
            return false;
        }
        String phoneReg = "1\\d{10}";// 1开头的11位数字
        return phoneNum.matches(phoneReg);
    }

    /**
     * 验证是否为正确的邮箱地址
     *
     * @param email
     *            待验证邮箱地址
     * @return 正确返回true，否则false
     */
    public static boolean isRightEmail(String email) {
        if (isNullOrEmpty(email)) {
            return false;
        }
        String emailFilter = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
        return email.matches(emailFilter);
    }

    /**
     * 验证是否为正确的密码 规则为6到16位的字母和数字组合
     *
     * @param pwd
     *            待验证密码
     * @return 正确返回true，否则false
     */
    public static boolean isRightPwd(String pwd) {
        if (isNullOrEmpty(pwd)) {
            return false;
        }
        // String lengthReg = "[a-zA-Z0-9]{6,16}";// 6到16位
        if (pwd.length() < 6 || pwd.length() > 16) {
            return false;
        }
        return true;
    }

    static public int INVALID_INT = 0xffff218a;

    /**
     * 对象转化成Int值
     * @param tmp
     * @return
     */
    static public int Object2Int(Object tmp) {
        int res = INVALID_INT;
        if (tmp != null) {
            if (tmp instanceof String) {
                try {
                    res = (int) Float.parseFloat((String) tmp);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (tmp instanceof Float) {
                res = (int) ((Float) tmp + 0);
            }
            if (tmp instanceof Double) {
                res = (int) ((Double) tmp + 0);
            }
            if (tmp instanceof Integer) {
                res = (int) ((Integer) tmp + 0);
            }
            // tmp instanceof Double||tmp instanceof Integer){

            // Double.

        }
        return res;
    }

    /**
     * 将数字转化成相应的文字(0到999直接显示，1000到9999显示为1.*k,10000到更多显示为1.*w)
     *
     * @param goodNumber
     * @return
     */
    public static String getBreNumberStr(int goodNumber) {
        NumberFormat nf;
        float value = goodNumber;
        if (value < 1000) {
            return goodNumber + "";
        } else if (value >= 1000 && value < 10000) {
            nf = new DecimalFormat("#.#千");
            goodNumber = goodNumber / 100;
            value = goodNumber;
            value = value / 10;
        } else {
            nf = new DecimalFormat("#.#万");
            goodNumber = goodNumber / 1000;
            value = goodNumber;
            value = value / 10;
        }
        String str = nf.format(value);
        return str;
    }

    /**
     * 字符串某些部分显示其他颜色
     * @param lightStr 高亮文字
     * @param allStr 完整文字
     * @param resourceColor 高亮文字颜色
     * @return
     */
    public static SpannableStringBuilder getSpannableStringBuilder(String lightStr, String allStr, int resourceColor) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(allStr);
        int lastIndexOf = allStr.lastIndexOf(lightStr);
        int endIndexOf = lastIndexOf + lightStr.length();
        spannableStringBuilder.setSpan(new ForegroundColorSpan(MainApplication.getApplication().getResources().getColor(resourceColor)), lastIndexOf, endIndexOf, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        return spannableStringBuilder;
    }
}