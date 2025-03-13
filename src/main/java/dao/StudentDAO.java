package dao;

import Models.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;


public class StudentDAO {
    private EntityManager em;

    public StudentDAO(EntityManager em) {
        this.em = em;
    }

    public Student findById(int id) {
        return em.find(Student.class, id);
    }

    public void update(Student student) {
        em.merge(student);
    }

    public void delete(int id) {
        Student student = em.find(Student.class, id);
        if (student != null) {
            em.remove(student);
        }
    }
}
