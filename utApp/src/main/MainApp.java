package main;

import java.io.IOException;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import main.model.Member;
import main.model.SupportClass;
import main.view.UTAppOverviewController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;
	private ObservableList<Member> member = FXCollections.observableArrayList();

	public MainApp() {
	}

	public void initRootLayout() {
		try {
			member = SupportClass.restoreAllMembers();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class
					.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
		}
	}

	public void showUTAppOverview() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class
					.getResource("view/uTAppOverview.fxml"));
			AnchorPane uTAppOverview = (AnchorPane) loader.load();
			rootLayout.setCenter(uTAppOverview);
			UTAppOverviewController controller = loader.getController();
			controller.setMainApp(this);
		} catch (IOException e) {

		}
	}

	@Override
	public void init() {
		SupportClass.startSessionFactory();
	}

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Заявки");
		initRootLayout();
		showUTAppOverview();
	}

	@Override
	public void stop() {
		SupportClass.stopSessionFactory();
	}

	public static void main(String[] args) {
		launch(args);

	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public ObservableList<Member> getMember() {
		return member;
	}

	public void setMember(ObservableList<Member> member) {
		this.member = member;
	}
}
