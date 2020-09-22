package io.github.simplex.serialization.util;

import java.util.Optional;

import io.github.simplex.serialization.object.content.Compostables;
import io.github.simplex.serialization.object.content.Flammables;
import io.github.simplex.serialization.object.content.Fuels;
import io.github.simplex.serialization.object.content.Paveables;
import io.github.simplex.serialization.object.content.Strippables;
import io.github.simplex.serialization.object.content.Tillables;

public class ObjectsHolder {
    private Compostables compostables;
    private Flammables flammables;
    private Fuels fuels;
    private Paveables paveables;
    private Strippables strippables;
    private Tillables tillables;

    public Compostables getCompostables() {
        return Optional.ofNullable(this.compostables).orElse(Compostables.getDefault());
    }

    public Flammables getFlammables() {
        return Optional.ofNullable(this.flammables).orElse(Flammables.getDefault());
    }

    public Fuels getFuels() {
        return Optional.ofNullable(this.fuels).orElse(Fuels.getDefault());
    }

    public Paveables getPaveables() {
        return Optional.ofNullable(this.paveables).orElse(Paveables.getDefault());
    }

    public Strippables getStrippables() {
        return Optional.ofNullable(this.strippables).orElse(Strippables.getDefault());
    }

    public Tillables getTillables() {
        return Optional.ofNullable(this.tillables).orElse(Tillables.getDefault());
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
