package com.chain.demo.models;

import com.chain.demo.algorithm.StringUtil;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "blockchain")
@Data
public class Block {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "hash")
    public String hash;

    @Column(name = "previous_hash")
    public String previousHash;

    @Column(name = "data")
    private String data;

    @Column(name = "create_date")
    private Timestamp timeStamp;

    @Column(name = "nonce")
    private int nonce;

    public Block() {
    }

    public Block(String data, String previousHash) {
        this.data = data;
        this.previousHash = previousHash;
        this.timeStamp = new Timestamp(System.currentTimeMillis());

        this.hash = calculateHash();
    }

    public String calculateHash() {
        String calculatedhash = StringUtil.applySha256(
                previousHash +
                        timeStamp.getTime() +
                        Integer.toString(nonce) +
                        data
        );
        return calculatedhash;
    }

    //создаем хеш с уровне сложности difficulty
    public void mineBlock(int difficulty) {
        String target = new String(new char[difficulty]).replace('\0', '0');
        while (!hash.substring(0, difficulty).equals(target)) {
            nonce++;
            hash = calculateHash();
        }
    }
}
