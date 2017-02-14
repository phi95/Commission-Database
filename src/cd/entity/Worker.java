package cd.entity;

import cd.CDException;

public interface Worker extends User {
    /** Return the worker's manager.
     * @return the worker's manager
     */
	public Manager getManager();
	
	/** Set the worker's manager.
     * @param manager the new manager of this worker
     */
	public void setManager(Manager manager);
}
