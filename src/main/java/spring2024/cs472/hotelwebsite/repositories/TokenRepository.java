package spring2024.cs472.hotelwebsite.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import spring2024.cs472.hotelwebsite.entities.PasswordResetToken;

public interface TokenRepository extends JpaRepository<PasswordResetToken, Integer>{

    PasswordResetToken findByToken(String token);

}
