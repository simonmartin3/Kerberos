/*
 * Copyright (c) Paque Eric Ugo
 */

package Crypto.DiffieHellmanNetwork;



import Crypto.DiffieHellman.DiffieHellman;
import Crypto.DiffieHellman.NP;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;

public class DiffieHellmanNetwork {

    public static BigInteger getSecretKey(ObjectOutputStream out,
                                          ObjectInputStream in,
                                          int boundMin,
                                          int boundMax,
                                          boolean receiver) throws IOException, ClassNotFoundException {
        NP np = null;
        if(receiver)
        {
            while(np==null) {
                Object objReceived = in.readObject();
                if (objReceived instanceof NP)
                    np = (NP) objReceived;
            }
        }
        else{
            np = DiffieHellman.generateNP(boundMin,boundMax);
            out.writeObject(np);
        }
        BigInteger x = DiffieHellman.generateRandomNumber();
        BigInteger publickey = DiffieHellman.getKey(np.getN(),np.getP(),x);
        out.writeObject(publickey);
        BigInteger publicKey2 = null;
        while(publicKey2==null) {
            Object objReceived = in.readObject();
            if (objReceived instanceof BigInteger)
                publicKey2 = (BigInteger)objReceived;
        }
        return DiffieHellman.getKey(publicKey2,np.getP(),x);

    }
}
