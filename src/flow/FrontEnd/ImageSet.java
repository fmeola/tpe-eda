package flow.FrontEnd;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import flow.gui.ImageUtils;

public class ImageSet {

	private HashMap<Integer, Image> imageMap;

	public ImageSet() throws IOException {
		this.imageMap = new HashMap<Integer, Image>();
		this.FillMap();
	}

	public void FillMap() throws IOException {
		imageMap.put(
				0,
				ImageUtils.loadImage("resources" + File.separator + "img"
						+ File.separator + "piso.jpg"));
		imageMap.put(
				1,
				ImageUtils.loadImage("resources" + File.separator + "img"
						+ File.separator + "rojo.jpg"));
		imageMap.put(
				2,
				ImageUtils.loadImage("resources" + File.separator + "img"
						+ File.separator + "verde.jpg"));
		imageMap.put(
				3,
				ImageUtils.loadImage("resources" + File.separator + "img"
						+ File.separator + "azul.jpg"));
		imageMap.put(
				4,
				ImageUtils.loadImage("resources" + File.separator + "img"
						+ File.separator + "celeste.jpg"));
		imageMap.put(
				5,
				ImageUtils.loadImage("resources" + File.separator + "img"
						+ File.separator + "amarillo.jpg"));
		imageMap.put(
				6,
				ImageUtils.loadImage("resources" + File.separator + "img"
						+ File.separator + "francia.jpg"));
		imageMap.put(
				7,
				ImageUtils.loadImage("resources" + File.separator + "img"
						+ File.separator + "marron.jpg"));
		imageMap.put(
				8,
				ImageUtils.loadImage("resources" + File.separator + "img"
						+ File.separator + "naranja.jpg"));
		imageMap.put(
				9,
				ImageUtils.loadImage("resources" + File.separator + "img"
						+ File.separator + "rosa.jpg"));
		imageMap.put(
				10,
				ImageUtils.loadImage("resources" + File.separator + "img"
						+ File.separator + "verdeclaro.jpg"));
	}

	public HashMap<Integer, Image> getMap() {
		return imageMap;
	}
}
