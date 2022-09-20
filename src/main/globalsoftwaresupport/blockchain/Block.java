package main.globalsoftwaresupport.blockchain;

import main.globalsoftwaresupport.constants.Constants;
import main.globalsoftwaresupport.cryptocurrency.CryptographyHelper;
import main.globalsoftwaresupport.cryptocurrency.Transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Block {

    private int id;
    private int nonce;
    private long timeStamp;
    private String hash;
    private String previousHash;
    // Ethereum cada bloco armazena 1500-2000 transações
    public List<Transaction> transactions;

    public Block(String previousHash){
        this.transactions = new ArrayList<>();
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();
        generateHash();
    }

    public void generateHash() {
        String dataToHash = Integer.toString(id) + previousHash + Long.toString(timeStamp) +
                transactions.toString() + Integer.toString(nonce);
        this.hash = CryptographyHelper.generateHash(dataToHash);
    }
    // verificar se a transação dada é válida ou não
    public boolean addTransaction(Transaction transaction) {
        if (transaction == null) return false;

        // se o bloco é o bloco de gênese não processamos
        if ((!previousHash.equals(Constants.GENESIS_PREV_HASH))) {
            if ((!transaction.verifyTransaction())) {
                System.out.println("A transação não é válida...");
                return false;
            }
        }
        transactions.add(transaction);
        System.out.println("A transação é válida e é adicionada ao bloco "+this);
        return true;

    }
    // o nonce é o único parâmetro que os mineradores podem ajustar (alterar)
    public void incrementNonce(){
        this.nonce++;
    }
    // este hash SHA-256 identifica o bloco
    public String getHash(){
        return this.hash;
    }

}
