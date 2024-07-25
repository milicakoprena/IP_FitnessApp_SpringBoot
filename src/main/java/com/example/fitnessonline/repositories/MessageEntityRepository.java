package com.example.fitnessonline.repositories;

import com.example.fitnessonline.model.entities.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageEntityRepository extends JpaRepository<MessageEntity, Integer> {
}
