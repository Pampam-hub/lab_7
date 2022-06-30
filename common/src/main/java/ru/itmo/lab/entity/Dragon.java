package ru.itmo.lab.entity;


import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;
import java.time.LocalDateTime;

@XStreamAlias("dragon")
public class Dragon implements Serializable {

    @XStreamAlias("id")
    protected Integer id;

    @XStreamAlias("name")
    protected String name;

    @XStreamAlias("coordinates")
    protected Coordinates coordinates;

    // Поле не может быть null, значение генерируется автоматически
    @XStreamAlias("creationDate")
    protected LocalDateTime creationDate;

    // Значение поля > 0, поле не может быть null
    @XStreamAlias("age")
    protected Integer age;

    // Значение поля > 0
    @XStreamAlias("wingspan")
    protected int wingspan;
    // Поле не может быть null
    @XStreamAlias("dragonType")
    protected DragonType dragonType;
    // Поле не может быть null
    @XStreamAlias("dragonCharacter")
    protected DragonCharacter dragonCharacter;

    @XStreamAlias("dragonHead")
    protected DragonHead dragonHead;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public void setCreationDate() {
        creationDate = LocalDateTime.now();
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public int getWingspan() {
        return wingspan;
    }

    public void setWingspan(int wingspan) {
        this.wingspan = wingspan;
    }

    public DragonType getType() {
        return dragonType;
    }

    public void setType(DragonType type) {
        this.dragonType = type;
    }

    public DragonCharacter getDragonCharacter() {
        return dragonCharacter;
    }

    public void setCharacter(DragonCharacter dragonCharacter) {
        this.dragonCharacter = dragonCharacter;
    }

    public DragonHead getDragonHead() {
        return dragonHead;
    }

    public void setDragonHead(DragonHead dragonHead) {
        this.dragonHead = dragonHead;
    }

    @Override
    public String toString() {
        return "Dragon{ " +
                "id: " + id +
                ", name: '" + name + '\'' +
                ", " + coordinates +
                ", creationDate: " + creationDate +
                ", age: " + age +
                ", wingspan: " + wingspan +
                ", type: " + dragonType +
                ", dragonCharacter: " + dragonCharacter +
                ", " + dragonHead + " }";
    }
}
