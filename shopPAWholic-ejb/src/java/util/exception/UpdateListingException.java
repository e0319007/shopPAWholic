/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exception;

/**
 *
 * @author EileenLeong
 */
public class UpdateListingException extends Exception {

    /**
     * Creates a new instance of <code>UpdateListingException</code> without
     * detail message.
     */
    public UpdateListingException() {
    }

    /**
     * Constructs an instance of <code>UpdateListingException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public UpdateListingException(String msg) {
        super(msg);
    }
}
