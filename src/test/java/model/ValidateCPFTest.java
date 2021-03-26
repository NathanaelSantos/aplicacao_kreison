package model;

import org.junit.Test;
import org.junit.Assert;

public class ValidateCPFTest {
    ValidateCPF validateCPF = new ValidateCPF();

    @Test
    public void validate() {

        boolean actual = validateCPF.validate("66208146038");
        boolean expected = true;

        Assert.assertEquals(expected,actual);

        boolean actual2 = validateCPF.validate("00000000000");
        boolean expected2 = false;

        Assert.assertEquals(expected2,actual2);

    }

    @Test
    public void getDigitChecker1() {
    }

    @Test
    public void setDigitChecker1() {
    }

    @Test
    public void getDigitChecker2() {
    }

    @Test
    public void setDigitChecker2() {
    }
}