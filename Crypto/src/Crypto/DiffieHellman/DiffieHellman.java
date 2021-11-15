/*
 * Copyright (c) Paque Eric Ugo
 */
package Crypto.DiffieHellman;

import java.math.BigInteger;
import java.util.Random;

public class DiffieHellman {


    private static boolean isPrime(int inputNum){
        if (inputNum <= 3 || inputNum % 2 == 0)
            return inputNum == 2 || inputNum == 3; //this returns false if number is <=1 & true if number = 2 or 3
        int divisor = 3;
        while ((divisor <= Math.sqrt(inputNum)) && (inputNum % divisor != 0))
            divisor += 2; //iterates through all possible divisors
        return inputNum % divisor != 0; //returns true/false
    }
    private static BigInteger generateRandomPrimeNumber()
    {
        return generateRandomPrimeNumber(1,100000);
    }
    private static BigInteger generateRandomPrimeNumber(int boundMin, int boundMax)
    {
        if(boundMin<1)
            throw new IllegalArgumentException("BoundMin less than 0");
        int num = 0;
        Random rand = new Random();
        num = rand.nextInt(boundMax +1 - boundMin) + boundMin;

        while(!isPrime(num)){
            num=rand.nextInt(boundMax +1 - boundMin) + boundMin;
        }
        return BigInteger.valueOf(num);
    }

    public static BigInteger generateRandomNumber()
    {
        return generateRandomNumber(1,100000);
    }
    public static BigInteger generateRandomNumber(int boundMin, int boundMax)
    {
        if(boundMin<1)
            throw new IllegalArgumentException("BoundMin less than 0");
        Random rand = new Random();
        return BigInteger.valueOf(rand.nextInt(boundMax +1 - boundMin) + boundMin);
    }

    public static NP generateNP(int boundMin, int boundMax) throws IllegalArgumentException
    {
        BigInteger a1 = generateRandomPrimeNumber(boundMin, boundMax);
        BigInteger a2 = generateRandomPrimeNumber(boundMin, boundMax);
        while(a1==a2)
            a2 = generateRandomPrimeNumber(boundMin,boundMax);

        if(a1.intValue()<a2.intValue())
        {
            return new NP(a2,a1);
        }
        return new NP(a1,a2);
    }

    /// (a1^a3) % a2
    public static BigInteger getKey(BigInteger a1 , BigInteger a2, BigInteger a3)
    {
        return BigInteger.valueOf((long)Math.pow(a1.doubleValue(),a3.longValue())%a2.longValue());
    }


}
