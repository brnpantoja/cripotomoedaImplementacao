package main.globalsoftwaresupport.cryptocurrency;

import main.globalsoftwaresupport.blockchain.Block;
import main.globalsoftwaresupport.blockchain.Blockchain;
import main.globalsoftwaresupport.constants.Constants;

public class Miner {
    // cada minerador recebe 6,25 BTC após a mineração
    private double reward;
    public void mine(Block block, Blockchain blockChain){
        // esta é a prova de trabalho (PoW) - PoS
        // não é energeticamente eficiente
        while(!isGoldenHash(block)){
            block.incrementNonce();
            block.generateHash();
        }
        System.out.println(block+" acabou de minerar...");
        System.out.println("O hash do bloco é: "+block.getHash());

        blockChain.addBlock(block);
        reward += Constants.MINER_REWARD;
    }

    public boolean isGoldenHash(Block block){
        String leadingZeros = new String(new char[Constants.DIFFICULTY]).replace('\0', '0');
        return block.getHash().substring(0, Constants.DIFFICULTY).equals(leadingZeros);

    }
    public double getReward(){
        return this.reward;
    }

}
