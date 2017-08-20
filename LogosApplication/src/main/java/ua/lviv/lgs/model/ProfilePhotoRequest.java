package ua.lviv.lgs.model;

public class ProfilePhotoRequest {

	private String img;
	
	private double maskLeft;
	
	private double maskTop;
	
	private double maskWidth;
	
	private double maskHeight;
	
	private double imgWidth;
	
	private double imgHeight;

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public double getMaskLeft() {
		return maskLeft;
	}

	public void setMaskLeft(double maskLeft) {
		this.maskLeft = maskLeft;
	}

	public double getMaskTop() {
		return maskTop;
	}

	public void setMaskTop(double maskTop) {
		this.maskTop = maskTop;
	}

	public double getMaskWidth() {
		return maskWidth;
	}

	public void setMaskWidth(double maskWidth) {
		this.maskWidth = maskWidth;
	}

	public double getMaskHeight() {
		return maskHeight;
	}

	public void setMaskHeight(double maskHeight) {
		this.maskHeight = maskHeight;
	}

	public double getImgWidth() {
		return imgWidth;
	}

	public void setImgWidth(double imgWidth) {
		this.imgWidth = imgWidth;
	}

	public double getImgHeight() {
		return imgHeight;
	}

	public void setImgHeight(double imgHeight) {
		this.imgHeight = imgHeight;
	}

	@Override
	public String toString() {
		return "ProfilePhotoRequest [maskLeft=" + maskLeft + ", maskTop=" + maskTop + ", maskWidth=" + maskWidth
				+ ", maskHeight=" + maskHeight + ", imgWidth=" + imgWidth + ", imgHeight=" + imgHeight + "]";
	}
}
