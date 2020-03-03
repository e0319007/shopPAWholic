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
public class CreateNewCartException extends Exception {

    /**
     * Creates a new instance of <code>CreateNewCartException</code> without
     * detail message.
     */
    public CreateNewCartException() {
    }

    /**
     * Constructs an instance of <code>CreateNewCartException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public CreateNewCartException(String msg) {
        super(msg);
    }
}
