package application;


import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class MainApp extends Application {

	// A reference to the primary stage and the GUI
    private Stage primaryStage;
    private AnchorPane estimator;
        
    // Constructor
    public MainApp() {}  
      
    // Start method
    // Sets up the primary stage then shows the estimator window
    @Override
    public void start(Stage primaryStage) 
    {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Mortgage Estimator");
        
        showEstimator();
        
    }

    // Initializes and shows the estimator window
    public void showEstimator() 
    {
        try 
        {
            // Load the GUI from the fxml file
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("MortgageEstimator.fxml"));
            estimator = loader.load();

            // Show the scene containing the estimator
            Scene scene = new Scene(estimator);
            primaryStage.setScene(scene);

            // Give the controller access to the estimator
            EstimatorController controller = loader.getController();
            controller.setMain(this);

            // Show the primary stage
            primaryStage.show();
        } 
        
        catch (IOException e) 
        {
            e.printStackTrace();
        }

       
    }
    
    // Primary stage getter
    public Stage getPrimaryStage() 
    {
        return primaryStage;
    }

    // Main
    public static void main(String[] args) 
    {
        launch(args);
    }
   
}