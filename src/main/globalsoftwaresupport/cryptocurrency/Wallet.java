package main.globalsoftwaresupport.cryptocurrency;

import main.globalsoftwaresupport.blockchain.Blockchain;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Wallet {

    // usuários da rede
    // usado para assinatura
    private PrivateKey privateKey;
    // verificação da assinatura
    // endereço: chave pública RIPMD (160 bits)
    private PublicKey publicKey;

    public Wallet() {
        KeyPair keyPair = CryptographyHelper.ellipticCurveCrypto();
        this.privateKey = keyPair.getPrivate();
        this.publicKey = keyPair.getPublic();
    }

    ///PODEMOS TRANSFERIR DINHEIRO PARA OUTRA CARTEIRA
    // mineradores do blockchain colocarão essa transação no blockchain
    public Transaction transferMoney(PublicKey receiver, double amount) {

        if (calculateBalance() < amount) {
            System.out.println("Transação inválida por falta de dinheiro...");
            return null;
        }

        // we store the inputs for the transaction in this array
        List<TransactionInput> inputs = new ArrayList<TransactionInput>();

        // vamos encontrar nossas transações não gastas
        for (Map.Entry<String, TransactionOutput> item : Blockchain.UTXOs.entrySet()) {
            TransactionOutput UTXO = item.getValue();
            if (UTXO.isMine(publicKey))
                inputs.add(new TransactionInput(UTXO.getId()));
        }
        // vamos criar a nova transação
        Transaction newTransaction = new Transaction(publicKey, receiver, amount, inputs);
        // o remetente assina a transação
        newTransaction.generateSignature(privateKey);

        return newTransaction;
    }


    // não há saldo associado aos usuários
    // UTXOs e considere todas as transações no passado
    public double calculateBalance() {
        double balance = 0;
        for (Map.Entry<String, TransactionOutput> item : Blockchain.UTXOs.entrySet()) {
            TransactionOutput transactionOutput = item.getValue();
            if (transactionOutput.isMine(publicKey))
                balance += transactionOutput.getAmount();

        }
        return balance;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }
}
