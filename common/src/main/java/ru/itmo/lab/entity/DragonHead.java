package ru.itmo.lab.entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;

@XStreamAlias("dragonHead")
public class DragonHead implements Serializable {

    private double eyesCount;

    public void setEyesCount(double eyesCount) {
        this.eyesCount = eyesCount;
    }

    public double getEyesCount() {
        return eyesCount;
    }

    @Override
    public String toString() {
        return "DragonHead{" +
                "eyesCount=" + eyesCount +
                '}';
    }
}
