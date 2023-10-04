package com.fzoo.zoomanagementsystem.repository;

import com.fzoo.zoomanagementsystem.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
}
