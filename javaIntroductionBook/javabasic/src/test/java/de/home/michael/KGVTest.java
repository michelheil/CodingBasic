package de.home.michael;

import org.junit.Assert;
import org.junit.Test;

public class KGVTest {

    KGV berechnungTest = new KGV();

    @Test
    public void einfacherTest() {
        Assert.assertEquals(36, berechnungTest.berechneKgv(12,18));
    }

    @Test
    public void vielfachesIstParam1() {
        Assert.assertEquals(12, berechnungTest.berechneKgv(12,6));
    }

    @Test
    public void vielfachesIstParam2() {
        Assert.assertEquals(36, berechnungTest.berechneKgv(12,36));
    }

    @Test
    public void parameterGleich() {
        Assert.assertEquals(71, berechnungTest.berechneKgv(71,71));
    }

    @Test(expected = IllegalArgumentException.class)
    public void negativeZahlen() {
        berechnungTest.berechneKgv(-20,30);
    }

    @Test(expected = ArithmeticException.class)
    public void zuGrosseZahlen() {
        berechnungTest.berechneKgv(Integer.MAX_VALUE,(Integer.MAX_VALUE - 1));
    }

}