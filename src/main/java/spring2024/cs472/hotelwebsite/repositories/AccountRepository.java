package spring2024.cs472.hotelwebsite.repositories;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring2024.cs472.hotelwebsite.entities.*;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findById(long id);
}
