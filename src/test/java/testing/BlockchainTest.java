package testing;

import app.Blockchain;
import domain.Block;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BlockchainTest {

    private Blockchain application;

    @Before
    public void setup() {
        application = new Blockchain(4);
    }

    @After
    public void teardown() {
        application = null;
    }

    @Test
    public void createNewBlock_ThenVerifyGenesisBlockNotNull() {
        Block genesisBlock = application.createNewBlock("This is genesis block");
        application.addBlock(genesisBlock);

        assertNotNull("Genesis block is not present", application.getBlockList().get(0));
        assertEquals("Not current data", application.getBlockList().get(0).getData(), "First Block");
        assertEquals(2, application.getBlockList().size());
    }

    @Test
    public void createApplication_ThenVerifyNoPreviousBlock() {

        assertNull("This is not Genesis Block. There is a previous Block",
                application.getBlockList().get(0).getPreviousHash());
    }
}
