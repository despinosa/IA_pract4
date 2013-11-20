/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package practica4;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author daniel
 */
public class ID3Test {
    ID3 instance;
    String[] attributes;
    HashMap<String[], Boolean> kb;
    
    @SuppressWarnings("empty-statement")
    public ID3Test() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        kb = new HashMap<String[], Boolean>();
        String[] pattern = {"sunny", "hot", "high", "weak"};
        kb.put(pattern, false);
        pattern[0] = "sunny";
        pattern[1] = "hot";
        pattern[2] = "high";
        pattern[3] = "strong";
        kb.put(pattern, false);
        pattern[0] = "overcast";
        pattern[1] = "hot";
        pattern[2] = "high";
        pattern[3] = "weak";
        kb.put(pattern, true);
        pattern[0] = "rain";
        pattern[1] = "mild";
        pattern[2] = "high";
        pattern[3] = "weak";
        kb.put(pattern, true);
        pattern[0] = "rain";
        pattern[1] = "cool";
        pattern[2] = "normal";
        pattern[3] = "weak";
        kb.put(pattern, true);
        pattern[0] = "rain";
        pattern[1] = "cool";
        pattern[2] = "normal";
        pattern[3] = "strong";
        kb.put(pattern, false);
        pattern[0] = "rain";
        pattern[1] = "cool";
        pattern[2] = "normal";
        pattern[3] = "strong";
        kb.put(pattern, false);
        pattern[0] = "overcast";
        pattern[1] = "cool";
        pattern[2] = "normal";
        pattern[3] = "strong";
        kb.put(pattern, true);
        pattern[0] = "sunny";
        pattern[1] = "mild";
        pattern[2] = "high";
        pattern[3] = "weak";
        kb.put(pattern, false);
        pattern[0] = "sunny";
        pattern[1] = "cool";
        pattern[2] = "normal";
        pattern[3] = "weak";
        kb.put(pattern, true);
        pattern[0] = "rain";
        pattern[1] = "mild";
        pattern[2] = "normal";
        pattern[3] = "weak";
        kb.put(pattern, true);
        pattern[0] = "sunny";
        pattern[1] = "mild";
        pattern[2] = "normal";
        pattern[3] = "strong";
        kb.put(pattern, true);
        pattern[0] = "overcast";
        pattern[1] = "mild";
        pattern[2] = "high";
        pattern[3] = "strong";
        kb.put(pattern, true);
        pattern[0] = "overcast";
        pattern[1] = "hot";
        pattern[2] = "normal";
        pattern[3] = "weak";
        kb.put(pattern, true);
        pattern[0] = "rain";
        pattern[1] = "mild";
        pattern[2] = "high";
        pattern[3] = "strong";
        kb.put(pattern, false);
        attributes =  new String[4];
        attributes[0] = "outlook";
        attributes[1] = "temperature";
        attributes[2] = "humidity";
        attributes[3] = "wind";
        try {
            instance = new ID3(attributes, kb);
        } catch (UnexpectedValueException e) {}
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of generateTree method, of class ID3.
     */
    @Test
    public void testGenerateTree() {
        System.out.println("generateTree");
        try {
            instance.generateTree();
        } catch (UnexpectedActionException ex) {
            Logger.getLogger(ID3Test.class.getName()).log(Level.INFO, null, ex);
        }
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of evaluate method, of class ID3.
     */
    @Test
    public void testEvaluate_StringArr() throws Exception {
        System.out.println("evaluate");
        String[] testPattern = {"sunny", "mild", "high", "strong"};
        Boolean expResult = false;
        Boolean result = instance.evaluate(testPattern);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of evaluate method, of class ID3.
     */
    @Ignore
    public void testEvaluate_InputStream() throws Exception {
        System.out.println("evaluate");
        InputStream is = null;
        ID3 instance = null;
        Boolean[] expResult = null;
        Boolean[] result = instance.evaluate(is);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of load method, of class ID3.
     */
    @Ignore
    public void testLoad() throws Exception {
        System.out.println("load");
        InputStream is = null;
        ID3 instance = null;
        instance.load(is);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of dump method, of class ID3.
     */
    @Ignore
    public void testDump() throws Exception {
        System.out.println("dump");
        OutputStream os = null;
        ID3 instance = null;
        instance.dump(os);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAttributes method, of class ID3.
     */
    @Test
    public void testGetAttributes() {
        System.out.println("getAttributes");
        String[] expResult = attributes;
        String[] result = instance.getAttributes();
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail(".");
    }

    /**
     * Test of setKnowledgeBase method, of class ID3.
     */
    @Test
    public void testSetKnowledgeBase() throws Exception {
        System.out.println("setKnowledgeBase");
        instance.setKnowledgeBase(kb);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getKnowledgeBase method, of class ID3.
     */
    @Test
    public void testGetKnowledgeBase() {
        System.out.println("getKnowledgeBase");
        HashMap expResult = kb;
        HashMap result = instance.getKnowledgeBase();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("Wrong knowledge base.");
    }
}