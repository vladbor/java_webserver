package resources;


public class TestResource implements TestResourceI {
    private String name;
    private int age;

    public TestResource() {
        this.name = "";
        this.age = 0;
    }


    @Override
    public int getAge() {
        return age;
    }

    @Override
    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String  getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}
