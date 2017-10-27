/*
 *      DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
 *                  Version 2, December 2004
 *
 *      Copyright (C) 2004 Sam Hocevar <sam@hocevar.net>
 *
 *      Everyone is permitted to copy and distribute verbatim or modified
 *      copies of this license document, and changing it is allowed as long
 *      as the name is changed.
 *
 *      DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
 *      TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION
 *
 *      0. You just DO WHAT THE FUCK YOU WANT TO.
 */

package com.adictosalainformatica.androkeepass.utils;

import com.adictosalainformatica.androkeepass.utils.password.PasswordGenerator;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class PasswordGeneratorTest {

    private static final String DIGITS =  ".*[0123456789].*";
    private static final String PUNCTUATION = ".*[!@#$%&*()_+-=\\[\\]|,./?><].*";
    private String password;
    private PasswordGenerator.PasswordGeneratorBuilder passwordGenerator;

    @Before
    public void setUp() throws Exception {
        passwordGenerator = new PasswordGenerator
                .PasswordGeneratorBuilder();
    }

    @After
    public void tearDown() throws Exception {
        passwordGenerator = null;
        password = null;
    }


    @Test
    public void useLower_generates_password_with_lower_case_characters() throws Exception {
        //Given
        boolean lowerCase = true;

        //When
        password = passwordGenerator
                .useLower(lowerCase)
                .build().generate(8);

        //Then
        Assert.assertTrue(checkStringContainsLowerCase(password));

    }

    @Test
    public void useUpper_generates_password_with_upper_case_characters() throws Exception {
        //Given
        boolean upperCase = true;

        //When
        password = passwordGenerator
                .useUpper(upperCase)
                .build().generate(8);

        //Then
        Assert.assertTrue(checkStringContainsUpperCase(password));
    }

    @Test
    public void useDigits_generates_password_with_digits_characters() throws Exception {
        //Given
        boolean digits = true;

        //When
        password = passwordGenerator
                .useDigits(digits)
                .build().generate(8);

        //Then
        Assert.assertTrue(password.matches(DIGITS));
    }

    @Test
    public void usePunctuation_generates_password_with_punctuation_characters() throws Exception {
        //Given
        boolean punctuation = true;

        //When
        password = passwordGenerator
                .usePunctuation(punctuation)
                .build().generate(8);

        //Then
        Assert.assertTrue(password.matches(PUNCTUATION));
    }

    @Test
    public void generate_creates_password_with_expected_length() throws Exception {
        //Given
        int expectedLength = 8;

        //When
        password = passwordGenerator
                .build().generate(expectedLength);

        //Then
        Assert.assertEquals(expectedLength, password.length());
    }

    @Test
    public void generate_returns_empty_string_if_length_is_lower_than_one() throws Exception {
        //Given
        int expectedLength = 0;

        //When
        password = passwordGenerator
                .build().generate(expectedLength);

        //Then
        Assert.assertTrue(password.isEmpty());
    }

    /**
     * Check that a given string contains at least one lower case character
     *
     * @param string
     * @return true if a lower case character is found, otherwise false
     */
    private static boolean checkStringContainsLowerCase(String string) {
        char character;
        for(int i=0;i < string.length();i++) {
            character = string.charAt(i);
            if (Character.isLowerCase(character)) {
                return true;
            }

        }
        return false;
    }

    /**
     * Check that a given string contains at least one upper case character
     *
     * @param string
     * @return true if a upper case character is found, otherwise false
     */
    private static boolean checkStringContainsUpperCase(String string) {
        char character;
        for(int i=0;i < string.length();i++) {
            character = string.charAt(i);
            if (Character.isUpperCase(character)) {
                return true;
            }
        }
        return false;
    }
}