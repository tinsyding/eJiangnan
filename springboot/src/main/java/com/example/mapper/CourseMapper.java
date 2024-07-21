package com.example.mapper;

import com.example.entity.Course;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface CourseMapper {

    @Select("SELECT * FROM course WHERE name LIKE CONCAT('%', #{name} ,'%') AND no LIKE CONCAT('%', #{no} ,'%') " +
            "AND teacher LIKE CONCAT('%', #{teacher} ,'%') AND description LIKE CONCAT('%', #{description} ,'%') " +
            "AND times LIKE CONCAT('%', #{times} ,'%') ORDER BY id DESC")
    List<Course> selectAll(Course course);

    @Insert("INSERT INTO course(name, no, description, times, teacher) " +
            "VALUES(#{name}, #{no}, #{description}, #{times}, #{teacher})")
    void insert(Course course);

    @Delete("DELETE FROM course WHERE id = #{id}")
    void deleteById(Integer id);

    @Update("UPDATE course SET name = #{name}, no = #{no}, description = #{description}, " +
            "times = #{times}, teacher = #{teacher} WHERE id = #{id}")
    void updateById(Course course);

}