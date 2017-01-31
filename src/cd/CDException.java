package cd;

public class CDException extends Exception {
	private static final long serialVersionUID = 1L;

    /**
     * Create a new CDException object.
     * @param message the message explaining the exception
     */
    public CDException( String message )
    {
      super( message );
    }

    /**
     * Create a new CDException object.
     * @param cause the cause of the exception
     */
    public CDException( Throwable cause )
    {
      super( cause );
    }
}
