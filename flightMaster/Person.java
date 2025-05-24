package flightMaster;

public abstract class Person {
   
	private String name;
    private String id;
    private int age;

    // Constructor
    public Person(String name, String id, int age) {
        this.name = name;
        this.id = id;
        this.age = age;
    }

    // Getter and Setter for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter and Setter for id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // Getter and Setter for age
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
    @Override
    public String toString() {
        return "Name: " + name + ", ID: " + id + ", Age: " + age;
    }

}
