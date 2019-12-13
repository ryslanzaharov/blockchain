package com.chain.demo.algorithm;

import com.chain.demo.models.Block;

import java.util.List;

public class NoobChain {

    public static int difficulty = 5;

    public static String isChainValid(List<Block> blockchain) {
        Block currentBlock;
        Block previousBlock;
        String hashTarget = new String(new char[difficulty]).replace('\0', '0');
        String error;

        //перебираем блок цепочку для проверки хешей
        for (int i = 1; i < blockchain.size(); i++) {
            currentBlock = blockchain.get(i);
            previousBlock = blockchain.get(i - 1);
            //сравнение зарегестрированного и вычисленного хеша
            if (!currentBlock.hash.equals(currentBlock.calculateHash())) {
                error = ". Зарегестрированный и вычисленный хеши не соответствуют";
                return "нет" + error;
            }
            //сравнение предыдущего хеша и зарегистрированного предыдущего хеша
            if (!previousBlock.hash.equals(currentBlock.previousHash)) {
                error = ". Предыдущий хеш и зарегистрированный предыдущий хеши не соответствуют";
                return "нет" + error;
            }
            //проверка на уровень сложности
            if (!currentBlock.hash.substring(0, difficulty).equals(hashTarget)) {
                error = ". Этот блок не соответсвует уровню сложности либо  не является зарегестрированным";
                return "нет" + error;
            }
        }
        return "да";
    }
}