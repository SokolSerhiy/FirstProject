package ua.lviv.lgs.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import ua.lviv.lgs.entity.User;

public interface UserRepository extends JpaRepository<User, String>{

	User findByEmail(String email);

	boolean existsByEmail(String email);

	@Transactional
	@Modifying
	@Query("UPDATE User u SET u.photoUrl = ?1 WHERE u.email = ?2")
	void addPhoto(String photoUrl, String email);
	
	@Query("SELECT u.photoUrl FROM User u WHERE u.email = ?1")
	String findPhotoUrlByEmail(String email);
	@Query("SELECT u.password FROM User u WHERE u.email = ?1")
	String findPasswordByEmail(String email);
	@Transactional
	@Modifying
	@Query("UPDATE User u SET u.password = ?1 WHERE u.email = ?2")
	void changePassword(String password, String email);
	@Transactional
	@Modifying
	@Query("UPDATE User u SET u.firstName = ?2, u.lastName = ?3, u.email = ?4, u.phoneNumber = ?5 WHERE u.email = ?1")
	void updateUserData(String email, String firstName, String lastName, String email2, String phoneNumber);

}
