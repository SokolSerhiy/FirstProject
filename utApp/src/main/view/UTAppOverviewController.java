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
				.getMainNumber());
		mobNumberColumn.setCellValueFactory(cellData -> cellData.getValue()
				.getMobNumber());
		statusColumn.setCellValueFactory(cellData -> cellData.getValue()
				.getStatus());
		comentColumn.setCellValueFactory(cellData -> cellData.getValue()
				.getComent());
		nextWorkColumn.setCellValueFactory(cellData -> cellData.getValue()
				.getNextWork());
		statusText.setItems(FXCollections.observableArrayList("����",
				"�������", "�������"));
		statusText.setTooltip(new Tooltip("������� ������ ������"));
		memberAddDel.setTooltip(new Tooltip(
				"������� ������ �������� �� ������� ������, ��� ��������"));
		mobNumberText.setTooltip(new Tooltip(
				"������ ����� ��������� ��������"));
		comentText.setTooltip(new Tooltip(
				"������ ��������� ������� � �볺����"));
		mainNumberText.setPromptText("������������ �����������");
		memberTable
				.getSelectionModel()
				.selectedItemProperty()
				.addListener(
						(observable, oldValue, newValue) -> {
							try {
								mainNumberText.setText(newValue
										.getMainNumber().get());
								mobNumberText.setText(newValue.getMobNumber()
										.get());
								comentText.setText(newValue.getComent().get());
								statusText.getSelectionModel().select(
										newValue.getStatus().get());
								nextWorkText.setValue(SupportClass
										.parse(newValue.getNextWork().get()));
							} catch (NullPointerException e) {
							}
						});

	}

	@FXML
	private void workDoneMethod() {
		for (int i = 0; i < mainApp.getMember().size(); i++) {
			if (mainApp.getMember().get(i).getMainNumberAsString()
					.equals(mainNumberText.getText())) {
				mainApp.getMember().get(i)
						.setAdditionalData(mobNumberText.getText(), statusText.getSelectionModel()
								.getSelectedItem(), comentText.getText(), nextWorkText.getValue());
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
		List<String> list = new ArrayList<>();
		memberTable.getSelectionModel().clearSelection();
		if (memberAddDel.getText() != null) {
			Pattern pattern = Pattern.compile("[0-9]+");
			Matcher matcher = pattern.matcher(memberAddDel.getText());
			while (matcher.find()) {
				Iterator<Member> it = mainApp.getMember().iterator();
				while (it.hasNext()) {
					if (it.next().getMainNumberAsString()
							.equals(matcher.group())) {
						list.add(matcher.group());
						it.remove();
						b = true;
						n++;
					}
				}
			}
			SupportClass.deleteMember(list);
			memberAddDel.setText(null);
			this.lockForIncondite();

			if (b) {
				String str = "������ �������� " + n + " ������";
				if ((n < 20 && n == 1) | (n > 20 && n % 10 == 1)) {
					str = "������ �������� " + n + " ������";
				}
				if ((n < 20 && n == 2) | (n < 20 && n == 3)
						| (n < 20 && n == 4) | (n > 20 && n % 10 == 2)
						| (n > 20 && n % 10 == 3) | (n > 20 && n % 10 == 4)) {
					str = "������ �������� " + n + " ������";
				}
				Dialogs.create().title("��������� ������").masthead(null)
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
					mainApp.getMember().add(
							new Member(matcher.group()));
					n++;
					b = true;
				} else {
					int j = 0;
					for (int i = 0; i < mainApp.getMember().size(); i++) {
						if (!matcher.group().equals(
								mainApp.getMember().get(i)
										.getMainNumberAsString())) {
							j++;
						}
					}
					if (j == mainApp.getMember().size())
						mainApp.getMember().add(
								new Member(matcher.group()));
					n++;
					b = true;
				}
			}
			memberAddDel.setText(null);
			this.lockForIncondite();
			if (b) {
				String str = "������ ������ " + n + " ������";
				if ((n < 20 && n == 1) | (n > 20 && n % 10 == 1)) {
					str = "������ ������ " + n + " ������";
				}
				if ((n < 20 && n == 2) | (n < 20 && n == 3)
						| (n < 20 && n == 4) | (n > 20 && n % 10 == 2)
						| (n > 20 && n % 10 == 3) | (n > 20 && n % 10 == 4)) {
					str = "������ ������ " + n + " ������";
				}
				Dialogs.create().title("��������� ������").masthead(null)
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
					.getNextWork().get()))) {
				mainNumberText.setText(mainApp.getMember().get(i)
						.getMainNumber().get());
				mobNumberText.setText(mainApp.getMember().get(i).getMobNumber()
						.get());
				statusText.getSelectionModel().select(
						mainApp.getMember().get(i).getStatus().get());
				comentText
						.setText(mainApp.getMember().get(i).getComent().get());
				nextWorkText.setValue(null);
				b = false;
				break;
			}
		}
		if (b) {
			for (int i = 0; i < mainApp.getMember().size(); i++) {
				try {
					if (mainApp.getMember().get(i).getComent().get().length() == 0) {
						mainNumberText.setText(mainApp.getMember().get(i)
								.getMainNumber().get());

						d = false;
					}
				} catch (NullPointerException e) {
					mainNumberText.setText(mainApp.getMember().get(i)
							.getMainNumber().get());

					d = false;
				}
			}
		}
		if (b && d) {
			mainNumberText.setText("��������");
		}
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		memberTable.setItems(mainApp.getMember());
	}
}