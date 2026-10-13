public class Person {
    private int x, y;
    private final String image;
    private final int maxLives;
    private int lives;

    public Person (int x, int y, int maxLives, String image){
        this.x = x;
        this.y = y;
        this.maxLives = maxLives;
        this.lives = maxLives;
        this.image = image;
    }
    public Person(int x, int y){
        this(x, y, 3, "\uD83E\uDDB8");
    }

    public void move(int x, int y){
        this.x = x;
        this.y = y;
    }

    public boolean decHealth(){
        this.lives--;
        System.out.println("Жизней:" + this.lives);
        return this.lives <= 0;
    }
    public void incHealth(){
        if (this.lives < this.maxLives) { // если нынешнее здоровье меньше максимального
            this.lives++;
            System.out.println("Жизней:" + this.lives);
        }else{
            System.out.println("Жизни не увеличились, достигнут максимум.\n" +
                    "Жизней/макс количество:\n" +
                    this.lives + '/' + this.maxLives);
        }
    }

    public int getX (){
        return this.x;
    }

    public int getY (){
        return this.y;
    }

    public String getImage() {
        return image;
    }

    public int getLives() {
        return lives;
    }
}

