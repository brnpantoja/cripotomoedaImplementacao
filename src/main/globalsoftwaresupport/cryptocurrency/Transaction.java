package main.globalsoftwaresupport.cryptocurrency;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class Transaction {
    // id da transação é um hash
    private String transactionId;
    // uso de PublicKeys para referenciar o remetente ou o destinatário
    private PublicKey sender;
    private PublicKey receiver;
    // quantidade de moedas que a transação envia para o destinatário do remetente
    private double amount;
    // certificar de que a transação esteja assinada para evitar que outra pessoa gaste as moedas
    private byte[] signature;

    // toda transação tem entradas e saídas
    public List<TransactionInput> inputs;
    public List<TransactionOutput> outputs;

    public Transaction(PublicKey sender, PublicKey receiver, double amount, List<TransactionInput> inputs) {
        this.inputs = new ArrayList<TransactionInput>();
        this.outputs = new ArrayList<TransactionOutput>();
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.inputs = inputs;
        calculateHash();
    }
}
