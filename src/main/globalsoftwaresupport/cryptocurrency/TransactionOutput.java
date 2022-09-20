package main.globalsoftwaresupport.cryptocurrency;

import java.security.PublicKey;

public class TransactionOutput {
    // identificador da saída da transação (SHA-256)
    private String id;
    // ID da transação do pai (portanto, a transação em que foi criada)
    private String parentTransactionId;
    // o novo dono da moeda
    private PublicKey receiver;
    // quantidade de moedas
    private double amount;

    public TransactionOutput(String parentTransactionId, PublicKey receiver, double amount) {
        this.parentTransactionId = parentTransactionId;
        this.receiver = receiver;
        this.amount = amount;
        genereateId();
    }
    private void genereateId(){
        this.id = CryptographyHelper.generateHash(receiver.toString()+Double.toString(amount)+parentTransactionId);
    }
    public boolean isMine(PublicKey publicKey){
        return publicKey == receiver;
    }

    public String getId() {
        return id;

    }

    public String getParentTransactionId() {
        return parentTransactionId;
    }

    public void setParentTransactionId(String parentTransactionId) {
        this.parentTransactionId = parentTransactionId;
    }

    public PublicKey getReceiver() {
        return receiver;
    }

    public void setReceiver(PublicKey receiver) {
        this.receiver = receiver;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
