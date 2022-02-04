package edu.emory.cs.algebraic;

import java.util.Arrays;

public class LongIntegerQuiz extends LongInteger {

    public LongIntegerQuiz(LongInteger n) { super(n); }

    public LongIntegerQuiz(String n) { super(n); }

    @Override
    protected void addDifferentSign(LongInteger n) {

        int m = Math.max(digits.length, n.digits.length);
        byte[] result = new byte[m + 1];

        // this < n
        if(compareAbs(n) < 0){

            // n goes on top
            System.arraycopy(n.digits, 0, result, 0, n.digits.length);

            //subtract this from n
            for(int i = 0; i < digits.length; i++) {
                result[i] -= digits[i];
                if (result[i] < 0) {
                    result[i] += 10;
                    result[i + 1] -= 1;
                }
            }

            //commits answer
            int z = 0;
            for (byte b : result) {
                if (b == 0) {
                    z++;
                }
            }
            digits = result[m] == 0 ? Arrays.copyOf(result, result.length-z) : result;

            //if this is pos, result is neg
            sign = isPositive() ? Sign.NEGATIVE : Sign.POSITIVE;

        }

        // this > n
        else if (compareAbs(n) > 0){

            // this goes on top
            System.arraycopy(digits, 0, result, 0, digits.length);

            //subtract n from this
            for (int i = 0; i < n.digits.length; i++) {
                result[i] -= n.digits[i];
                if (result[i] < 0) {
                    result[i] += 10;
                    result[i + 1] -= 1;
                }
            }

            //commits answer
            int z = 0;
            for (byte b : result) {
                if (b == 0) {
                    z++;
                }
            }
            digits = result[m] == 0 ? Arrays.copyOf(result, result.length-z) : result;


            //if this is pos, result is pos
            sign = isPositive() ? Sign.POSITIVE : Sign.NEGATIVE;


        }

        // |this| == |n|
        else{

            //commits answer
            digits = new byte[]{0};

            sign = isPositive() ? Sign.POSITIVE : Sign.NEGATIVE;
        }
    }
}
