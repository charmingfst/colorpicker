package com.chm.myapplication.common;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinYinUtil {
    static HanyuPinyinOutputFormat outputFormat;

    static {
        try {
            outputFormat = new HanyuPinyinOutputFormat();
            outputFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
            outputFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
            outputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 汉字返回拼音，字母原样返回 必须在Android中使用
     *
     * @param source stirng
     * @return string
     */

    public static String getFullPinYin(String source) {
        StringBuilder sb = new StringBuilder();

        if (source.length() > 0) {
            for (int i = 0; i < source.length(); i++) {
                try {

                    String[] arrays = PinyinHelper.toHanyuPinyinStringArray(
                            source.charAt(i), outputFormat);
                    if (arrays != null && arrays.length > 0) {
                        sb.append(arrays[0]);
                    }
                } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                    badHanyuPinyinOutputFormatCombination.printStackTrace();
                }
            }
        }
        return sb.toString();
    }


    /**
     * 获取汉字拼音首字母大写 必须在Android中使用
     *
     * @param source string
     * @return string
     */
    public static String getFirstPinYin(String source) {

        // StringBuilder sb = new StringBuilder();

        if (source.length() > 0) {

            if (!isEnglish(source)) {
                try {
                    String[] arrays = PinyinHelper.toHanyuPinyinStringArray(
                            source.charAt(0), outputFormat);
                    if (arrays != null && arrays.length > 0) {
                        return arrays[0];
                    }
                } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                    badHanyuPinyinOutputFormatCombination.printStackTrace();
                }
            } else {
                return String.valueOf(source.charAt(0));
            }

        }
        return "#";
    }

    public static boolean isEnglish(String s) {
        char c = s.charAt(0);
        int i = (int) c;
        if ((i >= 65 && i <= 90) || (i >= 97 && i <= 122)) {
            return true;
        } else {
            return false;
        }
    }


}
