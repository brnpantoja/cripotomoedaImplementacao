package main.globalsoftwaresupport.cryptocurrency;

import java.security.*;
import java.security.spec.ECGenParameterSpec;

public class CryptographyHelper {

    //Hash SHA-256 (256 bits = 64 caracteres hexadecimais)
    public static String generateHash(String data) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(data.getBytes("UTF-8"));

            //acabar com valores hexadecimais e não bytes
            StringBuffer hexadecimalString = new StringBuffer();
            for (int i = 0; i < hash.length; i++) {
                String hexadecimal = Integer.toHexString(0xff & hash[i]);
                if (hexadecimal.length() == 1) hexadecimalString.append('0');
                hexadecimalString.append(hexadecimal);
            }
            return hexadecimalString.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    // gerar chave pública e chave privada
    // chave privada: inteiro aleatório de 256 bits
    // chave pública: ponto na curva elíptica
    // chave pública = chave privada * ponto na curva elíptica
    public static KeyPair ellipticCurveCrypto() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("ECDSA", "BC");
            ECGenParameterSpec params = new ECGenParameterSpec("prime256v1");
            keyPairGenerator.initialize(params);
            return keyPairGenerator.generateKeyPair();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // ECC para assinar a transação dada (mensagem)
    // algoritmo de assinatura digital de curva elíptica (ECDSA)
    public static byte[] sign(PrivateKey privateKey, String input) {
        Signature signature;
        byte[] output = new byte[0];
        try {
            // castelo insuflável para ECC
            signature = Signature.getInstance("ECDSA", "BC");
            signature.initSign(privateKey);
            signature.update(input.getBytes());
            output = signature.sign();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return output;
    }
    // verifica se a transação determinada pertence ao remetente com base na assinatura
    public static boolean verify(PublicKey publicKey, String data, byte[] signature) {
        try {
            Signature ecdsaSignature = Signature.getInstance("ECDSA", "BC");
            ecdsaSignature.initVerify(publicKey);
            ecdsaSignature.update(data.getBytes());
            return ecdsaSignature.verify(signature);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

