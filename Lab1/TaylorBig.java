package pr_01_09;

import java.math.BigDecimal;
import java.math.BigInteger;

public class TaylorBig {

    public static BigDecimal calculate(BigDecimal x, BigInteger k) {
        int accuracy = k.intValue() + 10;
        BigDecimal eps = BigDecimal.ONE.divide(BigDecimal.TEN.pow(k.intValue()), accuracy, BigDecimal.ROUND_HALF_UP);

        BigDecimal res = BigDecimal.ZERO;
        BigDecimal part = BigDecimal.ONE;
        int n = 0;

        while (part.abs().compareTo(eps) >= 0) {
            res = res.add(part);
            n++;

            BigDecimal num = part.multiply(x.pow(2).negate());
            BigInteger denom = BigInteger.valueOf(2L * n - 1).multiply(BigInteger.valueOf(2L * n));
            part = num.divide(new BigDecimal(denom), accuracy, BigDecimal.ROUND_HALF_UP);
        }

        return res;
    }
}