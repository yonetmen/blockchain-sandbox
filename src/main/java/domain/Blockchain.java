package domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Blockchain {

    private int difficulty;
    private List<Block> blockList;

    public Blockchain(int difficulty) {
        this.difficulty = difficulty;
        blockList = new ArrayList<>();
        // First Block
        Block block = new Block(0, System.currentTimeMillis(), null, "First Block");
        block.mineBlock(difficulty);
        blockList.add(block);
    }

    public Block getLatestBlock() {
        return this.blockList.get(blockList.size() - 1);
    }

    public Block createNewBlock(String data) {
        Block latest = getLatestBlock();
        return new Block(latest.getIndex() + 1, System.currentTimeMillis(),
                getLatestBlock().getHash(), data);
    }

    public void addBlock(Block block) {
        if (block != null) {
            block.mineBlock(difficulty);
            blockList.add(block);
        }
    }

    public boolean isFirstBlockValid() {
        Block firstBlock = blockList.get(0);

        if (firstBlock.getIndex() != 0) {
            return false;
        }

        if (firstBlock.getPreviousHash() != null) {
            return false;
        }

        if (firstBlock.getHash() == null || !Block.calculateHash(firstBlock).equals(firstBlock.getHash())) {
            return false;
        }

        return true;
    }

    public boolean isValidNewBlock(Block newBlock, Block previousBlock) {

        if(newBlock != null && previousBlock != null) {
            if(previousBlock.getIndex()+1 != newBlock.getIndex()) {
                return false;
            }

            if(newBlock.getHash() == null || newBlock.getPreviousHash() == null || previousBlock.getHash() == null) {
                return false;
            }

            if(!newBlock.getPreviousHash().equals(previousBlock.getHash())) {
                return false;
            }

            if(!Block.calculateHash(newBlock).equals(newBlock.getHash())) {
                return false;
            }

            if(!Block.calculateHash(previousBlock).equals(previousBlock.getHash())) {
                return false;
            }

            if(!Block.calculateHash(previousBlock).equals(newBlock.getPreviousHash())) {
                return false;
            }

            return true;
        }
        return false;
    }

    public boolean isBlockChainValid() {
        if(!isFirstBlockValid()) {
            return false;
        }

        for (int i = 0; i < blockList.size(); i++) {
            Block current = blockList.get(i);
            Block previous = blockList.get(i-1);

            if(!isValidNewBlock(current, previous)) {
                return false;
            }
        }

        return true;
    }
}
