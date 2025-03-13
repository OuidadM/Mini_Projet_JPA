package Models;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "inscription")
public class Inscription implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int numSequence;
    private Double note;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "module_id")
    private Module module;

    public int getNumSequence() { return numSequence; }
    public void setNumSequence(int numSequence) { this.numSequence = numSequence; }

    public Double getNote() { return note; }
    public void setNote(Double note) { this.note = note; }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public Module getModule() { return module; }
    public void setModule(Module module) { this.module = module; }
}
