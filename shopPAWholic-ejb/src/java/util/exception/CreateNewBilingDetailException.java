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
public class CreateNewBilingDetailException extends Exception {

    /**
     * Creates a new instance of <code>CreateNewBilingDetailException</code>
     * without detail message.
     */
    public CreateNewBilingDetailException() {
    }

    /**
     * Constructs an instance of <code>CreateNewBilingDetailException</code>
     * with the specified detail message.
     *
     * @param msg the detail message.
     */
    public CreateNewBilingDetailException(String msg) {
        super(msg);
    }
}
