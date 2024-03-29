package ro.mycode.librarieapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ro.mycode.librarieapi.model.Student;

import java.util.Optional;

@Repository
public interface StudentRepo extends JpaRepository<Student,Long> {

    @Query("select s from Student s where s.email=?1")
    Optional<Student> findStudentsByEmail(String email);

    @Query(value="select e.course_id,count(*) as numarapariti from enrolled_coruse e group by e.course_id order by  numarapariti desc  limit 1",nativeQuery = true)
    Optional<Long> bestCourseId();


    void removeByEmail(String email);


}
