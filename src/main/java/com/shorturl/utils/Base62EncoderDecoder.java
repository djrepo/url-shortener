package com.shorturl.utils;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.xml.bind.DatatypeConverter;

import javax.swing.text.StyleConstants;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/*
* Utility class to decode and encode
*/
public class Base62EncoderDecoder {

    private static final int ZERO = 0;
    private static final char CHAR_ZERO = '0';
    /**
     * The digits involved in the Base62 encoding scheme
     */
    private static final String DIGITS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static final int BASE_INT = 62;

    /**
     * Encode the given long number in the Base62 encoding scheme. No
     * padding will be done.
     *
     * @param number a long number
     * @return Base62 encoded value for the given number.
     */
    public static String encode(long number) {
        return encode(number, ZERO, CHAR_ZERO);
    }

    /**
     * Encode the given {@link Long} in the Base62 encoding scheme. The padding
     * character will be padded at the start of the resulting string, if the
     * number of the characters in the encoded string produced is less than the
     * number of expected characters.
     *
     * @param number
     *            Number (a Long)
     * @param numCharsExpected
     *            The number of characters to be expected in the resulting
     *            string, less than which the padding will be done
     * @param paddingChar
     *            The character to be padded at the start of the encoded string
     * @return Base62 encoded value for the given number.
     */
    public static String encode(long number, int numCharsExpected, char paddingChar) {

        /*
         * Algorithm: Until the number 'n' is greater than 0: Remainder = n % 62
         * EncStr = EncStr + DIGITS[Remainder] n = n / 62 (The quotient now
         * becomes n)
         */

        StringBuilder encodedStringSb = new StringBuilder();
        int remainder = -1;
        while (number > 0) {
            remainder = (int) (number % BASE_INT);
            encodedStringSb.append(DIGITS.charAt(remainder));
            number = number / BASE_INT;
        }

        prependPaddingChars(encodedStringSb, numCharsExpected, paddingChar);
        return encodedStringSb.toString();
    }

    /**
     * Decode the given Base62 encoded string to a long number
     *
     * @param base62EncStr
     *            Base62 encoded string
     * @return Long value for the given Base62 encoded string
     */
    public static long decodeLong(String base62EncStr) {
        /*
         * Algorithm: The digits in the String are multiplied by 62 raised to
         * its appropriate power and summed up.
         *
         * For each Digit in Encoded String (Reversed): Temp = Digit's decimal
         * value Temp = Temp * 62 ^ i Result = Result + Temp
         */

        int len = base62EncStr.length();
        long result = 0, temp;
        for (int i = 0; i < len; i++) {
            temp = DIGITS.indexOf(base62EncStr.charAt(len - i - 1));
            temp *= Math.pow(BASE_INT, i);
            result += temp;
        }
        return result;
    }

    /**
     * Prepend the padding characters, when the length of the encoded string is
     * less than the number of expected characters
     *
     * @param encStrSb
     *            The encoded string whose length is to be compared
     * @param numCharsExpected
     *            Number of characters to be expected in the encoded string
     * @param paddingChar
     *            The character to be padded
     */
    private static void prependPaddingChars(final StringBuilder encStrSb, final int numCharsExpected,
                                            final char paddingChar) {
        while (encStrSb.length() < numCharsExpected) {
            encStrSb.insert(0, paddingChar);
        }
    }

}

