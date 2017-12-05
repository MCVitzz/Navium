import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PVector;

class Menu {
    private PImage title, play, quit;
    private PVector playBtn, quitBtn, titleTitle;
    private int pixelRadius;

    Menu(PApplet applet, float x, float parts, float titlePart, float playPart, float quitPart) {
        //X is not necessary here, but just in case we'll use it later

        title = applet.loadImage("title.png");
        play = applet.loadImage("play.png");
        quit = applet.loadImage("quit.png");

        titleTitle = new PVector(x, titlePart * applet.height / parts);
        playBtn = new PVector(x, playPart * applet.height / parts);
        quitBtn = new PVector(x, quitPart * applet.height / parts);

        pixelRadius = 100;
    }

    void display(PGraphics graphics) {
        graphics.imageMode(graphics.CENTER);
        //Draw Title
        graphics.image(title, titleTitle.x, titleTitle.y);
        //Draw Play
        graphics.image(play, playBtn.x, playBtn.y);
        //Draw Quit
        graphics.image(quit, quitBtn.x, quitBtn.y);
    }

    String checkButtons(float mouseX, float mouseY) {
        String result = "";
        PVector mouseVector = new PVector(mouseX, mouseY);
        if (playBtn.dist(mouseVector) <= pixelRadius)
            result = "play";
        else if (quitBtn.dist(mouseVector) <= pixelRadius)
            result = "quit";

        return result;
    }
}