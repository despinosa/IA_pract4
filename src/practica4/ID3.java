package practica4;

import static java.lang.Math.log;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author daniel
 */
public class ID3 {
    /* Implementación del algoritmo de machine learning ID3 empleando n
     * atributos con decisiones binarias. Puede inicializarse conociendo sólo
     * los atributos, los atributos y la base de conocimientos o a partir de un
     * flujo de entrada en formato DOT.
     * 
     * attributes: Atributos a considerar.
     * knowledgeBase: Base de conocimientos empleada para crear el árbol.
     *     Consiste en mapeos patrón -> decisión.
     * decisionTree: Nodo raíz del árbol de decisión.
     */

    private Node[] attributes;
    private HashMap<String[], Boolean> knowledgeBase;
    private Node decisionTree;

    protected class Node {
        /* Clase de los nodos del árbol de decisión. Guarda la información
         * representiva de cada uno.
         * 
         * attribute: Atributo al que representa, en su caso
         * decision: Decisión a la que representa, en su caso. Siempre uno, ya
         *     sea `attribute` o `decision`, será `null`.
         * sons:  Hijos del nodo. Consiste en mapeos valor para el atributo ->
         *     nodo siguiente.
         */

        private String attribute;
        private Boolean decision;
        private HashMap<String, Node> sons;

        public String getAttribute() {
            return attribute;
        }

        public Boolean getDecision() {
            return decision;
        }

        public HashMap<String, Node> getSons() {
            return sons;
        }

        public void setSons(HashMap<String, Node> sons) {
            this.sons = sons;
        }

        public Node(String attribute) {
            this.attribute = attribute;
            decision = null;
            sons = new HashMap<String, Node>();
        }

        public Node(Boolean decision) {
            attribute = null;
            this.decision = decision;
            sons = new HashMap<String, Node>();
        }

        public Node(String attribute, HashMap<String, Node> sons) {
            this.attribute = attribute;
            decision = null;
            this.sons = sons;
        }
    }

    public ID3(String[] attributes) {
        /* Crea una instancia de la implementación del algoritmo, inicializando
         * sólo los nodos atributos a considerar. Será necesario colocar la
         * base de conocimientos y generar el árbol o cargar un árbol desde
         * archivo antes de evaluar patrones.
         */
        this.attributes = new Node[attributes.length];
        for (Integer i = 0; i < attributes.length; i++) {
            this.attributes[i] = new Node(attributes[i]);
        }
        knowledgeBase = null;
        decisionTree = null;
    }

    public ID3(String[] attributes, HashMap<String[], Boolean>
            knowledgeBase) throws UnexpectedValueException {
        /* Crea una instancia de la implementación del algoritmo inicializando
         * todos los valores. Si la longitud de los patrones en la base de
         * conocimientos no corresponde a la longitud del arreglo de atributos,
         * lanza una excepción. Una instancia inicializada con este constructor
         * estará lista para clasificar patrones de entrada.
         */
        this.attributes = new Node[attributes.length];
        for (Integer i = 0; i < attributes.length; i++) {
            this.attributes[i] = new Node(attributes[i]);
        }
        Set<String[]> keys = knowledgeBase.keySet();
        if (!keys.isEmpty()) {
            String[] key = keys.iterator().next();
            if (key.length > attributes.length) {
                throw new UnexpectedValueException(true, null);
            } else if (key.length < attributes.length) {
                throw new UnexpectedValueException(false, null);
            } else {
                this.knowledgeBase = knowledgeBase;
                try {
                    generateTree();
                } catch (UnexpectedActionException ex) {
                    Logger.getLogger(ID3.class.getName()).log(Level.WARNING,
                            null, ex);
                }
            }
        } else {
            this.knowledgeBase = null;
            decisionTree = null;
        }
    }

    public ID3(InputStream is) throws IOException,
            UnexpectedValueException {
        /* Crea una instancia de la implementación del algoritmo a partir de un
         * flujo de entrada en formato DOT. Inicializa los atributos y el árbol
         * de decisión, quedando lista para clasificar patrones. La base de
         * conocimientos permanece vacía.
         */
    }

