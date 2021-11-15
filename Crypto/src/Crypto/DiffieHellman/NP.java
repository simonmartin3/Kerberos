/*
 * Copyright (c) Paque Eric Ugo
 */

package Crypto.DiffieHellman;

import java.io.Serializable;
import java.math.BigInteger;

public class NP implements Serializable {

    private BigInteger n;
    private BigInteger p;

    public NP(BigInteger n, BigInteger p)
    {
        this.n = n;
        this.p = p;
    }

    public BigInteger getN() {
        return n;
    }

    public void setN(BigInteger n) {
        this.n = n;
    }

    public BigInteger getP() {
        return p;
    }

    public void setP(BigInteger p) {
        this.p = p;
    }

    @Override
    public String toString() {
        return "NP{" +
                "n=" + n +
                ", p=" + p +
                '}';
    }
}
