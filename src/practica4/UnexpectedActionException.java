/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package practica4;

/**
 *
 * @author daniel
 */
public class UnexpectedActionException extends Exception {
    public UnexpectedActionException() {
        super("No se esperaba esta acción");
    }
}
