package pr_01_09;

import java.io.BufferedReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Formatter;

public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new java.io.InputStreamReader(System.in));

        System.out.print("Введите число x: ");
        BigDecimal x;
        try{
            x  = new BigDecimal(reader.readLine());
        }
        catch (NumberFormatException exc){
            System.out.println("Ошибка: x должен быть числом");
            return;
        }

        System.out.print("Введите натуральное число k: ");
        BigInteger k;
        try {
            k = new BigInteger(reader.readLine());
            if (k.compareTo(BigInteger.ZERO) <= 0) {
                System.out.println("Ошибка: k должно быть натуральным числом");
                return;
            }
        }
        catch (NumberFormatException exc){
            System.out.println("Ошибка: k должно быть натуральным числом");
            return;
        }

        BigDecimal result1 = TaylorBig.calculate(x, k);
        BigDecimal result2 = new BigDecimal(Math.cos(x.doubleValue())).setScale(k.intValue() + 10, BigDecimal.ROUND_HALF_UP);

        System.out.println("\n");

        Formatter f = new Formatter();

        int xRound = x.setScale(0, BigDecimal.ROUND_HALF_UP).intValue();

        f.format("х = %d\n", xRound);
        f.format("х в восьмеричном виде = %#o\n", xRound);
        f.format("х в шестнадцатеричном виде = %#x\n", xRound);

        int accuracy = k.intValue() + 1;

        f.format("\nx = %s\n", x);
        f.format("k = %s\n", k);
        f.format("Вычисление через ряд Тейлора = %+06." + accuracy + "f\n", result1);
        f.format("Вычисление через Math.cos() = %+06." + accuracy + "f\n", result2);

        System.out.print(f);
        f.close();
    }
}