    public void generateTree() throws UnexpectedActionException {
        /* Construye el árbol de decisión a partir de una base de
         * conocimientos. Si no se encuentra la base de conocimientos, lanza
         * una excepción.
         */
        if (knowledgeBase == null || knowledgeBase.isEmpty()) {
            throw new UnexpectedActionException();
        }
        ArrayList<Integer> all = new ArrayList<Integer>();
        for (Integer i = 0; i < attributes.length; i++) {
            all.add(i);
        }
        decisionTree = implementation(knowledgeBase, all);
    }

    private double entropy(HashMap<String[], Boolean> set) {
        Integer yes = 0, no = 0;
        for (Boolean decision : set.values()) {
            if (decision) {
                yes++;
            } else {
                no++;
            }
        }
        double rtrn = 0.0;
        if (yes > 0) rtrn += yes / set.size() * log(yes / set.size());
        if (no > 0) rtrn += no / set.size() * log(no / set.size());
        return rtrn / log(2);
    }

    private Boolean mostCommon(HashMap<String[], Boolean> set) {
        Integer yes = 0, no = 0;
        for (Boolean decision : set.values()) {
            if (decision) {
                yes++;
            } else {
                no++;
            }
        }
        if (yes != no) {
            return yes > no;
        } else {
            Random rndm = new Random();
            return rndm.nextBoolean();
        }
    }

    private double gain (HashMap<String[], Boolean> set, Integer attrIndex) {
        Double rtrn = entropy(set);
        ArrayList<HashMap> subSets = new ArrayList<HashMap>();
        for (String[] pattern : set.keySet()) {
            seek: { // for ... else
                for (HashMap<String[], Boolean> subSet : subSets) {
                    Set<String[]> keys = subSet.keySet();
                    if (!keys.isEmpty()) {
                        String attrVal = keys.iterator().next()[attrIndex];
                        if (pattern[attrIndex].equals(attrVal)) {
                            subSet.put(pattern, set.get(pattern));
                            break seek;
                        }
                    }
                }
                HashMap<String[], Boolean> newSet = new HashMap<String[],
                        Boolean>();
                newSet.put(pattern, set.get(pattern));
                subSets.add(newSet);
            }
        }
        for (HashMap<String[], Boolean> subSet : subSets) {
            rtrn += subSet.size()/set.size() * entropy(subSet);
        }
        return rtrn;
    }

    protected Node implementation(HashMap<String[], Boolean> set,
            ArrayList<Integer> attrIndexes) {
        if (attrIndexes.isEmpty()) { // caso trivial
            return new Node(mostCommon(set));
        }
        double entropy = entropy(set);
        if (entropy == 0.0) { // absolutamente homogéneo
            return new Node(mostCommon(set));
        }
        double bestAttrGain = Double.MIN_VALUE; // caso general
        Integer bestAttrIndex = 0;
        for (Integer attrIndex : attrIndexes) { // elige el mejor atributo
            double aux = gain(set, attrIndex);
            if (aux > bestAttrGain) {
                bestAttrGain = aux;
                bestAttrIndex = attrIndex;
            }
        }
        attrIndexes.remove(new Integer(bestAttrIndex)); // remove(Object o)
        HashMap<String, HashMap> subSets = new HashMap<String, HashMap>();
        for (String[] pattern : set.keySet()) {
            seek: { // for ... else
                for (String attrVal : subSets.keySet()) {
                    if (pattern[bestAttrIndex].equals(attrVal)) {
                        subSets.get(attrVal).put(pattern,
                                set.get(pattern));
                        break seek;
                    } 
                }
                subSets.put(pattern[bestAttrIndex], new HashMap<String[],
                        Boolean>());
                subSets.get(pattern[bestAttrIndex]).put(pattern,
                        set.get(pattern));
            }
        }
        HashMap sons = new HashMap<String, Node>();
        for (String value : subSets.keySet()) {
            sons.put(value, implementation(subSets.get(value), attrIndexes));
        }
        attributes[bestAttrIndex].setSons(sons);
        return attributes[bestAttrIndex];
    }

