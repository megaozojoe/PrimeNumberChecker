//Project by Matthew del Real

import java.io.*;
import java.net.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Client extends Application {
	//io streams for communication
	DataOutputStream toServer = null;
	DataInputStream fromServer = null;
	
	@Override
	public void start(Stage primaryStage) {
		
		//setitng up gui elements
		BorderPane paneForTextField = new BorderPane(); //for formatting
		paneForTextField.setPadding(new Insets(5 , 5, 5, 5));
		paneForTextField.setStyle("-fx-border-color: green"); //adding style
		paneForTextField.setLeft(new Label("Enter a radius: "));
		
		
		TextField tf = new TextField(); //alliging the textfield
		tf.setAlignment(Pos.BOTTOM_RIGHT);
		paneForTextField.setCenter(tf);
		
		BorderPane mainPane = new BorderPane();
		//text are to display contents
		TextArea ta = new TextArea();
		mainPane.setCenter(new ScrollPane(ta));
		mainPane.setTop(paneForTextField);
		
		//Creating a scene and place it in the stage
		Scene scene = new Scene(mainPane, 450, 200);
		primaryStage.setTitle("Client for Primary Number");
		primaryStage.setScene(scene);
		primaryStage.show();//display the stage
		
		tf.setOnAction(e -> {
			try {
				//get the prime number
				double prime = Double.parseDouble(tf.getText().trim());
				
				//Send the prime to the server
				toServer.writeDouble(prime);
				toServer.flush();
				
				//Get Prime check from the server
				Boolean check = fromServer.readBoolean();
				
				//Display to text area
				ta.appendText("number is  " + prime + '\n');
				if(check) {
					ta.appendText(prime + " is prime.\n");
				}else {
					ta.appendText(prime + " is not prime.\n");
				}
				check = false;
				
				
			}
			catch(IOException ex) {
				System.err.println(ex);
			}
		});
		
		try {
			//create socket for server connection
			Socket socket = new Socket("localhost", 8080); //this is the local host connection
			
			//input stream from server connection
			fromServer = new DataInputStream(socket.getInputStream());
			
			//outbound stream to server
			toServer = new DataOutputStream(socket.getOutputStream());
		}
		catch(IOException ex) { //prints any errors with code
			ta.appendText(ex.toString() + '\n');			
		}
		
	}
	
	
	//used for launching the gui elements
	public static void main(String[] args) {
	launch(args);
	}

	}
