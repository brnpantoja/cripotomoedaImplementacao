package main.globalsoftwaresupport.blockchain;

import main.globalsoftwaresupport.cryptocurrency.TransactionOutput;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Blockchain {

    // este é o livro-razão público - todos podem acessar
    // todos os blocos (transações anteriores) no blockchain
    public static List<Block> blockChain;
    // armazena todas as transações não gastas
    public static Map<String, TransactionOutput> UTXOs;

    public Blockchain() {
        Blockchain.UTXOs = new HashMap<String, TransactionOutput>();
        Blockchain.blockChain = new ArrayList<>();
    }

    public void addBlock(Block block) {
        Blockchain.blockChain.add(block);
    }

    public int size() {
        return Blockchain.blockChain.size();
    }

    @Override
    public String toString() {
        String blockChain = "";
        for (Block block : Blockchain.blockChain)
            blockChain += block.toString()+"\n";

        return blockChain;

    }
}
