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
public class ForumTitleExistsException extends Exception {

    /**
     * Creates a new instance of <code>ForumTitleExistsException</code> without
     * detail message.
     */
    public ForumTitleExistsException() {
    }

    /**
     * Constructs an instance of <code>ForumTitleExistsException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public ForumTitleExistsException(String msg) {
        super(msg);
    }
}
