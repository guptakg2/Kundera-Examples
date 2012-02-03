package com.impetus.kundera.examples.crossdatastore.useraddress;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.impetus.kundera.examples.crossdatastore.useraddress.entities.HabitatBiMToM;
import com.impetus.kundera.examples.crossdatastore.useraddress.entities.PersonnelBiMToM;

public class MTMBiAssociationTest extends TwinAssociation

{

    /**
     * Inits the.
     */
    @BeforeClass
    public static void init()
    {
        List<Class> clazzz = new ArrayList<Class>(2);
        clazzz.add(PersonnelBiMToM.class);
        clazzz.add(HabitatBiMToM.class);
        init(clazzz, "twingo", "twissandra");
    }

    /**
     * Sets the up.
     * 
     * @throws Exception
     *             the exception
     */
    @Before
    public void setUp() throws Exception
    {
        setUpInternal();
    }

    /**
     * Test insert.
     */
    @Test
    public void testInsert()
    {
        tryOperation();
    }

    @Override
    protected void find()
    {
        // TODO Auto-generated method stub

    }

    @Override
    protected void insert()
    {
        // TODO Auto-generated method stub

    }

    /**
     * Test merge.
     */
    @Test
    public void testMerge()
    {

    }

    /**
     * Tear down.
     * 
     * @throws Exception
     *             the exception
     */
    @After
    public void tearDown() throws Exception
    {
        tearDownInternal();
    }
}
