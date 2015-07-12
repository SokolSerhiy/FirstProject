package main.view;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.controlsfx.dialog.Dialogs;

import main.MainApp;
import main.model.Member;
import main.model.SupportClass;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;

public class UTAppOverviewController {

	@FXML
	private TableView<Member> memberTable;
	@FXML
	private TableColumn<Member, String> mainNumberColumn;
	@FXML
	private TableColumn<Member, String> mobNumberColumn;
	@FXML
	private TableColumn<Member, String> statusColumn;
	@FXML
	private TableColumn<Member, String> comentColumn;
	@FXML
	private TableColumn<Member, String> nextWorkColumn;
	@FXML
	private Button addMember;
	@FXML
	private Button delMember;
	@FXML
	private Button workDone;
	@FXML
	private TextArea memberAddDel;
	@FXML
	private TextField mainNumberText;
	@FXML
	private TextField mobNumberText;
	@FXML
	private ChoiceBox<String> statusText;
	@FXML
	private TextField comentText;
	@FXML
	private DatePicker nextWorkText;

	private MainApp mainApp;

	public UTAppOverviewController() {
	}

	@FXML
	private void initialize() {
		mainNumberColumn.setCellValueFactory(cellData -> cellData.getValue()
				.asPropertyMainNumber());
		mobNumberColumn.setCellValueFactory(cellData -> cellData.getValue()
				.asPropertyMobNumber());
		statusColumn.setCellValueFactory(cellData -> cellData.getValue()
				.asPropertyStatus());
		comentColumn.setCellValueFactory(cellData -> cellData.getValue()
				.asPropertyComent());
		nextWorkColumn.setCellValueFactory(cellData -> cellData.getValue()
				.asPropertyNextWork());
		statusText.setItems(FXCollections.observableArrayList("Нова",
				"Активна", "Возврат"));
		statusText.setTooltip(new Tooltip("Виберіть статус заявки"));
		memberAddDel.setTooltip(new Tooltip(
				"Вставте номера телефонів які потрібно додати, або видалити"));
		mobNumberText.setTooltip(new Tooltip(
				"Введіть номер мобільного телефону"));
		comentText.setTooltip(new Tooltip(
				"Введіть результат розмови з клієнтом"));
		mainNumberText.setPromptText("Заповнюється автоматично");
		memberTable
				.getSelectionModel()
				.selectedItemProperty()
				.addListener(
						(observable, oldValue, newValue) -> {
							try {
								mainNumberText.setText(newValue
										.asPropertyMainNumber().get());
								mobNumberText.setText(newValue.getMobNumber());
								comentText.setText(newValue.getComent());
								statusText.getSelectionModel().select(
										newValue.getStatus());
								nextWorkText.setValue(SupportClass
										.parse(newValue.getNextWork()));
							} catch (NullPointerException e) {
							}
						});

	}

	@FXML
	private void workDoneMethod() {
		for (int i = 0; i < mainApp.getMember().size(); i++) {
			if (mainApp.getMember().get(i).getMainNumber()
					.equals(mainNumberText.getText())) {
				mainApp.getMember()
						.get(i)
						.setAdditionalData(
								mobNumberText.getText(),
								statusText.getSelectionModel()
										.getSelectedItem(),
								comentText.getText(), nextWorkText.getValue());
			}
		}
		mobNumberText.setText(null);
		comentText.setText(null);
		statusText.getSelectionModel().clearSelection();
		nextWorkText.setValue(null);
		memberTable.getSelectionModel().clearSelection();
		this.lockForIncondite();

	}

