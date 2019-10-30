package  com.jxk.oto.dto;

import java.io.InputStream;

public class ImageHoder {
	private String ImageName;
	private InputStream image;
	public ImageHoder(String imageName, InputStream image) {
		super();
		ImageName = imageName;
		this.image = image;
	}
	public String getImageName() {
		return ImageName;
	}
	public void setImageName(String imageName) {
		ImageName = imageName;
	}
	public InputStream getImage() {
		return image;
	}
	public void setImage(InputStream image) {
		this.image = image;
	}
	

}

