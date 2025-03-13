package Models;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "prof")
public class Prof extends Person implements Serializable {
    private String specialite;

    @OneToMany(mappedBy = "prof", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Module> modules = new ArrayList<>();

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }

    public void addModule(Module module) {
        modules.add(module);
        module.setProf(this);  // Lier le module au professeur
    }

    public void removeModule(Module module) {
        modules.remove(module);
        module.setProf(null);  // Désassocier le module du professeur
    }
}
