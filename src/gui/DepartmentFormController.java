package gui;

import java.net.URL;
import java.util.ResourceBundle;

import db.DbException;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;
import model.services.DepartmentService;

public class DepartmentFormController implements Initializable {
	private Department entity;
	private DepartmentService service;
	
	@FXML
	private TextField txtID;
	
	@FXML
	private TextField txtName;
	
	@FXML
	private Label labelErrorName;  
	
	@FXML
	private Button btSave;
	
	@FXML
	private Button btCancel;
	
	@FXML
	public void onBtSaveAction(ActionEvent event) {
		// System.out.println("onBtSaveAction");
		if(entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		
		if(service == null) {
			throw new IllegalStateException("Service was null");
		}
		
		try {
			entity = getFormData();
			service.saveOrUpdate(entity);
			Utils.currentStage(event).close();
		} catch (DbException e) {
			Alerts.showAlert("Error saving object", null, e.getMessage(), AlertType.ERROR);
		}				
	}
	
	private Department getFormData() {
		Department obj = new Department();
		obj.setId(Utils.tryParseToInt(txtID.getText()));
		obj.setName(txtName.getText());
		return obj;
	}

	@FXML
	public void onBtCancelAction(ActionEvent event) {
		// System.out.println("onBtCancelAction");
		Utils.currentStage(event).close();
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}
	
	private void initializeNodes() {
		Constraints.setTextFieldInteger(txtID);
		Constraints.setTextFieldMaxLength(txtName, 30);		
	}
	
	public void setDepartmentService(DepartmentService service) {
		this.service = service;
	}
	
	public void setDepartment(Department entity) {
		this.entity = entity;
	}
	
	public void updateFormData() {
		if(entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		
		txtID.setText(String.valueOf(entity.getId()));
		txtName.setText(entity.getName());
	}
}