	@FXML
	private void delMemberMethod() {
		int n = 0;
		boolean b = false;
		memberTable.getSelectionModel().clearSelection();
		if (memberAddDel.getText() != null) {
			Pattern pattern = Pattern.compile("[0-9]+");
			Matcher matcher = pattern.matcher(memberAddDel.getText());
			while (matcher.find()) {
				for (int i = 0; i <= mainApp.getMember().size(); i++) {
					if (mainApp.getMember().get(i).getMainNumber()
							.equals(matcher.group())) {
						SupportClass.deleteMember(mainApp.getMember().get(i));
						mainApp.getMember().remove(i);
						b = true;
						n++;
						break;
					}
				}
			}
			memberAddDel.setText(null);
			this.lockForIncondite();

			if (b) {
				String str = "Успішно видалено " + n + " заявок";
				if ((n < 20 && n == 1) | (n > 20 && n % 10 == 1)) {
					str = "Успішно видалена " + n + " заявка";
				}
				if ((n < 20 && n == 2) | (n < 20 && n == 3)
						| (n < 20 && n == 4) | (n > 20 && n % 10 == 2)
						| (n > 20 && n % 10 == 3) | (n > 20 && n % 10 == 4)) {
					str = "Успішно видалено " + n + " заявки";
				}
				Dialogs.create().title("Видалення заявок").masthead(null)
						.message(str).showWarning();
			}
		}
	}

	@FXML
	private void addMemberMethod() {
		int n = 0;
		boolean b = false;
		memberTable.getSelectionModel().clearSelection();
		if (memberAddDel.getText() != null) {
			Pattern pattern = Pattern.compile("[0-9]+");
			Matcher matcher = pattern.matcher(memberAddDel.getText());
			while (matcher.find()) {
				if (mainApp.getMember().size() == 0) {
					mainApp.getMember().add(new Member(matcher.group()));
					n++;
					b = true;
				} else {
					int j = 0;
					for (int i = 0; i < mainApp.getMember().size(); i++) {
						if (!matcher.group().equals(
								mainApp.getMember().get(i).getMainNumber())) {
							j++;
						}
					}
					if (j == mainApp.getMember().size())
						mainApp.getMember().add(new Member(matcher.group()));
					n++;
					b = true;
				}
			}
			memberAddDel.setText(null);
			this.lockForIncondite();
			if (b) {
				String str = "Успішно додано " + n + " заявок";
				if ((n < 20 && n == 1) | (n > 20 && n % 10 == 1)) {
					str = "Успішно додана " + n + " заявка";
				}
				if ((n < 20 && n == 2) | (n < 20 && n == 3)
						| (n < 20 && n == 4) | (n > 20 && n % 10 == 2)
						| (n > 20 && n % 10 == 3) | (n > 20 && n % 10 == 4)) {
					str = "Успішно додано " + n + " заявки";
				}
				Dialogs.create().title("Додавання заявок").masthead(null)
						.message(str).showWarning();
			}
		}
	}

	private void lockForIncondite() {
		LocalDate curentDate = LocalDate.now();
		boolean b = true;
		boolean d = true;
		for (int i = 0; i < mainApp.getMember().size(); i++) {
			if (curentDate.equals(SupportClass.parse(mainApp.getMember().get(i)
					.getNextWork()))) {
				mainNumberText.setText(mainApp.getMember().get(i)
						.asPropertyMainNumber().get());
				mobNumberText
						.setText(mainApp.getMember().get(i).getMobNumber());
				statusText.getSelectionModel().select(
						mainApp.getMember().get(i).getStatus());
				comentText.setText(mainApp.getMember().get(i).getComent());
				nextWorkText.setValue(null);
				b = false;
				break;
			}
		}
		if (b) {
			for (int i = 0; i < mainApp.getMember().size(); i++) {
				try {
					if (mainApp.getMember().get(i).getComent().length() == 0) {
						mainNumberText.setText(mainApp.getMember().get(i)
								.asPropertyMainNumber().get());

						d = false;
					}
				} catch (NullPointerException e) {
					mainNumberText.setText(mainApp.getMember().get(i)
							.asPropertyMainNumber().get());

					d = false;
				}
			}
		}
		if (b && d) {
			mainNumberText.setText("Молодець");
		}
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		memberTable.setItems(mainApp.getMember());
	}
}