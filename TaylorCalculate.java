package pr_01_09;

import java.util.Scanner;
import java.util.Formatter;

public class TaylorCalculate {

    public static double calculate(double x, int k){
        x = toPiRange(x);

        double eps = Math.pow(10, -k);
        double res = 0;
        double part = 1;
        int n = 0;

        while(Math.abs(part) >= eps ){
            res += part;
            n++;
            part = part * (-x * x) / ((2 * n - 1) * (2 * n));
        }

        return res;
    }

    private static double toPiRange(double x) {
        double twoPi = 2 * Math.PI;
        x = x % twoPi;

        if (x > Math.PI) {
            x -= twoPi;
        } else if (x < -Math.PI) {
            x += twoPi;
        }

        return x;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Введите число x: ");
        double x = sc.nextDouble();

        System.out.print("Введите натуральное число k: ");
        int k = sc.nextInt();

        if (k <= 0) {
            System.out.println("k должно быть натуральным числом");
            sc.close();
            return;
        }

        double result1 = TaylorCalculate.calculate(x, k);
        double result2 = Math.cos(x);

        System.out.println("\n");

        Formatter f = new Formatter();

        int xRound = (int) Math.round(x);

        f.format("х = %d%n", xRound);
        f.format("х в восьмеричном виде = %#o%n", xRound);
        f.format("х в шестнадцатеричном виде =  %#x%n", xRound);

        int accuracy = k + 1;

        f.format("%nx = %.6f%n", x);
        f.format("Вычисление через ряд Тейлора = %+06." + accuracy + "f%n", result1);
        f.format("Вычисление через Math.cos() = %+06." + accuracy + "f%n", result2);

        System.out.print(f);
        f.close();

        sc.close();
    }

}
