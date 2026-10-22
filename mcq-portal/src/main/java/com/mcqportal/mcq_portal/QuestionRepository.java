package com.mcqportal.mcq_portal;

import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository  extends JpaRepository<Question, Long>
{

}
