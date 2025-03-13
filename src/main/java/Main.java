import Models.Address;
import Models.Prof;
import Models.Student;
import Models.Module;
import dao.StudentDAO;
import dao.ModuleDAO;

import jakarta.persistence.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("studentsJPA");
        EntityManager em = emf.createEntityManager();

        StudentDAO studentDAO = new StudentDAO(em);
        ModuleDAO moduleDAO = new ModuleDAO(em);

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Prof prof1 = new Prof();
        prof1.setNom("Arjoun");
        prof1.setPrenom("Mohammed");
        prof1.setSpecialite("Web development");

        //Modules
        Module m1 = new Module();
        m1.setNom("Web");
        Module m2 = new Module();
        m2.setNom("Java");
        Module m3 = new Module();
        m3.setNom("JEE");

        prof1.addModule(m1);
        prof1.addModule(m3);

        //Student 1
        Student student1 = new Student();
        student1.setNom("Wahbi");
        student1.setPrenom("Walid");
        student1.setEmail("walid@gmail.com");

        Address address1 = new Address();
        address1.setCity("Rabat Ville");
        address1.setStreet("Irfan");
        address1.setState("Rabat ville");

        student1.setAddress(address1);
        student1.addModule(m1);
        student1.addModule(m2);
        m1.addStudent(student1);
        m2.addStudent(student1);

        //Student 2
        Student student2 = new Student();
        student2.setNom("Mehdi");
        student2.setPrenom("Ali");
        student2.setEmail("mehdi@gmail.com");

        Address address2 = new Address();
        address2.setCity("Casablanca");
        address2.setStreet("Sidi Youssef");
        address2.setState("Casa");

        student2.setAddress(address2);
        student2.addModule(m2);
        student2.addModule(m3);
        m2.addStudent(student2);
        m3.addStudent(student2);

        //Persistance
        em.persist(prof1);
        em.persist(m1);
        em.persist(m2);
        em.persist(m3);
        em.persist(address1);
        em.persist(address2);
        em.persist(student1);
        em.persist(student2);

        tx.commit();

        afficherEtudiants(em);
        List<Module> modules = moduleDAO.findAll();
        for (Module m : modules) afficherEtudiantsParModule(em, m.getNom());

        tx.begin();
        Student s = studentDAO.findById(student1.getId());
        if (s != null) {
            s.setNom("Updated Wahbi");
            studentDAO.update(s);
        }
        tx.commit();
        afficherEtudiants(em);

        tx.begin();
        studentDAO.delete(student2.getId());
        tx.commit();
        afficherEtudiants(em);

        System.out.println("\nAvant suppression de " + m1.getNom() + " : ");
        modules = moduleDAO.findModulesByStudent(student1.getId());
        afficherModules(modules, student1);

        tx.begin();
        moduleDAO.delete(m1.getId());
        tx.commit();

        System.out.println("\nAprès la suppression de " + m1.getNom() + " : ");
        modules = moduleDAO.findModulesByStudent(student1.getId());
        afficherModules(modules, student1);

        tx.begin();
        Module newModule = new Module();
        newModule.setNom("Spring Boot");
        moduleDAO.save(newModule);
        s = studentDAO.findById(student1.getId());
        if (s != null) {
            s.addModule(newModule);
            newModule.addStudent(s);
            studentDAO.update(s);
        }
        tx.commit();

        System.out.println("\nAprès ajout du module à l'étudiant :");
        modules = moduleDAO.findModulesByStudent(student1.getId());
        afficherModules(modules, student1);

        em.close();
        emf.close();
    }

    public static void afficherEtudiants(EntityManager em) {
        List<Student> students = em.createQuery("SELECT s FROM Student s", Student.class).getResultList();
        System.out.println("\nListe des étudiants :");
        for (Student s : students) {
            System.out.println("- " + s.getNom() + " " + s.getPrenom() + " | Email: " + s.getEmail());
        }
    }

    public static void afficherEtudiantsParModule(EntityManager em, String moduleName) {
        List<Student> students = em.createQuery("SELECT s FROM Student s JOIN s.modules m WHERE m.nom = :nom", Student.class)
                .setParameter("nom", moduleName)
                .getResultList();
        System.out.println("\nÉtudiants inscrits au module " + moduleName + " :");
        for (Student s : students) {
            System.out.println("- " + s.getNom() + " " + s.getPrenom());
        }
    }

    public static void afficherModules(List<Module> modules, Student student) {
        System.out.println("\n" + student.getNom() + " " + student.getPrenom() + " est inscrit aux modules suivants : ");
        if (modules.isEmpty()) {
            System.out.println("Cet étudiant n'est inscrit à aucun module.");
        } else {
            for (Module m : modules) {
                System.out.println("- " + m.getNom());
            }
        }
    }
}
