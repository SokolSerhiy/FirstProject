package main.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class SupportClass {
	private static final String DATE_PATTERN = "dd.MM.yyyy";
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter
			.ofPattern(DATE_PATTERN);
	private static SessionFactory ses;

	public static String format(LocalDate date) {
		if (date == null) {
			return null;
		}
		return DATE_FORMATTER.format(date);
	}

	public static LocalDate parse(String dateString) {
		try {
			if (dateString != null) {
				return DATE_FORMATTER.parse(dateString, LocalDate::from);
			}
		} catch (DateTimeParseException e) {
			return null;
		}
		return null;
	}

	public static void startSessionFactory() {
		Configuration configuration = new Configuration();
		configuration.configure();
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties()).build();
		ses = new Configuration().configure().buildSessionFactory(
				serviceRegistry);
	}

	public static void stopSessionFactory() {
		ses.close();
	}

	@SuppressWarnings("unchecked")
	public static ObservableList<Member> restoreAllMembers() {
		ObservableList<Member> list = FXCollections.observableArrayList();
		Session session = ses.openSession();
		session.beginTransaction();
		List<Member> supportList = session.createCriteria(Member.class).list();
		session.getTransaction().commit();
		session.close();
		if (supportList.size() > 0) {
			list.addAll(supportList);
		}
		return list;
	}

	public static void updateMember(Member member) {
		Session session = ses.openSession();
		session.beginTransaction();
		session.update(member);
		session.getTransaction().commit();
		session.close();
	}

	public static void deleteMember(Member member) {
		Session session = ses.openSession();
		session.beginTransaction();
			session.delete(member);
		session.getTransaction().commit();
		session.close();
	}

	public static void saveMember(Member member) {
		Session session = ses.openSession();
		session.beginTransaction();
		session.save(member);
		session.getTransaction().commit();
		session.close();

	}
}
