package com.iadvise.controller.exceptions;

public class NonexistentEntityException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 4986493393121442844L;
	public NonexistentEntityException(String message, Throwable cause) {
        super(message, cause);
    }
    public NonexistentEntityException(String message) {
        super(message);
    }
}
