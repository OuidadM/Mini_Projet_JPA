package Models;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "student")
public class Student extends Person implements Serializable {
    @OneToOne(cascade = CascadeType.PERSIST)
    private Address address;

    @ManyToMany(cascade = {CascadeType.PERSIST})
    @JoinTable(name = "inscription",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "module_id"))
    private List<Module> modules = new ArrayList<>();

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Module> getModules() {
        return modules;
    }

    public void addModule(Module m) {
        modules.add(m);
    }
}
