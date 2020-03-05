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
public class CartNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>CartNotFoundException</code> without
     * detail message.
     */
    public CartNotFoundException() {
    }

    /**
     * Constructs an instance of <code>CartNotFoundException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public CartNotFoundException(String msg) {
        super(msg);
    }
}
