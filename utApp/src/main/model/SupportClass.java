package main.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Iterator;
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

	public static SessionFactory getSessionFactory() {
		Configuration configuration = new Configuration();
		configuration.configure();
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties()).build();
		SessionFactory sessionFactory = new Configuration().configure()
				.buildSessionFactory(serviceRegistry);
		return sessionFactory;
	}

	public static ObservableList<Member> restoreAllMembers() {
		ObservableList<Member> list = FXCollections.observableArrayList();
		Session session = getSessionFactory().openSession();
		session.beginTransaction();
		// List<SupportMember> supportList = (List<SupportMember>) session
		// .createSQLQuery("SELECT * FROM supportmember")
		// .addEntity(SupportMember.class).list();
		List<SupportMember> supportList = (List<SupportMember>) session
				.createCriteria(SupportMember.class).list();
		session.getTransaction().commit();
		session.close();
		if (supportList.size() > 0) {
			for (SupportMember supportMember : supportList) {
				System.out.println(supportList.size());
				String mainNumber = new Long(supportMember.getMainNumber())
						.toString();
				String mobNumber = new Long(supportMember.getMobNumber())
						.toString();
				String status = supportMember.getStatus();
				String coment = supportMember.getComent();
				String nextWork = supportMember.getNextWork();
				list.add(new Member(mainNumber, mobNumber, status, coment,
						nextWork));
			}
		}
		return list;
	}

	public static void updateMember(Member member) {
		long mainNumber = Long.parseLong(member.getMainNumberAsString());
		long mobNumber = 0;
		if (member.getMobNumber() != null) {
			if (member.getMobNumber().get() != null) {
				mobNumber = Long.parseLong(member.getMobNumber().get());
			}
		}
		String status = null;
		if (member.getStatus() != null) {
			status = member.getStatus().get();
		}
		String coment = null;
		if (member.getComent() != null) {
			coment = member.getComent().get();
		}
		String nextWork = null;
		if (member.getNextWork() != null) {
			nextWork = member.getNextWork().get();
		}
		Session session = getSessionFactory().openSession();
		session.beginTransaction();
		session.update(new SupportMember(mainNumber, mobNumber, status, coment,
				nextWork));
		session.getTransaction().commit();
		session.close();
	}

	public static void deleteMember(List<String> list) {
		Session session = getSessionFactory().openSession();
		session.beginTransaction();
		for (String supportString : list) {
			session.delete(new SupportMember(Long.parseLong(supportString)));
		}
		session.getTransaction().commit();
		session.close();
	}

	public static void saveAllMembers(ObservableList<Member> list) {
		Iterator<Member> iter = list.iterator();
		Session session = getSessionFactory().openSession();
		session.beginTransaction();
		while (iter.hasNext()) {
			long mainNumber = Long.parseLong(iter.next().getMainNumber().get());
			// long mobNumber = Long.parseLong(tmp.getMobNumber().get());
			// String status = tmp.getStatus().get();
			// String coment = tmp.getComent().get();
			// String nextWork = tmp.getNextWork().get();
			session.save(new SupportMember(mainNumber));
		}
		session.getTransaction().commit();
		session.close();
	}
}
