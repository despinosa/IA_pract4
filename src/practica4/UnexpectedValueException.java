/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package practica4;

/**
 *
 * @author daniel
 */
public class UnexpectedValueException extends Exception {

    private Boolean wrongSize;
    private Integer recordNotFound;

    public UnexpectedValueException(Boolean wrongSize,
            Integer recordNotFound) {
        super("El dato de entrada no es válido");
        this.wrongSize = wrongSize;
        this.recordNotFound = recordNotFound;
    }
    
    public Boolean getWrongSize () {
        return wrongSize;
    }
    
    public Integer getRecordNotFound () {
        return recordNotFound;
    }
}
