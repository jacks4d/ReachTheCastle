public class Castle {
    final private int x, y;
    private final String image;

    public Castle(int x, int y, String image){
        this.x = x;
        this.y = y;
        this.image = image;
    }

    public Castle(int x, int y){
        this(x, y, "\uD83C\uDFF0");
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getImage() {
        return image;
    }
}
