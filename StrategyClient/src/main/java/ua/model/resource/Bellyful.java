package ua.model.resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ua.validator.exception.ПомилкаГри;

public class Bellyful implements Serializable{

	private static final long serialVersionUID = -5021663413245478211L;
	
	private List<Integer> humanBellyfuls = new ArrayList<>();
	
	public static Bellyful restore() {
		File file = new File("saves.save");
		try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))){
			Bellyful tmp = (Bellyful) ois.readObject();
			validate(tmp);
			return tmp;
		}catch (IOException | ClassNotFoundException e) {
		}
		return new Bellyful();
	}
	
	private static void validate(Bellyful tmp) {
		for (Integer i : tmp.humanBellyfuls) {
			if(i>100) throw new ПомилкаГри("Чітерити не добре");
		} 
	}
	
	public void save() {
		File file = new File("saves.save");
		try(ObjectOutputStream ois = new ObjectOutputStream(new FileOutputStream(file))){
			ois.writeObject(this);
			ois.flush();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void clearHumanBellyfuls() {
		humanBellyfuls.clear();
	}
	
	public List<Integer> getHumanBellyfuls() {
		return humanBellyfuls;
	}

	public void setHumanBellyfuls(List<Integer> humanBellyfuls) {
		this.humanBellyfuls = humanBellyfuls;
	}
	
	
}
