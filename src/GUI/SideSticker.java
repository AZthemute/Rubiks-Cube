package GUI;

public class SideSticker extends Sticker {
    static final int LRwidth = 20;
    static final int LRheight = 50;
    static final int FBwidth = 50;
    static final int FBheight = 20;

    public SideSticker(int x, int y, java.awt.Color color, boolean isOnLeftOrRight) {
        super(x, y, color);
        if (isOnLeftOrRight) {
            width = LRwidth;
            height = LRheight;
        }
        else {
            width = FBwidth;
            height = FBheight;
        }
    }
}
