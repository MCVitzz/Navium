package utilities;

public class ResolutionManager {

    //This class handles all the resolution difference there may be in all the PCs this game might be played in

    //It basically uses a proportional rule

    private int width, height;
    private float currentWidth, currentHeight;

    public ResolutionManager(float currentWidth, float currentHeight) {
        width = 1920;
        height = 1080;
        this.currentWidth = currentWidth;
        this.currentHeight = currentHeight;
    }

    public float resolvedOfWidth(float number) {
        return (currentWidth * number) / width;
    }

    public float resolvedOfHeight(float number) {
        return (currentHeight * number) / height;
    }

}