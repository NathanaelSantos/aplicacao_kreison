package model;

import java.util.Arrays;

public class ValidateCPF {

    private int digitChecker1 = 0;
    private int digitChecker2 = 0;
    private int first = 10;
    private int second = 11;

    public boolean validate(String userCPF) {

        char[] arrayChar = userCPF.toCharArray();
        int[] arrayInt = new int[arrayChar.length];
        int[] arrayCPF = new int[11];

        for (int i = 0; i < 11; i++) {
            arrayInt[i] = Integer.parseInt(String.valueOf(arrayChar[i]));
        }

        if (userCPF.equals("00000000000") || userCPF.equals("11111111111") || userCPF.equals("22222222222")
                || userCPF.equals("33333333333") || userCPF.equals("44444444444") || userCPF.equals("55555555555")
                || userCPF.equals("66666666666") || userCPF.equals("77777777777") || userCPF.equals("88888888888")
                || userCPF.equals("99999999999") || (userCPF.length() != 11)) {

            return false;
        } else {

            // Calculating the First Digit Checker
            for (int i = 0; i < 9; i++) {
                setDigitChecker1(getDigitChecker1() + arrayInt[i] * first);
                arrayCPF[i] = arrayInt[i];
                first--;
            }
            setDigitChecker1(getDigitChecker1() % 11);
            setDigitChecker1((11 - getDigitChecker1()));

            if (getDigitChecker1() > 9) {
                setDigitChecker1(0);
                arrayCPF[9] = 0;
            } else {
                arrayCPF[9] = getDigitChecker1();
            }

            // Calculating the second Digit Checker
            for (int i = 0; i < 10; i++) {
                setDigitChecker2(getDigitChecker2() + arrayCPF[i] * second);
                second--;
            }
            setDigitChecker2(getDigitChecker2() % 11);
            setDigitChecker2((11 - getDigitChecker2()));

            if (getDigitChecker2() > 9) {
                setDigitChecker2(0);
                arrayCPF[10] = 0;
            } else {
                arrayCPF[10] = getDigitChecker2();
            }

            return Arrays.equals(arrayCPF, arrayInt);
        }
    }

    public int getDigitChecker1() {
        return digitChecker1;
    }

    public void setDigitChecker1(int digitChecker1) {
        this.digitChecker1 = digitChecker1;
    }

    public int getDigitChecker2() {
        return digitChecker2;
    }

    public void setDigitChecker2(int digitChecker2) {
        this.digitChecker2 = digitChecker2;
    }
}
