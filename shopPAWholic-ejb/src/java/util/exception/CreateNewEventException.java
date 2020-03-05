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
public class CreateNewEventException extends Exception {

    /**
     * Creates a new instance of <code>CreateNewEventException</code> without
     * detail message.
     */
    public CreateNewEventException() {
    }

    /**
     * Constructs an instance of <code>CreateNewEventException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public CreateNewEventException(String msg) {
        super(msg);
    }
}
