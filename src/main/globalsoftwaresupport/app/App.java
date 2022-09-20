package main.globalsoftwaresupport.app;

import main.globalsoftwaresupport.blockchain.Block;
import main.globalsoftwaresupport.blockchain.Blockchain;
import main.globalsoftwaresupport.constants.Constants;
import main.globalsoftwaresupport.cryptocurrency.Miner;
import main.globalsoftwaresupport.cryptocurrency.Transaction;
import main.globalsoftwaresupport.cryptocurrency.TransactionOutput;
import main.globalsoftwaresupport.cryptocurrency.Wallet;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Security;

public class App {
    public static void main(String[] args) {
        // usamos o castelo inflável e o provedor relacionado à criptografia
        Security.addProvider(new BouncyCastleProvider());

        // crie carteiras + blockchain + o único minerador na rede
        Wallet userA = new Wallet();
        Wallet userB = new Wallet();
        Wallet lender = new Wallet();
        Blockchain chain = new Blockchain();
        Miner miner = new Miner();

        // crie uma transação de gênese que envie 500 moedas para o usuárioA:
        Transaction genesisTransaction = new Transaction(lender.getPublicKey(),
                userA.getPublicKey(), 500, null);
        genesisTransaction.generateSignature(lender.getPrivateKey());
        genesisTransaction.setTransactionId("0");
        genesisTransaction.outputs.add(new TransactionOutput(genesisTransaction.
                getTransactionId(), genesisTransaction.getReceiver(), genesisTransaction.getAmount()));
        Blockchain.UTXOs.put(genesisTransaction.outputs.get(0).getId(), genesisTransaction.outputs.get(0));

        System.out.println("Construindo o bloco de gênese...");
        Block genesis = new Block(Constants.GENESIS_PREV_HASH);
        genesis.addTransaction(genesisTransaction);
        miner.mine(genesis, chain);

        Block block1 = new Block(genesis.getHash());
        System.out.println("\no saldo do usuárioA é: " + userA.calculateBalance());
        System.out.println("\nusuárioA tenta enviar dinheiro (120 moedas) para usuárioB...");
        block1.addTransaction(userA.transferMoney(userB.getPublicKey(), 120));
        miner.mine(block1, chain);
        System.out.println("\no saldo do usuárioA é: " + userA.calculateBalance());
        System.out.println("o saldo do usuárioB é: " + userB.calculateBalance());

        Block block2 = new Block(block1.getHash());
        System.out.println("\nusuárioA envia mais fundos (600) do que tem...");
        block2.addTransaction(userA.transferMoney(userB.getPublicKey(), 600));
        miner.mine(block2, chain);
        System.out.println("\nO saldo do usuárioA é: " + userA.calculateBalance());
        System.out.println("O saldo do userB é: " + userB.calculateBalance());

        Block block3 = new Block(block2.getHash());
        System.out.println("\nusuário está tentando enviar fundos (110) para users...");
        block3.addTransaction(userB.transferMoney(userA.getPublicKey(), 110));
        System.out.println("\no saldo do usuárioA é: " + userA.calculateBalance());
        System.out.println("o saldo do usuárioB é: " + userB.calculateBalance());
        miner.mine(block3, chain);

        System.out.println("Recompensa do Mineiro: " + miner.getReward());
    }

}
