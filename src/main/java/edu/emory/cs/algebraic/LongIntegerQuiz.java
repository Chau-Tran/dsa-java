package edu.emory.cs.algebraic;

import java.util.Arrays;

public class LongIntegerQuiz extends LongInteger {

    public LongIntegerQuiz(LongInteger n) { super(n); }

    public LongIntegerQuiz(String n) { super(n); }

    @Override
    protected void addDifferentSign(LongInteger n) {

        int m = Math.max(digits.length, n.digits.length);
        byte[] result = new byte[m + 1];

        //same absolute values, just different signs, would cancel out and yield 0
        if (compareAbs(n) == 0) {
            result[0] = 0;
        }
        //if THIS number is less than N number, copy the greater value, N, into result and subtract THIS.
        else if(compareAbs(n) < 0){

            System.arraycopy(n.digits, 0, result, 0, n.digits.length);

            //subtract THIS from n
            for(int i = 0; i < digits.length; i++) {
                result[i] -= digits[i];
                if (result[i] < 0) {
                    result[i] += 10;
                    result[i + 1] -= 1;
                }
            }

            //top number, N, is negative, and a smaller, positive THIS number is getting subtracted, result will still yield a negative
            if(n.sign == Sign.NEGATIVE){
                sign = Sign.NEGATIVE;
            }
            //else if the top number, N, is a large, positive number, and a smaller, negative THIS number is being subtracted, the result will still be positive
            else{
                sign = Sign.POSITIVE;
            }

            digits = result[m] == 0 ? Arrays.copyOf(result, m) : result;
        }

        //else if THIS number is greater than N number, copy the greater value, THIS, into result and subtract N.
        else{

            System.arraycopy(digits, 0, result, 0, digits.length);

            //subtract N from THIS
            for(int i = 0; i < n.digits.length; i++){
                result[i] -= n.digits[i];
                if(result[i]<0){
                    result[i] += 10;
                    result[i+1] -= 1;
                }
            }

            //if top number, THIS, is negative, result would be negative bc a large negative subtracted by a smaller positive yields a negative
            if(this.sign == Sign.NEGATIVE){
                sign = Sign.NEGATIVE;
            }
            //if top number, THIS, is positive, then the bottom number, N, is negative. However, since the larger number THIS is positive, getting subtracted by a smaller N number, will still yield a positive result
            else {
                sign = Sign.POSITIVE;
            }

            digits = result[m] == 0 ? Arrays.copyOf(result, m) : result;

        }
    }
}
