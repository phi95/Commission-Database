package cd.entity;

import cd.CDException;

public interface Employee extends User {
    /** Return the employee's employer.
     * @return the employee's employer
     */
	public Employer getEmployer();
	
	/** Set the worker's manager.
     * @param employer the new manager of this worker
     */
	public void setEmployer(Employer employer);
}
