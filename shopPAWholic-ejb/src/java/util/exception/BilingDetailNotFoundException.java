/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exception;

/**
 *
 * @author Joanna Ng
 */
public class BilingDetailNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>BilingDetailNotFoundException</code>
     * without detail message.
     */
    public BilingDetailNotFoundException() {
    }

    /**
     * Constructs an instance of <code>BilingDetailNotFoundException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public BilingDetailNotFoundException(String msg) {
        super(msg);
    }
}
