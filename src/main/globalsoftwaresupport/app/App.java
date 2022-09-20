package main.globalsoftwaresupport.app;

import main.globalsoftwaresupport.cryptocurrency.CryptographyHelper;
import org.bouncycastle.jce.provider.BouncyCastleProvider;


import java.security.KeyPair;
import java.security.Security;
import java.util.Base64;

public class App {
    public static void main(String[] args) {
        // usamos o castelo inflável e o provedor relacionado à criptografia
        Security.addProvider(new BouncyCastleProvider());



        KeyPair keys = CryptographyHelper.ellipticCurveCrypto();

        System.out.println(Base64.getEncoder().encodeToString(keys.getPrivate().getEncoded()));
        System.out.println(Base64.getEncoder().encodeToString(keys.getPublic().getEncoded()));

    }

}
