/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exception;

/**
 *
 * @author Shi Zhan
 */
public class EventNameExistsException extends Exception {

    /**
     * Creates a new instance of <code>EventNameExistsException</code> without
     * detail message.
     */
    public EventNameExistsException() {
    }

    /**
     * Constructs an instance of <code>EventNameExistsException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public EventNameExistsException(String msg) {
        super(msg);
    }
}
