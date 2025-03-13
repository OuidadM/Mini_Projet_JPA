package dao;

import Models.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import Models.Module;
import java.util.List;

public class ModuleDAO {
    private EntityManager em;

    public ModuleDAO(EntityManager em) {
        this.em = em;
    }

    // Save "Module"
    public void save(Module module) {
        EntityTransaction tx = em.getTransaction();
        try {
            em.persist(module);
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }


    // Récupérer tous les modules
    public List<Module> findAll() {
        return em.createQuery("SELECT m FROM Module m", Module.class).getResultList();
    }

    // Trouver les modules d'un étudiant
    public List<Module> findModulesByStudent(int studentId) {
        return em.createQuery(
                        "SELECT m FROM Module m JOIN m.students s WHERE s.id = :studentId", Module.class)
                .setParameter("studentId", studentId)
                .getResultList();
    }

    // Supprimer un module
    public void delete(int id) {
        EntityTransaction tx = em.getTransaction();
        try {
            Module module = em.find(Module.class, id);
            if (module != null) {
                for (Student std : module.getStudents()) {
                    std.getModules().remove(module);
                }
                em.remove(module);
            }
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }
}
