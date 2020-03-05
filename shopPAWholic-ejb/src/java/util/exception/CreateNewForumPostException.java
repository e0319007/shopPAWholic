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
public class CreateNewForumPostException extends Exception {

    /**
     * Creates a new instance of <code>CreateNewForumPostException</code>
     * without detail message.
     */
    public CreateNewForumPostException() {
    }

    /**
     * Constructs an instance of <code>CreateNewForumPostException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public CreateNewForumPostException(String msg) {
        super(msg);
    }
}
