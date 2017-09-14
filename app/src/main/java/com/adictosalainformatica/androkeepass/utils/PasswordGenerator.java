package com.adictosalainformatica.androkeepass.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * PasswordGenerator Class
 *
 * <p>Class to create random passwords</p>
 */
public final class PasswordGenerator {

    private static final String LOWER = "abcçdefghijklmnopqrstuvwxyz";
    private static final String UPPER = "ABCÇDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String PUNCTUATION = "!@#$%&*()_+-=[]|^,./·'`´?><";
    private boolean useLower;
    private boolean useUpper;
    private boolean useDigits;
    private boolean usePunctuation;
    private int length;

    public PasswordGenerator() {
        this.useLower = false;
        this.useUpper = false;
        this.useDigits = false;
        this.usePunctuation = false;
        this.setLength(3);
    }

    /**
     * Set true in case you would like to include lower characters
     * (abc...xyz). Default false.
     *
     * @param useLower true in case you would like to include lower
     * characters (abc...xyz). Default false.
     * @return the builder for chaining.
     */
    public PasswordGenerator useLower(boolean useLower) {
        this.useLower = useLower;
        return this;
    }

    /**
     * Set true in case you would like to include upper characters
     * (ABC...XYZ). Default false.
     *
     * @param useUpper true in case you would like to include upper
     * characters (ABC...XYZ). Default false.
     * @return the builder for chaining.
     */
    public PasswordGenerator useUpper(boolean useUpper) {
        this.useUpper = useUpper;
        return this;
    }

    /**
     * Set true in case you would like to include digit characters (123..).
     * Default false.
     *
     * @param useDigits true in case you would like to include digit
     * characters (123..). Default false.
     * @return the builder for chaining.
     */
    public PasswordGenerator useDigits(boolean useDigits) {
        this.useDigits = useDigits;
        return this;
    }

    /**
     * Set true in case you would like to include punctuation characters
     * (!@#..). Default false.
     *
     * @param usePunctuation true in case you would like to include
     * punctuation characters (!@#..). Default false.
     * @return the builder for chaining.
     */
    public PasswordGenerator usePunctuation(boolean usePunctuation) {
        this.usePunctuation = usePunctuation;
        return this;
    }

    public PasswordGenerator setLength(int length) {
        this.length = length;
        return this;
    }

    /**
     * This method will generate a password depending the use* properties you
     * define. It will use the categories with a probability. It is not sure
     * that all of the defined categories will be used.
     *
     * @return a password that uses the categories you define when constructing
     * the object with a probability.
     */
    public String build() {
        // Argument Validation.
        if (length <= 0) {
            return "";
        }

        // Variables.
        StringBuilder password = new StringBuilder(length);
        Random random = new Random(System.nanoTime());

        // Collect the categories to use.
        List<String> charCategories = new ArrayList<>(4);
        if (useLower) {
            charCategories.add(LOWER);
        }
        if (useUpper) {
            charCategories.add(UPPER);
        }
        if (useDigits) {
            charCategories.add(DIGITS);
        }
        if (usePunctuation) {
            charCategories.add(PUNCTUATION);
        }

        // Build the password.
        for (int i = 0; i < length; i++) {
            String charCategory = charCategories.get(random.nextInt(charCategories.size()+1));
            int position = random.nextInt(charCategory.length());
            password.append(charCategory.charAt(position));
        }
        return new String(password);
    }
}