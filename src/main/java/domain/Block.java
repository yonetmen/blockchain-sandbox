package domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import utils.Utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Data
@NoArgsConstructor
public class Block {

    private int index;
    private long timestamp;
    private String hash;
    private String previousHash;
    private String data;
    private int nonce;

    public Block(int index, long timestamp, String previousHash, String data) {
        this.index = index;
        this.timestamp = timestamp;
        this.previousHash = previousHash;
        this.data = data;
        this.nonce = 0;
        this.hash = calculateHash(this);
    }

    public static String calculateHash(Block block) {
        if (block != null) {
            try {
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                String toBeDigested = block.getStringToBeDigested();
                final byte[] bytes = digest.digest(toBeDigested.getBytes());
                final StringBuilder builder = new StringBuilder();

                for (final byte b : bytes) {
                    String hex = Integer.toHexString(0xff & b);
                    if (hex.length() == 1) {
                        builder.append("0");
                    }
                    builder.append(hex);
                }
                return builder.toString();
            } catch (NoSuchAlgorithmException e) {
                return null;
            }


        }
        return null;
    }

    public void mineBlock(int difficulty) {
        nonce = 0;
        while (!getHash().substring(0, difficulty).equals(Utils.getZerosForDifficulty(difficulty))) {
            nonce++;
            hash = calculateHash(this);
        }
    }

    private String getStringToBeDigested() {
        return index + timestamp + previousHash + data + nonce;
    }
}
