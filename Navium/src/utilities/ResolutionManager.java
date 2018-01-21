package utilities;

import processing.core.PApplet;

public class ResolutionManager {

    //This class handles all the resolution difference there may be in all the PCs this game might be played in

    //It basically has a comparator (1920 or 1080) and a comparable(min(width, height))


    private int comparator, comparable;

    public ResolutionManager(float currentWidth, float currentHeight) {
        int width = 1920;
        int height = 1080;

        comparator = Math.round(PApplet.min(currentWidth, currentHeight));
        if(comparator == currentHeight)
            comparable = height;
        else comparable = width;

    }

    public float resolvedOf(float number) {
        return (comparator * number) / comparable;
    }

}