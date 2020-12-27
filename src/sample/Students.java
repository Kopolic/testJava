package sample;

public class Students {
    private final int id;
    private final String name;
    private final String a1;
    private final String a2;
    private final String a3;
    private final String a4;

    public Students(int id, String name, String a1, String a2, String a3, String a4){
        this.id = id;
        this.name = name;
        this.a1 = a1;
        this.a2 = a2;
        this.a3 = a3;
        this.a4 = a4;
    }
    public int getId(){return id;}
    public String getName() {
        return name;
    }
    public String getA1() {
        return a1;
    }
    public String getA2() {
        return a2;
    }
    public String getA3() {
        return a3;
    }
    public String getA4() {
        return a4;
    }
    @Override
    public String toString(){
        return String.format("%s; %s; %s; %s; %s \n",getName(), getA1(), getA2(), getA3(), getA4());
    }
}
