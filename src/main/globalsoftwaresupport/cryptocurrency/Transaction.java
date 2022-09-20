package main.globalsoftwaresupport.cryptocurrency;

import main.globalsoftwaresupport.blockchain.Blockchain;

import java.security.PrivateKey;
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

    public boolean verifyTransaction() {
        if (!verifySignature()) {
            System.out.println("Transações inválidas devido a assinatura inválida...");
            return false;
        }
        // reunir transações não gastas (tem que considerar as entradas)
        for (TransactionInput transactionInput : inputs)
            transactionInput.setUTXO(Blockchain.UTXOs.get(transactionInput.getTransactionOutputId()));

        // as transações têm 2 partes: enviar um valor para o destinatário + enviar o (valor do saldo)
        // de volta ao remetente
        // enviar valor ao destinatário
        outputs.add(new TransactionOutput(this.receiver, amount, transactionId));
        // envie o que sobrou de 'mudança' de volta ao remetente
        outputs.add(new TransactionOutput(this.sender, getInputsSum() - amount, transactionId));

        // TEMOS QUE ATUALIZAR A LISTA UTXO
        // as saídas serão entradas para outras transações
        for (TransactionOutput transactionOutput : outputs)
            Blockchain.UTXOs.put(transactionOutput.getId(), transactionOutput);

        // remover entradas de transação da lista UTXOs do blockchain porque elas foram gastas
        for (TransactionInput transactionInput : inputs)
            if (transactionInput.getUTXO() != null)
                Blockchain.UTXOs.remove(transactionInput.getUTXO().getId());

        return true;

    }

    // é assim que calculamos quanto dinheiro o remetente tem
    // temos que considerar as transações no pas
    private double getInputsSum() {
        double sum = 0;

        for (TransactionInput transactionInput : inputs)
            if (transactionInput.getUTXO() != null)
                sum += transactionInput.getUTXO().getAmount();
        return sum;

    }

    public void generateSignature(PrivateKey privateKey) {
        String data = sender.toString() + receiver.toString() + Double.toString(amount);
        signature = CryptographyHelper.sign(privateKey, data);

    }

    public boolean verifySignature() {
        String data = sender.toString() + receiver.toString() + Double.toString(amount);
        return CryptographyHelper.verify(sender, data, signature);

    }

    private void calculateHash() {
        String hashData = sender.toString() + receiver.toString() + Double.toString(amount);
        this.transactionId = CryptographyHelper.generateHash(hashData);
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public PublicKey getSender() {
        return sender;
    }

    public void setSender(PublicKey sender) {
        this.sender = sender;
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

    public byte[] getSignature() {
        return signature;
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }

    public List<TransactionInput> getInputs() {
        return inputs;
    }

    public void setInputs(List<TransactionInput> inputs) {
        this.inputs = inputs;
    }

    public List<TransactionOutput> getOutputs() {
        return outputs;
    }

    public void setOutputs(List<TransactionOutput> outputs) {
        this.outputs = outputs;
    }
}
