package main.globalsoftwaresupport.constants;

public class Constants {
    private Constants() {
    }

    // este é o número de zeros à esquerda que o hash deve ter
    public static final int DIFFICULTY = 5;
    // este é o hash (SHA-256) do hash anterior do primeiro bloco
    public static final String GENESIS_PREV_HASH = "0000000000000000000000000000000000000000000000000000000000000000";
    // quantidade de BTCs que os mineradores obtêm após a mineração de um bloco
    // 2009: recompensa = 50 BTC
    public static final double MINER_REWARD = 6.25;
}
