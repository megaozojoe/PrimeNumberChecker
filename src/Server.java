
import java.io.*;
import java.net.*;
import java.util.Date;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;


public class Server extends Application {
	@Override // this is for overriding the start method
	public void start(Stage primaryStage) {//etting up gui elements
		
		TextArea ta = new TextArea();
		
		// creating place for all the elements
		Scene scene = new Scene(new ScrollPane(ta), 450, 200);
		primaryStage.setTitle("Prime number Sever");//sets the title of the applicaiton
		primaryStage.setScene(scene); //sets scene in stage
		primaryStage.show(); //displays stage
		
		
		new Thread ( () -> {
			try {
				
				ServerSocket serverSocket = new ServerSocket(8080); //this should be the local connection for server
				
				Platform.runLater( () -> ta.appendText("Server started at " + new Date() + '\n'));
				
				//Listen for a connection requrest 
				Socket socket = serverSocket.accept();
				
				// Create data input and out streams
				DataInputStream inputFromClient = new DataInputStream(socket.getInputStream());
				DataOutputStream outputToClient = new DataOutputStream(socket.getOutputStream());
				
				while(true) {
					double prime = inputFromClient.readDouble(); //set variable to check for prime
					System.out.println(prime);
					Boolean notPrime = false; //this checks if we should send the message that it is prime or not
					
					//Compute if prime

					Boolean check = true;
					
					//checks if prime
					for(int i = 2; i <= (prime/2); ++i ) {
						if(prime % i == 0) {
							check = false;
							break;
						}
					}
					outputToClient.writeBoolean(check);
					
					Platform.runLater(() -> {
						ta.appendText("Number received from client: " + prime + '\n');
						
					});
					
					notPrime = false;
				}
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
				
				).start();
	}
	//main method is only used basically to launch the gui elements lol
	public static void main(String[] args) {
	launch(args);
	}
}
