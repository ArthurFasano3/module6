package application;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.TextArea;

import java.io.File;
import java.io.PrintStream;


public class JavaFX_proj extends Application {
	
	Button button;

	
	public static void main(String[] args) throws IOException{
		wordOccurance();
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Word Occurances");

        TextArea ta = new TextArea();
        OutputStream console =  Console(ta);
		PrintStream ps = new PrintStream(console , true);
        System.setOut(ps);
        VBox vbox = new VBox(ta);
        Scene app = new Scene(vbox, 1000, 1000);
 
        primaryStage.setScene(app);
        primaryStage.show();
		
	}
	
	private OutputStream Console(TextArea ta) {
		// TODO Auto-generated method stub
		return null;
	}

	static class replaceOperator implements UnaryOperator<String> {
		
		public String apply(String t) {
			
			return t.replaceAll("(<.*?>)|(&.*?;)", "").replaceAll("\\s{2,}", "").replaceAll("\\p{Punct}", "").toLowerCase();
		}

	}
	

	public static void wordOccurance() throws IOException{
		
		BufferedReader reader = Files.newBufferedReader(Paths.get("htmldoc.html"), StandardCharsets.UTF_8);
		//BufferedReader writer = Files.newBufferedReader(Paths.get("data-sorted.txt"), StandardCharsets.UTF_8);
		
		List<String> line = reader.lines().skip(257).limit(166).collect(Collectors.toList());
		List<String> words = new ArrayList<String>();
		line.replaceAll( new replaceOperator() );
		
		line.stream().forEach(str -> {
			words.addAll(Arrays.asList(str.split(" ")));
		});
		
		
		Map<String, Long> wordCounts = words.stream().collect(Collectors.groupingBy(Function.identity(),Collectors.counting()));
		
		wordCounts.entrySet().forEach(entry -> {
			System.out.println(entry.getKey() + " " + entry.getValue());
		});			
				
	}
	
}
