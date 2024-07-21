package com.example.mapper;

import com.example.entity.StudentCourse;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface StudentCourseMapper {

    @Insert("INSERT INTO student_course (name, no, student_id, course_id) VALUES (#{name}, #{no}, #{studentId}, #{courseId})")
    void insert(StudentCourse studentCourse);

    @Select("SELECT * FROM student_course WHERE student_id = #{studentId} AND course_id = #{courseId}")
    StudentCourse selectByCondition(StudentCourse studentCourse);

    // 没法区分管理员和学生，所以不用这个方法，转到myBatis的xml文件中
    List<StudentCourse> selectAll(StudentCourse studentCourse);

    @Delete("DELETE FROM student_course WHERE id = #{id}")
    void deleteById(Integer id);
}
