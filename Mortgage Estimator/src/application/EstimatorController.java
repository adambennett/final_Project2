package application;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class EstimatorController implements Initializable
{
	
	@FXML
    private TextField grossIncome;
    @FXML
    private TextField monthlyDebt;
    @FXML
    private TextField interestRate;
    @FXML
	private ComboBox<Integer> terms;
    @FXML
    private TextField downPayment;
    @FXML
    private TextField lowPayment;
    @FXML
    private TextField highPayment;
    @FXML
    private TextField maxPayment;
    @FXML
    private TextField mortgageAmount;

    // Constructor
	public EstimatorController() {}
	
	// A reference to the main application
	private MainApp mainApp;
	
	
	// This initializes the term combo box with the three possible terms, as well as sets the default term to 30
	public void initialize(URL fxmlFileLocation, ResourceBundle resources)
	{
		assert terms != null : "fx:id=\"myChoices\" was not injected: check your FXML file 'MortgageEstimator.fxml'.";
		terms.setItems(FXCollections.observableArrayList(30, 15, 10));
		terms.setValue(30);
		lowPayment.setText("$0.0");
		highPayment.setText("$0.0");
		maxPayment.setText("$0.0");
		mortgageAmount.setText("$0.0");
	}
	
	// Sets up the application correctly
	public void setMain(MainApp mainApp) 
	{
		this.mainApp = mainApp;
	}
	
	// This method handles calculated the four fields on the left side of the GUI
	@FXML
	private void handleCalculate() 
	{
		// A string that is displayed when your debt is higher than your income
		String debtDude = "Too much debt";
		String youMakeNegativeMoney = "Negative income?";
		
		// Reads in the user inputed data from "Annual Gross Income"
		// This string is then parsed into a double
		String grIncome = grossIncome.getText();
		double grIncome2 = Double.parseDouble(grIncome);
		
		// Reads in the user inputed data from "Total Monthly Debt", parses to double
		String mDebt = monthlyDebt.getText();
		double mDebt2 = Double.parseDouble(mDebt);
		
		// Reads from "Monthly Interest Rate", parses to double
		// This field should be inputed as a decimal. (Ex: 6% annual interest should be entered as 0.06)
		String iRate = interestRate.getText();
		double iRate2 = (Double.parseDouble(iRate) * 0.01);
		
		// Reads from the terms drop down combo box
		Integer years = terms.getValue();
		
		// Reads from "Down Payment", parses to double
		String dPayment = downPayment.getText();
		double dPayment2 = Double.parseDouble(dPayment);
		
		
		// Calculations actually begin
		// tempValue is used to hold the calculated value at different points
		// tempLowPayment keeps track of the 18% figure specifically
		double tempValue = ((grIncome2 / 12) * 0.18);
		double tempLowPayment = tempValue;
		if (tempValue < 0) { lowPayment.setText(youMakeNegativeMoney); }
		else { lowPayment.setText("$" + Double.toString(tempValue)); }
	
		// Here tempValue is recalculated to become the 36% figure
		tempValue = (((grIncome2 / 12) * 0.36) - mDebt2);
		
		// If the 36% figure is a negative number, your debt is too high to make payments
		if (tempValue < 0) { highPayment.setText(debtDude); }
		else { highPayment.setText("$" + Double.toString(tempValue)); }
		
		// Now we check to see which value is lower, the 18% or 36% figure
		// Whichever is lower is the max payment you can afford to pay per month
		if (tempLowPayment < tempValue) { 	maxPayment.setText("$" + Double.toString(tempLowPayment)); }
		else if (tempLowPayment > tempValue && tempValue >= 0) { maxPayment.setText("$" + Double.toString(tempValue)); }
		else if (tempValue < 0) { maxPayment.setText(debtDude); }
		else { 	maxPayment.setText("$" + Double.toString(tempLowPayment)); }
		
		// This formula was found online, it is not the same as the formula Excel uses but it is similar
		// The figure calculated by the formula should be within 100k of the Excel figure
		// Formula: (PMT(1-((1+i)^n) / i)) + down payment
		// n = number of years
		// i = interest rate
		// PMT = max 
		tempValue = (((1-(Math.pow(1+iRate2, (-(years*12)))))/(iRate2)) * tempLowPayment) + dPayment2;
		if (isNumeric(Double.toString(tempValue))) { mortgageAmount.setText("$" + Double.toString(tempValue)); }
		else { mortgageAmount.setText("$0.0"); }
		
	}
	
	// This method simply resets every field to 0, and the combo box is reset to a 30-year term
	@FXML
	private void handleResest()
	{
		grossIncome.setText("0");
		monthlyDebt.setText("0");
		interestRate.setText("0");
		terms.setValue(30);
		downPayment.setText("0");
		lowPayment.setText("$0.0");
		highPayment.setText("$0.0");
		maxPayment.setText("$0.0");
		mortgageAmount.setText("$0.0");
	}
	
	// Check if a string is a number or not
	public static boolean isNumeric(String str)
	{
	  return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
	}

}

