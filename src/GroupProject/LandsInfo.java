/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GroupProject;

/**
 *
 * @author jason
 */
public class LandsInfo {
    int slot;
    String landName;
    int price;

    public LandsInfo(int slot, String landName, int price) {
        this.slot = slot;
        this.landName = landName;
        this.price = price;
    }

    public int getSlot() {
        return slot;
    }

    public String getLandName() {
        return landName;
    }

    public int getPrice() {
        return price;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public void setLandName(String landName) {
        this.landName = landName;
    }

    public void setPrice(int price) {
        this.price = price;
    }


}