    protected Boolean traverse (String[] pattern, Node currentNode) throws
            UnexpectedValueException {
        Integer i;
        if (currentNode.decision != null) {
            return currentNode.decision;
        }
        for (i = 0; i < attributes.length; i++) {
            if (attributes[i] == currentNode) break;
        }
        HashMap<String, Node> sons = currentNode.getSons();
        for (String value : sons.keySet()) {
            if (value.equals(pattern[i])) {
                return traverse(pattern, sons.get(value));
            }
        }
        throw new UnexpectedValueException(false, i);
    }

    public Boolean evaluate(String[] pattern) throws
            UnexpectedValueException, UnexpectedActionException {
        /* Evalúa un patrón de entrada con el árbol de decisión ya existente.
         * Lanza una excepción si no se encuentra el árbol o el tamaño del
         * patron de entrada no corresponde con el de los atributos.
         */
        if (pattern.length > attributes.length) {
            throw new UnexpectedValueException(true, null);
        } else if (pattern.length < attributes.length) {
            throw new UnexpectedValueException(false, null);
        }
        if (decisionTree == null) {
            throw new UnexpectedActionException();
        } else if (decisionTree.getDecision() == null &&
                decisionTree.getSons() == null) {
            throw new UnexpectedActionException();
        }
        return traverse(pattern, decisionTree);
    }

    public Boolean[] evaluate(InputStream is) throws IOException,
            UnexpectedValueException, UnexpectedActionException {
        /* Evalúa un conjunto de patrones de entrada recibidos a través de un
         * flujo de entrada en formato CSV con el árbol de decisión ya
         * existente. Lanza una excepción si no se encuentra el árbol o el
         * tamaño del patron de entrada no corresponde con el de los atributos.
         */
        return null;
    }

    public void load(InputStream is) throws IOException,
            UnexpectedValueException {
        /* Carga un árbol de decisión a partir de un flujo de entrada en
         * formato DOT, que contiene el conjunto de atributos y el árbol.
         * Lanza una excepción si hay discrepancia entre los atributos
         * nombrados y los descritos en el árbol. Sobreescribe los valores
         * anteriores en las variables de instancia.
         */
    }

    public void dump(OutputStream os) throws IOException,
            UnexpectedActionException {
        /* Vacía el árbol de decisión ya existente en formato DOT sobre un
         * flujo de salida. Lanza una excepción si no se encuentra el árbol ó
         * los atributos.
         */
    }

    public String[] getAttributes() {
        /* Devuelve los atributos a manejar en forma de cadenas.
         */
        String[] sAttributes = new String[attributes.length];
        for (Integer i = 0; i < attributes.length; i++) {
            sAttributes[i] = attributes[i].getAttribute();
        }
        return sAttributes;
    }

    public void setKnowledgeBase(HashMap<String[], Boolean> knowledgeBase)
            throws UnexpectedValueException {
        /* Coloca una base de conocimientos. Lanza una excepción si el tamaño
         * de los patrones no coincide con el de los atributos.
         */
        Set<String[]> keys = knowledgeBase.keySet();
        if (!keys.isEmpty()) {
            String[] key = keys.iterator().next();
            if (key.length > attributes.length) {
                throw new UnexpectedValueException(true, null);
            } else if (key.length < attributes.length) {
                throw new UnexpectedValueException(false, null);
            } else {
                this.knowledgeBase = knowledgeBase;
            }
        } else {
            throw new UnexpectedValueException(false, null);
        }
    }

    public HashMap<String[], Boolean> getKnowledgeBase() {
        /* Devuelve la base de conocimientos empleada.
         */
        return knowledgeBase;
    }
    
    public static void main (String[] args) {
        HashMap kb = new HashMap<String[], Boolean>();
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
        String[] attributes =  new String[4];
        attributes[0] = "outlook";
        attributes[1] = "temperature";
        attributes[2] = "humidity";
        attributes[3] = "wind";
        try {
            System.out.println("INIT");
            ID3 instance = new ID3(attributes, kb);
            System.out.println("GETTER");
            System.out.println(instance.getAttributes());
            System.out.println(instance.getKnowledgeBase());
            String[] testPattern = {"sunny", "mild", "high", "strong"};
            System.out.println("TEST");
            System.out.println(instance.evaluate(testPattern));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
