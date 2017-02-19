package cd.logic.impl;

import java.util.List;

import cd.CDException;
import cd.entity.Employee;
import cd.entity.Employer;
import cd.object.ObjectLayer;

public class EmployeeCtrl {

	ObjectLayer objectLayer = null;
	
	public EmployeeCtrl(ObjectLayer objectLayer) {
		this.objectLayer = objectLayer;
	} // EmployeeCtrl
	
	public List<Employee> getEmployeesFromEmployer(Employer employer) throws CDException {
		return objectLayer.getPersistence().restoreEmployeeEmployedByEmployer(employer);
	} // getEmployeesFromEmployer
} // EmployeeCtrl
