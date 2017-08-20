package ua.lviv.lgs.service.implementation;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ua.lviv.lgs.model.ProfilePhotoRequest;
import ua.lviv.lgs.service.PhotoSaver;

@Service
public class PhotoSaverImpl implements PhotoSaver{
	
	@Value("${path.to.img.folder}")
	private String imgPath;
	
	@Override
	public String savePhoto(ProfilePhotoRequest request) {
		try {
			byte[] data = Base64.getDecoder().decode(request.getImg().split(",")[1].getBytes(StandardCharsets.UTF_8));
			InputStream in = new ByteArrayInputStream(data);
			BufferedImage present = ImageIO.read(in);
			final double coefficientY = present.getHeight()/request.getImgHeight();
			final double coefficientX = present.getWidth()/request.getImgWidth();
			BufferedImage forSave = new BufferedImage(300, 400, present.getType());
			final double realMaskWidth = request.getMaskWidth() * coefficientX;
			final double realMaskHeight = request.getMaskHeight() * coefficientY;
			final int realTop = (int) Math.round(request.getMaskTop() * coefficientY);
			final int realLeft = (int) Math.round(request.getMaskLeft() * coefficientX);
			final double maskCoefficientY = realMaskHeight / 400;
			final double maskCoefficientX = realMaskWidth / 300;
			for (int x = 0; x < forSave.getWidth(); x++) {
				for (int y = 0; y < forSave.getHeight(); y++) {
					forSave.setRGB(x, y, present.getRGB((int)Math.round(x*maskCoefficientX)+realLeft, (int)Math.round(y*maskCoefficientY)+realTop));
				}
			}
			String fileName = UUID.randomUUID().toString()+".jpeg";
			ImageIO.write(forSave, "jpeg", new File(new File(imgPath), fileName));
			return fileName;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void deletePreviousPhoto(String photoUrl){
		if(photoUrl!=null){
			new File(new File(imgPath), photoUrl).delete();
		}
	}
}
