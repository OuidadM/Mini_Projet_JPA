package Models;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "module")
public class Module implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nom;
    private String description;

    @ManyToOne
    @JoinColumn(name = "prof_id")
    private Prof prof;

    @ManyToMany(mappedBy = "modules")
    private List<Student> students = new ArrayList<>();

    public Integer getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<Student> getStudents() { return students; }
    public void setStudents(List<Student> students) { this.students = students; }
    public void addStudent(Student student) { this.students.add(student); }

    public Prof getProf() { return prof; }
    public void setProf(Prof prof) { this.prof = prof; }
}
