package dev.chanchhaya.course;

import dev.chanchhaya.course.domain.Course;
import dev.chanchhaya.course.features.course.CourseRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

@SpringBootTest
class CoursesServiceTests {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    void filterCourses() {
        Query query = new Query();
        Criteria criteria = Criteria
                .where("title")
                .regex("test", "i");
        Criteria criteria2 = Criteria
                .where("price")
                .gte(100);
        Criteria criteria3 = new Criteria();
        criteria3.orOperator(criteria, criteria2);
        query.addCriteria(criteria3);
        List<Course> courses = mongoTemplate.find(query, Course.class);
        //List<Course> courses = mongoTemplate.query(Course.class).matching(query).all();
        //List<Course> courses = courseRepository.filterCoursesByTitle("^A");
        //List<Course> courses = courseRepository.findCoursesTitle("spring");
        //List<Course> courses = courseRepository.findCoursesByPrice(BigDecimal.valueOf(100));
        System.out.println(courses);
        System.out.println(courses.size());
    }

}
