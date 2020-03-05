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
public class ListingSkuCodeExistException extends Exception {

    /**
     * Creates a new instance of <code>ListingSkuCodeExistException</code>
     * without detail message.
     */
    public ListingSkuCodeExistException() {
    }

    /**
     * Constructs an instance of <code>ListingSkuCodeExistException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public ListingSkuCodeExistException(String msg) {
        super(msg);
    }
}
