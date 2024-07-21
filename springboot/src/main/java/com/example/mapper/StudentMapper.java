package com.example.mapper;

import com.example.entity.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface StudentMapper {

    // 根据学号/工号查询学生
    @Select("SELECT * FROM student WHERE username = #{username}")
    Student selectByUsername(String username);

    // 新增学生
    @Insert("INSERT INTO student (username, password, name, phone, email, sex, birth, avatar, role) " +
            "VALUES (#{username}, #{password}, #{name}, #{phone}, #{email}, #{sex}, #{birth}, #{avatar}, #{role})")
    void insert(Student student);

    // 删除学生
    @Delete("DELETE FROM student WHERE id = #{id}")
    void deleteById(Integer id);

    // 修改学生
    @Update("UPDATE student SET username = #{username}, password = #{password}, name = #{name}, phone = #{phone}, " +
            "email = #{email}, sex = #{sex}, birth = #{birth}, avatar = #{avatar} WHERE id = #{id}")
    void updateById(Student student);

    // 查询学生
    @Select("SELECT * FROM student WHERE username LIKE CONCAT('%', #{username}, '%') " +
            "AND name LIKE CONCAT('%',  #{name}, '%') ORDER BY id DESC")
    List<Student> selectAll(Student student);

    // 更新学生密码
    @Update("UPDATE student SET password = #{password} WHERE username = #{username}")
    void update(Student student);
}
