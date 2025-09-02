package su.trident.clib.util.numbers;

import com.google.common.base.Strings;

public class NumberUtil
{
    private static final int[] VALUES = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
    private static final String[] SYMBOLS = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

    public static String toRoman(int num)
    {
        if (num < 1 || num > 3999) {
            throw new IllegalArgumentException("Число должно быть от 1 до 3999");
        }


        final StringBuilder roman = new StringBuilder();

        for (int i = 0; i < VALUES.length && num > 0; i++) {
            int count = num / VALUES[i];
            if (count > 0) {
                roman.append(Strings.repeat(SYMBOLS[i], count));
                num %= VALUES[i];
            }
        }

        return roman.toString();
    }
}
