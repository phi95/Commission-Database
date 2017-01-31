package cd.entity;

public interface Customer extends User {
    /** Return the customer's description.
     * @return the customer's description
     */
	public String getDescription();
	
    /** Set the customer's description.
     * @param description the new first name of this user
     */
	public void setDescription(String description);
}
