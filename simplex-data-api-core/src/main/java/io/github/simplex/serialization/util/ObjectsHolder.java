package io.github.simplex.serialization.util;

import io.github.simplex.serialization.object.content.Compostables;
import io.github.simplex.serialization.object.content.Flammables;
import io.github.simplex.serialization.object.content.Fuels;
import io.github.simplex.serialization.object.content.Paveables;
import io.github.simplex.serialization.object.content.Strippables;
import io.github.simplex.serialization.object.content.Tillables;

import net.minecraft.nbt.CompoundTag;

public class ObjectsHolder {
    private Compostables compostables;
    private Flammables flammables;
    private Fuels fuels;
    private Paveables paveables;
    private Strippables strippables;
    private Tillables tillables;

    public Compostables getCompostables() {
        return this.compostables;
    }

    public Flammables getFlammables() {
        return this.flammables;
    }

    public Fuels getFuels() {
        return this.fuels;
    }

    public Paveables getPaveables() {
        return this.paveables;
    }

    public Strippables getStrippables() {
        return this.strippables;
    }

    public Tillables getTillables() {
        return this.tillables;
    }

    public void setCompostables(Compostables compostables) {
        this.compostables = compostables;
    }

    public void setFlammables(Flammables flammables) {
        this.flammables = flammables;
    }

    public void setFuels(Fuels fuels) {
        this.fuels = fuels;
    }

    public void setPaveables(Paveables paveables) {
        this.paveables = paveables;
    }

    public void setStrippables(Strippables strippables) {
        this.strippables = strippables;
    }

    public void setTillables(Tillables tillables) {
        this.tillables = tillables;
    }

    public void addAll() {
        this.getCompostables().addAll();
        this.getFlammables().addAll();
        this.getFuels().addAll();
        this.getPaveables().addAll();
        this.getPaveables().addAll();
        this.getTillables().addAll();
    }

    public void removeAll() {
        this.getCompostables().removeAll();
        this.getFlammables().removeAll();
        this.getFuels().removeAll();
        this.getPaveables().removeAll();
        this.getPaveables().removeAll();
        this.getTillables().removeAll();
    }
}
