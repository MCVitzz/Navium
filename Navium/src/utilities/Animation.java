package utilities;

import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;

public class Animation {
    private ArrayList<PImage> images;
    private int imageCount;
    private int index;

    Animation(String prefix, int count, PApplet applet) {
        this.imageCount = count;
        this.index = 0;
        this.images = new ArrayList<>();

        for (int i = 0; i < imageCount; i++) {
            String filename = prefix + PApplet.nf(i + 1, 4) + ".png";
            images.add(applet.loadImage(filename));
        }
    }

    public PImage image() {
        index++;
        if(index > imageCount - 1)
            index = 0;
        return images.get(index);
    }
}